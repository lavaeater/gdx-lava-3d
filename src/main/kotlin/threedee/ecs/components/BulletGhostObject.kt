package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.bullet.collision.btGhostObject
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class BulletGhostObject: Component, Pool.Poolable {
    private var _ghostObject: btGhostObject? = null
    var ghostObject: btGhostObject
        get() = _ghostObject!!
        set(value) {
            _ghostObject = value
        }


    override fun reset() {
        _ghostObject = null
    }

    companion object {
        val mapper = mapperFor<BulletGhostObject>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): BulletGhostObject {
            return mapper.get(entity)
        }
    }
}