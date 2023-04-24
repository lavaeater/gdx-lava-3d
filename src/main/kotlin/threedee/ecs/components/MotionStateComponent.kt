package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor
import threedee.bullet.MotionState

class MotionStateComponent : Component, Poolable {

    private var _motionState: MotionState? = null
    var motionState: MotionState
        get() = _motionState!!
        set(value) {
            _motionState = value
        }

    val position get() = motionState.position
    val forward get() = motionState.forward
    val up get() = motionState.up
    val right get() = motionState.right
    val backwards get() = motionState.backwards
    val down get() = motionState.down
    val left get() = motionState.left


    override fun reset() {
        _motionState?.dispose()
    }

    companion object {
        val mapper = mapperFor<MotionStateComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }

        fun get(entity: Entity): MotionStateComponent {
            return mapper.get(entity)
        }
    }
}
