package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec3
import threedee.general.Direction
import threedee.general.DirectionControl
import threedee.general.Rotation

class KeyboardControlComponent: Component, Pool.Poolable {

    val lookDirection = vec3()
    val directionControl = DirectionControl()
    fun has(direction: Direction) : Boolean {
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
