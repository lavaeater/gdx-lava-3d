package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.ashley.allOf
import ktx.math.vec3
import threedee.ecs.components.Camera3dFollowComponent
import threedee.ecs.components.IsometricCameraFollowComponent
import threedee.ecs.components.SceneComponent

class UpdateOrthographicCameraSystem(
    private val orthographicCamera: OrthographicCamera
) :
    IteratingSystem(
        allOf(
            SceneComponent::class,
            IsometricCameraFollowComponent::class
        ).get()
    ) {

    val target = vec3()
    override fun processEntity(entity: Entity, deltaTime: Float) {

        SceneComponent.get(entity).scene.modelInstance.transform.getTranslation(target)
        target.add(1f, 2.5f, 1f)
        orthographicCamera.position.lerp(target, 0.2f)
        orthographicCamera.update()
    }
}
