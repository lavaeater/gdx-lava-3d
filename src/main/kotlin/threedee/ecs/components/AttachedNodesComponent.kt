package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import threedee.bullet.AttachedNode

class AttachedNodesComponent: Component, Pool.Poolable {
    val attachedNodes = mutableListOf<AttachedNode>()
    override fun reset() {
        attachedNodes.clear()
    }

    companion object {
        val mapper = mapperFor<AttachedNodesComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): AttachedNodesComponent {
            return mapper.get(entity)
        }
    }
}