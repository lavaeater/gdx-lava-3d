package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import threedee.ecs.components.Animation3dComponent
import threedee.ecs.components.SceneComponent

class Animation3dSystem : IteratingSystem(allOf(SceneComponent::class, Animation3dComponent::class).get()) {
    private var stateTime = 0f
    override fun processEntity(entity: Entity, deltaTime: Float) {
        stateTime += deltaTime
        val sceneComponent = SceneComponent.get(entity)
        val animation3dComponent = Animation3dComponent.get(entity)
        sceneComponent.scene.animationController.update(deltaTime)

    }

}