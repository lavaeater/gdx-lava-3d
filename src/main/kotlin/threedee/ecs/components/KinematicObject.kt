package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class KinematicObject: Component, Pool.Poolable {
    private var _kinematicObject: btRigidBody? = null
    var kinematicBody: btRigidBody
        get() = _kinematicObject!!
        set(value) {
            _kinematicObject = value
        }


    override fun reset() {
        _kinematicObject = null
    }

    companion object {
        val mapper = mapperFor<KinematicObject>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): KinematicObject {
            return mapper.get(entity)
        }
    }
}