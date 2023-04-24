package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class CameraComponent: Component, Pool.Poolable {
    private var _camera: Camera? = null
    var camera: Camera
        get() = _camera!!
        set(value) {
            _camera = value
        }

    override fun reset() {
        _camera = null
    }

    companion object {
        val mapper = mapperFor<CameraComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): CameraComponent {
            return mapper.get(entity)
        }
    }
}