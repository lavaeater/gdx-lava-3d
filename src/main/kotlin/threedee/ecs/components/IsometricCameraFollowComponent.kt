package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec2
import ktx.math.vec3

class IsometricCameraFollowComponent:Component, Pool.Poolable {
    val offsetDirection = vec3(0f, 0f, -1f)
    val offsetXZ = vec2(0f, 50f)
    var offsetY = 50f

    val offset = vec3()
    override fun reset() {
        offsetDirection.set(0f, 0f, -1f)
        offset.setZero()
    }

    companion object {
        val mapper = mapperFor<IsometricCameraFollowComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): IsometricCameraFollowComponent {
            return mapper.get(entity)
        }
    }
}
