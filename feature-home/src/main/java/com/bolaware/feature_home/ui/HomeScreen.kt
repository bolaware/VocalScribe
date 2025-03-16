package com.bolaware.feature_home.ui

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bolaware.core.theme.SunRed
import com.bolaware.core.ui.AutoScrollingTextField
import com.bolaware.feature_home.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    var requestedPermission by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is HomeEvent.Snackbar -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(it.message)
                    }
                }
            }
        }
    }

    HomeContent(
        state = state,
        text = state.text,
        modifier = Modifier.fillMaxSize(),
        onTextChange = viewModel::onTextChanged,
        onMicClicked = {
            if (permissionState.status.isGranted) {
                viewModel.onMicClicked()
            } else {
                requestedPermission = true
                permissionState.launchPermissionRequest()
            }
        },
        onClearClicked = viewModel::onClearClicked,
        onSaveClicked = viewModel::onSaveClicked,
        onLanguageSelected = viewModel::onLanguageChoiceSelected
    )

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted && requestedPermission) {
            requestedPermission = false
            viewModel.onMicClicked()
        }
    }

    state.alertDialogState?.let {
        AlertDialogContent(it) { viewModel.dismissDialog()  }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
private fun HomeContent(
    text: String,
    state: HomeState,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onMicClicked: () -> Unit,
    onClearClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onLanguageSelected: (LanguageUi) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LanguageSelector(
            modifier = Modifier.align(Alignment.TopEnd),
            state = state,
            onLanguageSelected = onLanguageSelected
        )

        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            AnimatedVisibility(state.showTextField) {
                AutoScrollingTextField(
                    text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    onTextChange = onTextChange
                )
            }

            if (state.showMicButton) {
                MicButton(
                    isAnimating = state.isListening,
                    isLoading = state.isMicLoading
                ) {
                    onMicClicked()
                }
            }

            if (state.showClearSaveButton) {
                ClearSaveButton(onClearClicked, onSaveClicked)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
    modifier: Modifier = Modifier,
    state: HomeState,
    onLanguageSelected: (LanguageUi) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier.padding(16.dp),
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        state.selectedLanguage?.language?.let {
            TextField(
                value = it.fullName,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                label = { Text("Select Language") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
        }


        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            state.languages.forEach { language ->
                DropdownMenuItem(
                    text = { Text(language.language.fullName) },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun MicButton(
    modifier: Modifier = Modifier,
    isAnimating: Boolean = false,
    isLoading: Boolean = false,
    onTap: () -> Unit
) {
    val alpha by rememberInfiniteTransition(label = "Alpha Transition").animateFloat(
        initialValue = 1f,
        targetValue = if (isAnimating) 0f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), // 1-second fade duration
            repeatMode = RepeatMode.Reverse
        ),
        label = "Alpha Animation"
    )

    val scale by rememberInfiniteTransition(label = "Scale Transition").animateFloat(
        initialValue = 1f,
        targetValue = if (isAnimating) 1.5f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), // 1-second fade duration
            repeatMode = RepeatMode.Reverse
        ),
        label = "Scale Animation"
    )

    val color = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(80.dp)
        ) {
            // Animated Beam Effect
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale)
            ) {
                drawCircle(
                    color = color.copy(alpha),
                    style = Stroke(width = 8f)
                )
            }

            IconButton(
                onClick = onTap,
                enabled = !isLoading,
                modifier = Modifier
                    .size(80.dp)
                    .background(color, shape = CircleShape)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_speak_now_24), // Default mic icon
                        contentDescription = "Microphone",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Text(if (isAnimating) "Listening... (Tap to stop)" else "Tap to speak")
    }
}

@Composable
fun ClearSaveButton(
    onClearClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Button(
            onClick = onClearClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = SunRed,
                contentColor = Color.White
            )
        ) {
            Text("Clear")
        }
        Button(onClick = onSaveClicked) {
            Text("Save")
        }
    }
}

@Composable
fun AlertDialogContent(
    state: AlertDialogState,
    dismissDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { if (state.isDismissable) dismissDialog() },
        confirmButton = {
            TextButton(onClick = state.positive.onAction) {
                Text(state.positive.text)
            }
        },
        dismissButton = {
            TextButton(onClick = state.negative.onAction) {
                Text(state.negative.text)
            }
        },
        title = { Text(state.title) },
        text = { Text(state.text) }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeContent(
        text = "",
        state = HomeState(recordState = RecordState.Listening),
        onTextChange = { },
        onMicClicked = { },
        onSaveClicked = { },
        onClearClicked = { },
        onLanguageSelected = {  }
    )
}