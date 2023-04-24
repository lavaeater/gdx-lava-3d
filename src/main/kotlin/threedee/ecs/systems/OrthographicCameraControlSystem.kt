package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector3
import ktx.app.KtxInputAdapter
import ktx.ashley.allOf
import ktx.math.vec3
import threedee.ecs.components.CameraComponent
import threedee.ecs.components.KeyboardControlComponent
import threedee.general.Direction
import threedee.general.DirectionControl
import twodee.input.KeyPress
import twodee.input.command

class OrthographicCameraControlSystem :
    IteratingSystem(
        allOf(
            CameraComponent::class,
            KeyboardControlComponent::class
        ).get()
    ),
    KtxInputAdapter {
    private val controlledEntity by lazy { entities.first() }
    private val controlComponent by lazy { KeyboardControlComponent.get(controlledEntity) }
    private val camera by lazy { CameraComponent.get(controlledEntity).camera }

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

    val directionToVector = mapOf(
        Direction.Forward to Vector3(1f, 0f, -1f),
        Direction.Reverse to Vector3(-1f, 0f, 1f),
        Direction.Left to Vector3(-1f, 0f, -1f),
        Direction.Right to Vector3(1f, 0f, 1f)
    )

    val directionVector = vec3()
    private fun setDirectionVector(directionControl: DirectionControl) {
        if(directionControl.orthogonal.isEmpty()) {
            directionVector.setZero()
            return
        }
        directionControl.orthogonal.forEach { directionVector.add(directionToVector[it]!!) }
        directionVector.nor()
    }


    override fun processEntity(entity: Entity, deltaTime: Float) {
        setDirectionVector(controlComponent.directionControl)
        camera.position.lerp((camera.position + directionVector), 0.25f)
//
//        if (controlComponent.has(Direction.Forward)) {
//            camera.position.z -= 1f
//            camera.position.x += 1f
//        }
//        if (controlComponent.has(Direction.Reverse)) {
//            camera.position.z += 1f
//            camera.position.x -= 1f
//        }
//        if (controlComponent.has(Direction.Left)) {
//            camera.position.x -= 1f
//            camera.position.z -= 1f
//        }
//        if (controlComponent.has(Direction.Right)) {
//            camera.position.x += 1f
//            camera.position.z += 1f
//        }

        camera.update()

    }
}

private operator fun Vector3.plus(addor: Vector3): Vector3 {
    return vec3(this.x + addor.x, this.y + addor.y, this.z + addor.z)
}
