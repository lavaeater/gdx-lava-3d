package threedee.general

sealed class Direction {
    object Left : Direction()
    object Right : Direction()
    object Up : Direction()
    object Down : Direction()
    object Forward : Direction()
    object Reverse : Direction()
    object Neutral : Direction()

    val flag by lazy {
        FlagManager.registerDirection(this)
    }
}


