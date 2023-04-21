package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import general.Rotation
import ktx.ashley.mapperFor
import general.Direction
import general.DirectionControl

class KeyboardControlComponent: Component, Pool.Poolable {

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
