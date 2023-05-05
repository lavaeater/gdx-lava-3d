package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import threedee.ecs.components.AttachedNodesComponent

class UpdateAttachedNodesSystem:IteratingSystem(allOf(AttachedNodesComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val anc = AttachedNodesComponent.get(entity)
        for(attachedNode in anc.attachedNodes) {
            attachedNode.update()
        }
    }
}