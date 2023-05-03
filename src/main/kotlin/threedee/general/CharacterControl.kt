package threedee.general

class CharacterControl {
    val modifiers = mutableSetOf<Modifier>()
    val orthogonal = mutableSetOf<Direction>()
    val rotational = mutableSetOf<Rotation>()
    val noDirection get() = orthogonal.isEmpty()
    val noRotation get() = rotational.isEmpty()
    val noModifiers get() = modifiers.isEmpty()

    val thrust get() = if(orthogonal.contains(Direction.Forward)) 1f else if(orthogonal.contains(Direction.Reverse)) -1f else 0f
    val strafe get() = if(orthogonal.contains(Direction.Left)) 1f else if(orthogonal.contains(Direction.Right)) -1f else 0f

    val mask: Long
        get() {
            var result = 0L
            for (direction in orthogonal) {
                result = result or direction.flag
            }
            for (rotation in rotational) {
                result = result or rotation.flag
            }
            for (modifier in modifiers) {
                result = result or modifier.flag
            }
            return result
        }

    fun has(direction: Direction) : Boolean {
        return orthogonal.contains(direction)
    }

    fun has(rotation: Rotation): Boolean {
        return rotational.contains(rotation)
    }

    fun add(direction: Direction) : Boolean {
        return orthogonal.add(direction)
    }

    fun remove(direction: Direction): Boolean  {
        return orthogonal.remove(direction)
    }

    fun add(rotation: Rotation): Boolean  {
        return rotational.add(rotation)
    }

    fun remove(rotation: Rotation) : Boolean {
        return rotational.remove(rotation)
    }

    fun add(modifier: Modifier): Boolean  {
        return modifiers.add(modifier)
    }

    fun remove(modifier: Modifier) : Boolean {
        return modifiers.remove(modifier)
    }

    fun has(modifier: Modifier): Boolean {
        return modifiers.contains(modifier)
    }

    fun clear() {
        rotational.clear()
        orthogonal.clear()
        modifiers.clear()
    }
}