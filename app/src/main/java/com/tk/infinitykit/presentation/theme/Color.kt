package com.tk.infinitykit.presentation.theme

import androidx.compose.ui.graphics.Color

// === Base Brand Colors ===
val Primary = Color(0xFF0C0058)    // deep indigo
val DarkPrimary = Color(0xFF3E2ACD)

val Secondary = Color(0xFFFF7322)  // coral red

// Additional matched colors
val SoftLilac = Color(0xFFE3D7FF)
val DeepMagenta = Color(0xFFB20070)
val WarmGold = Color(0xFFFFC857)
val TealGreen = Color(0xFF2AC5A8)


// ---------------------------
// Light Theme Colors
// ---------------------------
val md_theme_light_primary = Primary
val md_theme_light_onPrimary = Color.White
val md_theme_light_primaryContainer = Primary.copy(alpha = 0.20f)
val md_theme_light_onPrimaryContainer = SoftLilac

val md_theme_light_secondary = Secondary
val md_theme_light_onSecondary = Color.White
val md_theme_light_secondaryContainer = Secondary.copy(alpha = 0.20f)
val md_theme_light_onSecondaryContainer = Color(0xFF4A0000)

val md_theme_light_tertiary = TealGreen
val md_theme_light_onTertiary = Color.Black
val md_theme_light_tertiaryContainer = TealGreen.copy(alpha = 0.20f)
val md_theme_light_onTertiaryContainer = Color(0xFF003A2E)

val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_onError = Color.White
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onErrorContainer = Color(0xFF410002)

val md_theme_light_background = Color(0xFFF8F6FF)  // light cool neutral to fit Primary
val md_theme_light_onBackground = Color(0xFF1B1724)

val md_theme_light_surface = Color(0xFFFDFBFF)
val md_theme_light_onSurface = Color(0xFF1B1724)

val md_theme_light_surfaceVariant = SoftLilac.copy(alpha = 0.55f)
val md_theme_light_onSurfaceVariant = Color(0xFF4C4560)

val md_theme_light_outline = Color(0xFF7D738C)
val md_theme_light_inverseOnSurface = Color(0xFFF6F0FF)
val md_theme_light_inverseSurface = Color(0xFF302940)
val md_theme_light_inversePrimary = Primary


// ---------------------------
// Dark Theme Colors
// ---------------------------
val md_theme_dark_primary = DarkPrimary
val md_theme_dark_onPrimary = SoftLilac
val md_theme_dark_primaryContainer = Color(0xFF302A73)
val md_theme_dark_onPrimaryContainer = Primary

val md_theme_dark_secondary = Secondary
val md_theme_dark_onSecondary = Color(0xFF3A0000)
val md_theme_dark_secondaryContainer = Color(0xFFB44141)
val md_theme_dark_onSecondaryContainer = Secondary

val md_theme_dark_tertiary = TealGreen
val md_theme_dark_onTertiary = Color(0xFF003A2E)
val md_theme_dark_tertiaryContainer = Color(0xFF006856)
val md_theme_dark_onTertiaryContainer = TealGreen

val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)

val md_theme_dark_background = Color(0xFF0D0A15)
val md_theme_dark_onBackground = Color(0xFFE8E1F5)

val md_theme_dark_surface = Color(0xFF0D0A15)
val md_theme_dark_onSurface = Color(0xFFE8E1F5)

val md_theme_dark_surfaceVariant = Color(0xFF3E3952)
val md_theme_dark_onSurfaceVariant = Color(0xFFCFC6E3)

val md_theme_dark_outline = Color(0xFF968EAA)
val md_theme_dark_inverseOnSurface = Color(0xFF0F0C18)
val md_theme_dark_inverseSurface = Color(0xFFE8E1F5)
val md_theme_dark_inversePrimary = Primary