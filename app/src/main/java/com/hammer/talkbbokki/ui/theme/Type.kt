package com.hammer.talkbbokki.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hammer.talkbbokki.R

val pretendardFamily = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_medium, FontWeight.SemiBold),
    Font(R.font.pretendard_regular, FontWeight.Medium),
    Font(R.font.pretendard_thin, FontWeight.Thin)
)

// Set of Material typography styles to start with
class talkbbokkiTypography {
    companion object {
        val h1: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 40.sp
        )
        val h2_bold: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 36.sp
        )
        val h2_regular: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 36.sp
        )
        val b1_bold: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        )
        val b1_regular: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 28.sp
        )
        val b2_bold: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
        val b2_regular: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
        val b3_bold: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        val b3_regular: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        val caption: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 12.sp
        )
        val button_large: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 18.sp
        )
        val button_small_bold: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 14.sp
        )
        val button_small_regular: TextStyle = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 14.sp
        )
    }
}
