package com.bolaware.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bolaware.core.R

// Set of Material typography styles to start with

private val CircularStd = FontFamily(
    Font(R.font.circular_std_book),
    Font(R.font.circular_std_medium, FontWeight.W700),
    Font(R.font.circular_std_black, FontWeight.W900),
    Font(R.font.circular_std_bold, FontWeight.W800)
)

val Typography = Typography(
    // Headline styles (Large titles)
    headlineLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),

    // Title styles (For AppBar, Cards, or Section Titles)
    titleLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W800,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W800,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleSmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    // Body text (For paragraphs, descriptions, and regular text)
    bodyLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // Labels (For buttons, captions, helper text)
    labelLarge = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = CircularStd,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )
)