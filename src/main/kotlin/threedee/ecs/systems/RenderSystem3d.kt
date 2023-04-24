package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import twodee.physics.addComponent
import ktx.ashley.allOf
import ktx.ashley.exclude
import net.mgsx.gltf.scene3d.scene.Scene
import net.mgsx.gltf.scene3d.scene.SceneManager
import threedee.ecs.components.AddedToRenderableList
import threedee.ecs.components.SceneComponent
import threedee.ecs.components.VisibleComponent

class RenderSystem3d(
    private val sceneManager: SceneManager
) : IteratingSystem(
    allOf(
        SceneComponent::class,
        VisibleComponent::class
    )
        .exclude(AddedToRenderableList::class).get()
) {
    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        renderScenes(deltaTime)
    }

    private val scenesToRender = mutableListOf<Scene>()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val sceneComponent = SceneComponent.get(entity)
        if (!sceneComponent.added) {
            scenesToRender.add(sceneComponent.scene)
            sceneComponent.added = true // why not...
            entity.addComponent<AddedToRenderableList>()
        }
    }

    private fun renderScenes(deltaTime: Float) {
        sceneManager.update(deltaTime)
        sceneManager.render()
    }
}
