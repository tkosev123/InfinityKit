package com.tk.infinitykit.presentation.theme

import androidx.compose.ui.graphics.Color

// === Glowing Horizon Palette ===
val YellowOrange = Color(0xFFFFB343)   // Primary warm yellow-orange
val AquaBlue = Color(0xFF42EAFF)       // Secondary aqua
val RoyalBlue = Color(0xFF4272FF)      // Tertiary accent
val VibrantOrange = Color(0xFFFF7E42)  // Complement / strong accent


// ---------------------------
// Light Theme Colors
// ---------------------------
val md_theme_light_primary = YellowOrange
val md_theme_light_onPrimary = Color.Black
val md_theme_light_primaryContainer = YellowOrange.copy(alpha = 0.25f)
val md_theme_light_onPrimaryContainer = Color(0xFF3D2A00)

val md_theme_light_secondary = AquaBlue
val md_theme_light_onSecondary = Color(0xFF00363D)
val md_theme_light_secondaryContainer = AquaBlue.copy(alpha = 0.25f)
val md_theme_light_onSecondaryContainer = Color(0xFF004E56)

val md_theme_light_tertiary = RoyalBlue
val md_theme_light_onTertiary = Color.White
val md_theme_light_tertiaryContainer = RoyalBlue.copy(alpha = 0.25f)
val md_theme_light_onTertiaryContainer = Color(0xFF0C1A58)

val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_onError = Color.White
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onErrorContainer = Color(0xFF410002)

// Light surfaces
val md_theme_light_background = Color(0xFFFFF7EC) // warm neutral
val md_theme_light_onBackground = Color(0xFF221B12)

val md_theme_light_surface = Color(0xFFFFFBF5)
val md_theme_light_onSurface = Color(0xFF221B12)

val md_theme_light_surfaceVariant = Color(0xFFFFE1B8)
val md_theme_light_onSurfaceVariant = Color(0xFF5A4630)

val md_theme_light_outline = Color(0xFF8C7A64)
val md_theme_light_inverseOnSurface = Color(0xFFFFF8F0)
val md_theme_light_inverseSurface = Color(0xFF303030)
val md_theme_light_inversePrimary = YellowOrange


// ---------------------------
// Dark Theme Colors
// ---------------------------
val md_theme_dark_primary = YellowOrange
val md_theme_dark_onPrimary = Color(0xFF3D2A00)
val md_theme_dark_primaryContainer = Color(0xFF674300)
val md_theme_dark_onPrimaryContainer = YellowOrange

val md_theme_dark_secondary = AquaBlue
val md_theme_dark_onSecondary = Color(0xFF00363D)
val md_theme_dark_secondaryContainer = Color(0xFF004E56)
val md_theme_dark_onSecondaryContainer = AquaBlue

val md_theme_dark_tertiary = RoyalBlue
val md_theme_dark_onTertiary = Color(0xFFE6E9FF)
val md_theme_dark_tertiaryContainer = Color(0xFF0C1A58)
val md_theme_dark_onTertiaryContainer = RoyalBlue

val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)

// Dark surfaces
val md_theme_dark_background = Color(0xFF1A120B)
val md_theme_dark_onBackground = Color(0xFFEEDCC3)

val md_theme_dark_surface = Color(0xFF1A120B)
val md_theme_dark_onSurface = Color(0xFFEEDCC3)

val md_theme_dark_surfaceVariant = Color(0xFF4F3D2A)
val md_theme_dark_onSurfaceVariant = Color(0xFFEBD3B8)

val md_theme_dark_outline = Color(0xFFAA8F72)
val md_theme_dark_inverseOnSurface = Color(0xFF1A120B)
val md_theme_dark_inverseSurface = Color(0xFFEEDCC3)
val md_theme_dark_inversePrimary = YellowOrange