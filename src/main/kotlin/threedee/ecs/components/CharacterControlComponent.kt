package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec3
import threedee.general.Direction
import threedee.general.CharacterControl
import threedee.general.Modifier
import threedee.general.Rotation

class CharacterControlComponent : Component, Pool.Poolable {

    private var dirty = false
    val characterControl = CharacterControl()

    val thrust get() = characterControl.thrust
    val strafe get() = characterControl.strafe

    /**
     * Whenever we check this, it is automatically reset, until next time we check it,
     * yay for my brilliant brain
     */
    val isDirty: Boolean
        get() {
            val result = dirty
            dirty = false
            return result
        }

    val mask: Long
        get() = characterControl.mask

    fun has(direction: Direction): Boolean {
        return characterControl.has(direction)
    }

    fun has(rotation: Rotation): Boolean {
        return characterControl.has(rotation)
    }

    fun add(direction: Direction) {
        dirty = dirty || characterControl.add(direction)
    }

    fun remove(direction: Direction) {
        dirty = dirty || characterControl.remove(direction)
    }

    fun add(rotation: Rotation) {
        dirty = dirty || characterControl.add(rotation)
    }

    fun remove(rotation: Rotation) {
        dirty = dirty || characterControl.remove(rotation)
    }

    fun add(modifier: Modifier) {
        dirty = dirty || characterControl.add(modifier)
    }

    fun remove(modifier: Modifier) {
        dirty = dirty || characterControl.remove(modifier)
    }

    fun has(modifier: Modifier): Boolean {
        return characterControl.has(modifier)
    }

    val hasNoDirection: Boolean get() = characterControl.noDirection
    val noRotation: Boolean get() = characterControl.noRotation
    val noModifiers: Boolean get() = characterControl.noModifiers

    val mouseWorldPosition = vec3()

    override fun reset() {
        dirty = false
        characterControl.clear()
    }

    fun toggle(modifier: Modifier) {
        if(characterControl.has(modifier))
            characterControl.remove(modifier)
        else
            characterControl.add(modifier)
        dirty = true
    }

    fun clearDirections() {
        dirty = dirty || characterControl.clearDirections()
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
        val mapper = mapperFor<CharacterControlComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }

        fun get(entity: Entity): CharacterControlComponent {
            return mapper.get(entity)
        }
    }
}
