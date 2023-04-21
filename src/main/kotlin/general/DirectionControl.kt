package general

class DirectionControl {
    val orthogonal = mutableSetOf<Direction>()
    val rotational = mutableSetOf<Rotation>()

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