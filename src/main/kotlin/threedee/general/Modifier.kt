package threedee.general

sealed class Modifier {
    object Aiming: Modifier()
    object Crouching: Modifier()
    object Crawling: Modifier()
    object Shooting: Modifier()

    val flag by lazy { FlagManager.registerModifier(this) }
}