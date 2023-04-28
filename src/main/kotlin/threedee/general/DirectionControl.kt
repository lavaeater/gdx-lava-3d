package threedee.general

class DirectionControl {
    val orthogonal = mutableSetOf<Direction>()
    val rotational = mutableSetOf<Rotation>()
    val noDirection get() = orthogonal.isEmpty()
    val noRotation get() = rotational.isEmpty()

    val thrust get() = if(orthogonal.contains(Direction.Forward)) 1f else if(orthogonal.contains(Direction.Reverse)) -1f else 0f
    val strafe get() = if(orthogonal.contains(Direction.Left)) 1f else if(orthogonal.contains(Direction.Right)) -1f else 0f

    fun has(direction: Direction) : Boolean {
        return orthogonal.contains(direction)
    }

    fun has(rotation: Rotation): Boolean {
        return rotational.contains(rotation)
    }

    fun add(direction: Direction) {
        orthogonal.add(direction)
    }
    fun remove(direction: Direction) {
        orthogonal.remove(direction)
    }

    fun add(rotation: Rotation) {
        rotational.add(rotation)
    }
    fun remove(rotation: Rotation) {
        rotational.remove(rotation)
    }

    fun clear() {
        rotational.clear()
        orthogonal.clear()
    }
}