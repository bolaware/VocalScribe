package com.bolaware.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bolaware.core.theme.AppTheme

class SettingsFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(SettingsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme { SettingsScreen() }
            }
        }
    }

    @Composable
    private fun SettingsScreen() {
        val text by viewModel.text.collectAsState()

        Scaffold(
            content = { padding ->
                SettingsContent(
                    text = text,
                    modifier = Modifier.padding(padding).fillMaxSize(),
                )
            }
        )
    }

    @Composable
    private fun SettingsContent(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Box(modifier = modifier) {
            Text(
                text,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}