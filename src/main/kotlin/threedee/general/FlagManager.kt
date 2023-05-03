package threedee.general

class FlagManager {
    var lastFlag = 0L
    fun getNextFlag() : Long {
        lastFlag = if (lastFlag == 0L)
            1L
        else
            lastFlag shl 1

        return lastFlag
    }

    fun registerModifier(modifier: Modifier) : Long {
        val flag = getNextFlag()
        modifierFlags[modifier] = flag
        return flag
    }

    fun registerDirection(direction: Direction) : Long {
        val flag = getNextFlag()
        directionFlags[direction] = flag
        return flag
    }

    fun registerRotation(rotation: Rotation) : Long {
        val flag = getNextFlag()
        rotationFlags[rotation] = flag
        return flag
    }

    fun flag(modifier: Modifier): Long{
        return modifierFlags[modifier]!!
    }

    fun flag(direction: Direction): Long{
        return directionFlags[direction]!!
    }

    fun flag(rotation: Rotation): Long{
        return rotationFlags[rotation]!!
    }

    val modifierFlags = mutableMapOf<Modifier, Long>()
    val flagModifiers get() = modifierFlags.entries.associateBy({it.value}, {it.key})

    val directionFlags = mutableMapOf<Direction, Long>()
    val flagDirections get() = directionFlags.entries.associateBy({it.value}, {it.key})

    val rotationFlags = mutableMapOf<Rotation, Long>()
    val flagRotations get() = rotationFlags.entries.associateBy({it.value}, {it.key})

    companion object {
        val instance by lazy { FlagManager() }
        fun registerDirection(direction: Direction):Long = instance.registerDirection(direction)
        fun registerRotation(rotation: Rotation):Long = instance.registerRotation(rotation)
        fun registerModifier(modifier: Modifier):Long = instance.registerModifier(modifier)
    }
}