package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec3
import threedee.general.Direction
import threedee.general.DirectionControl
import threedee.general.Rotation

class KeyboardControlComponent : Component, Pool.Poolable {

    val directionControl = DirectionControl()

    val thrust get() = directionControl.thrust
    val strafe get() = directionControl.strafe

    fun has(direction: Direction): Boolean {
        return directionControl.has(direction)
    }

    fun has(rotation: Rotation): Boolean {
        return directionControl.has(rotation)
    }

    fun add(direction: Direction) {
        directionControl.add(direction)
    }

    fun remove(direction: Direction) {
        directionControl.remove(direction)
    }

    fun add(rotation: Rotation) {
        directionControl.add(rotation)
    }

    fun remove(rotation: Rotation) {
        directionControl.remove(rotation)
    }

    val mouseWorldPosition = vec3()

    override fun reset() {
        directionControl.clear()
    }

    val intersection = vec3()
    val lookDirection = vec3()
    val forward get() = lookDirection
    var left = vec3()
        get() {
            field.set(lookDirection)
            field.rotate(Vector3.Y, 90f)
            return field
        }
    private set

    var right = vec3()
        get() {
            field.set(lookDirection)
            field.rotate(Vector3.Y, -90f)
            return field
        }
        private set

    var backwards = vec3()
        get() {
            field.set(lookDirection)
            field.rotate(Vector3.Y, 180f)
            return field
        }
        private set

    companion object {
        val mapper = mapperFor<KeyboardControlComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }

        fun get(entity: Entity): KeyboardControlComponent {
            return mapper.get(entity)
        }
    }
}
