package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g3d.model.Node
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.DebugDrawer
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.vec3
import threedee.ecs.components.KeyboardControlComponent
import threedee.ecs.components.MotionStateComponent
import threedee.ecs.components.SceneComponent

class DebugRenderSystem3d(private val viewport: Viewport, private val bulletWorld: btDynamicsWorld) : IteratingSystem(
    allOf(
        MotionStateComponent::class
    ).get()
) {
    val debugDrawer = DebugDrawer().apply {
        debugMode = 1 or 2 or 4 or 8 or 16 or 32 or 64 or 128 or 256 or 512 or 1024
        bulletWorld.debugDrawer = this
    }

    val keyboardControlComponentFamily = allOf(KeyboardControlComponent::class).get()
    val controlledEntity by lazy { engine.getEntitiesFor(keyboardControlComponentFamily).first() }
    val controlComponent by lazy { KeyboardControlComponent.get(controlledEntity) }

    private val forwardColor = vec3(0f, 0f, 1f)
    private val upColor = vec3(0f, 1f, 0f)
    private val rightColor = vec3(1f, 0f, 0f)

    override fun update(deltaTime: Float) {
        debugDrawer.begin(viewport)
        bulletWorld.debugDrawWorld()
        super.update(deltaTime)
        debugDrawer.drawSphere(controlComponent.mouseWorldPosition, 0.1f, vec3(1f, 0f,0f))
        debugDrawer.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val motionState = MotionStateComponent.get(entity)
        //Draw the normals!
        if(SceneComponent.has(entity))
            drawSkeleton(entity)
        debugDrawer.drawLine(motionState.position, motionState.position + motionState.forward.cpy().scl(2f), forwardColor)
        debugDrawer.drawLine(motionState.position, motionState.position + motionState.up.cpy().scl(2f), upColor)
        debugDrawer.drawLine(motionState.position, motionState.position + motionState.right.cpy().scl(2f), rightColor)
    }

    private val sceneTranslation = vec3()
    private val parentTranslation = vec3()
    private val childTranslation = vec3()
    private fun drawNode(parent: Node, actualNode: Node, worldPosition: Vector3) {
        val pp = parent.globalTransform.getTranslation(parentTranslation)
        val cp = actualNode.globalTransform.getTranslation(childTranslation)

        pp.add(worldPosition)
        cp.add(worldPosition)
        
        debugDrawer.drawLine(pp, cp, vec3(1f, 1f, 0f))
        for (child in actualNode.children) {
            drawNode(actualNode, child, worldPosition)
        }
    }

    private fun drawSkeleton(entity: Entity) {
        val scene = SceneComponent.get(entity).scene

        if(scene.modelInstance.nodes.any()) {
            val firstNode = scene.modelInstance.nodes.first()
            firstNode.children.forEach { drawNode(firstNode, it, scene.modelInstance.transform.getTranslation(sceneTranslation)) }
        }
    }
}
