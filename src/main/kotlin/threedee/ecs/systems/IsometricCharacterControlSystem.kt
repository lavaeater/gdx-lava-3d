package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector3
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf
import ktx.math.vec3
import threedee.ecs.components.IsometricCameraFollowComponent
import threedee.ecs.components.KeyboardControlComponent
import threedee.ecs.components.SceneComponent
import threedee.general.Direction
import threedee.general.DirectionControl
import twodee.core.world
import twodee.input.KeyPress
import twodee.input.command

class IsometricCharacterControlSystem :
    IteratingSystem(
        allOf(
            IsometricCameraFollowComponent::class,
            KeyboardControlComponent::class,
            SceneComponent::class
        ).get()
    ),
    KtxInputAdapter {
    private val controlledEntity by lazy { entities.first() }
    private val controlComponent by lazy { KeyboardControlComponent.get(controlledEntity) }
    private val scene by lazy { SceneComponent.get(controlledEntity).scene }

    private val controlMap = command("Controoool") {
        setBoth(
            Input.Keys.W,
            "Throttle",
            { controlComponent.remove(Direction.Forward) },
            { controlComponent.add(Direction.Forward) }
        )
        setBoth(
            Input.Keys.S,
            "Brake",
            { controlComponent.remove(Direction.Reverse) },
            { controlComponent.add(Direction.Reverse) }
        )
        setBoth(
            Input.Keys.A,
            "Left",
            { controlComponent.remove(Direction.Left) },
            { controlComponent.add(Direction.Left) }
        )
        setBoth(
            Input.Keys.D,
            "Right",
            { controlComponent.remove(Direction.Right) },
            { controlComponent.add(Direction.Right) }
        )
//        setBoth(
//            Input.Keys.UP,
//            "Up",
//            { },
//            { cameraFollowComponent.offsetY += 0.1f }
//        )
//        setBoth(
//            Input.Keys.DOWN,
//            "Down",
//            { },
//            { cameraFollowComponent.offsetY -= 0.1f }
//        )
//        setBoth(
//            Input.Keys.LEFT,
//            "Up",
//            { },
//            { cameraFollowComponent.offsetXZ.rotateDeg(5f) }
//        )
//        setBoth(
//            Input.Keys.RIGHT,
//            "Down",
//            { },
//            { cameraFollowComponent.offsetXZ.rotateDeg(-5f) }
//        )
    }

    override fun keyDown(keycode: Int): Boolean {
        return controlMap.execute(keycode, KeyPress.Down)
    }

    override fun keyUp(keycode: Int): Boolean {
        return controlMap.execute(keycode, KeyPress.Up)
    }


    override fun update(deltaTime: Float) {
        Gdx.input.inputProcessor = this
        super.update(deltaTime)
    }

    private val directionToVector = mapOf(
        Direction.Forward to Vector3(1f, 0f, -1f),
        Direction.Reverse to Vector3(-1f, 0f, 1f),
        Direction.Left to Vector3(-1f, 0f, -1f),
        Direction.Right to Vector3(1f, 0f, 1f)
    )

    private val directionVector = vec3()
    private fun setDirectionVector(directionControl: DirectionControl) {
        if(directionControl.orthogonal.isEmpty()) {
            directionVector.setZero()
            return
        }
        directionControl.orthogonal.forEach { directionVector.add(directionToVector[it]!!) }
        directionVector.nor()
    }


    val worldPosition = vec3()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        scene.modelInstance.transform.getTranslation(worldPosition)
        setDirectionVector(controlComponent.directionControl)
        worldPosition.lerp((worldPosition + directionVector), 0.5f)
        scene.modelInstance.transform.setToWorld(worldPosition, Vector3.Z, Vector3.Y)
    }
}