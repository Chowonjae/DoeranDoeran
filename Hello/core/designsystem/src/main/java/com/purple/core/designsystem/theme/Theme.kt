package com.purple.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = purple_theme_light_primary,
    onPrimary = purple_theme_light_onPrimary,
    primaryContainer = purple_theme_light_primaryContainer,
    onPrimaryContainer = purple_theme_light_onPrimaryContainer,
    secondary = purple_theme_light_secondary,
    onSecondary = purple_theme_light_onSecondary,
    secondaryContainer = purple_theme_light_secondaryContainer,
    onSecondaryContainer = purple_theme_light_onSecondaryContainer,
    tertiary = purple_theme_light_tertiary,
    onTertiary = purple_theme_light_onTertiary,
    tertiaryContainer = purple_theme_light_tertiaryContainer,
    onTertiaryContainer = purple_theme_light_onTertiaryContainer,
    error = purple_theme_light_error,
    onError = purple_theme_light_onError,
    errorContainer = purple_theme_light_errorContainer,
    onErrorContainer = purple_theme_light_onErrorContainer,
    outline = purple_theme_light_outline,
    background = purple_theme_light_background,
    onBackground = purple_theme_light_onBackground,
    surface = purple_theme_light_surface,
    onSurface = purple_theme_light_onSurface,
    surfaceVariant = purple_theme_light_surfaceVariant,
    onSurfaceVariant = purple_theme_light_onSurfaceVariant,
    inverseSurface = purple_theme_light_inverseSurface,
    inverseOnSurface = purple_theme_light_inverseOnSurface,
    inversePrimary = purple_theme_light_inversePrimary,
    surfaceTint = purple_theme_light_surfaceTint,
    outlineVariant = kakao_button,
)

@Composable
fun HiTheme(
    content: @Composable () -> Unit,
) {
    val hiGradientColors = GradientColors(
        top = colorScheme.background,
        bottom = colorScheme.secondaryContainer,
        container = Color.Unspecified,
    )

    val hiBackgroundTheme = BackgroundTheme(color = colorScheme.background)

    CompositionLocalProvider(
        LocalGradientColors provides hiGradientColors,
        LocalBackgroundTheme provides hiBackgroundTheme,
    ) {
        MaterialTheme(
            colorScheme = LightColors,
            typography = HiTypography,
            content = content,
        )
    }
}
