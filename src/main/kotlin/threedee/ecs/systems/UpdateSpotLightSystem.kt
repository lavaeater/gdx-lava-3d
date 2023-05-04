package threedee.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector3
import ktx.ashley.allOf
import ktx.math.plus
import ktx.math.vec3
import threedee.ecs.components.CharacterControlComponent
import threedee.ecs.components.MotionStateComponent
import threedee.ecs.components.SpotLightComponent
import threedee.general.Direction

class UpdateSpotLightSystem : IteratingSystem(
    allOf(
        SpotLightComponent::class,
        CharacterControlComponent::class,
        MotionStateComponent::class
    )
        .get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val spotLightComponent = SpotLightComponent.get(entity)
        val controlComponent = CharacterControlComponent.get(entity)
        val direction = controlComponent.lookDirection.cpy()
        val motionState = MotionStateComponent.get(entity)
        val position = motionState.position.cpy()
//        val offsetDirection = when (spotLightComponent.offsetDirection) {
//            Direction.Down -> motionState.down.cpy()
//            Direction.Forward -> motionState.forward.cpy()
//            Direction.Left -> motionState.left.cpy()
//            Direction.Neutral -> vec3()
//            Direction.Reverse -> motionState.backwards.cpy()
//            Direction.Right -> motionState.right.cpy()
//            Direction.Up -> motionState.up.cpy()
//        }.scl(spotLightComponent.offset)

        spotLightComponent.spotLightEx.setPosition(position + spotLightComponent.offset.cpy().scl(direction))
        spotLightComponent.spotLightEx.setPosition(spotLightComponent.spotLightEx.position.x, spotLightComponent.spotLightEx.position.y + 2f, spotLightComponent.spotLightEx.position.z)

        spotLightComponent.spotLightEx.setDirection(direction.rotate(motionState.left, 15f))
    }
}
