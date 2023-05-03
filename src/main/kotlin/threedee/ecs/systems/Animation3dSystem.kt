package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import threedee.ecs.components.CharacterAnimationComponent
import threedee.ecs.components.CharacterAnimationStateComponent
import threedee.ecs.components.CharacterControlComponent

class Animation3dSystem : IteratingSystem(
    allOf(
        CharacterAnimationComponent::class,
        CharacterControlComponent::class
    ).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val characterAnimationComponent = CharacterAnimationComponent.get(entity)
        characterAnimationComponent.characterAnimationState.update(deltaTime, CharacterControlComponent.get(entity))
    }

}