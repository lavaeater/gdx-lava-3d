package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import threedee.ecs.components.Animation3dComponent
import threedee.ecs.components.CharacterAnimationStateComponent

class Animation3dSystem : IteratingSystem(allOf(CharacterAnimationStateComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animation3dComponent = Animation3dComponent.get(entity)
        animation3dComponent.animationController.update(deltaTime)
    }

}