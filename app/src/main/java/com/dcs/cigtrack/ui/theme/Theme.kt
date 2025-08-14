package com.dcs.cigtrack.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val StitchLightColorScheme = lightColorScheme(
    primary = Primary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onBackground = OnBackground,
    onSurface = OnSurface
    /* Other default colors to override
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSecondary = Color.White,
    onTertiary = Color.White,
    */
)

@Composable
fun CigTrackTheme(
    //darkTheme: Boolean = isSystemInDarkTheme(), // Force light theme
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true, // Force light theme
    content: @Composable () -> Unit
) {
    // Force light theme
    val colorScheme = StitchLightColorScheme

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}