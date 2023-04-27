package threedee.ecs.components

import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import statemachine.StateMachine

class CharacterStateMachine(
    private val animationController: AnimationController,
    private val animationListener: AnimationController.AnimationListener? = null
    ) {

    private fun stateChanged(state: CharacterState, event: CharacterEvent?) {
        changeAnimation(state.animProps.id)
    }

    fun acceptEvent(event: CharacterEvent) {
        animationStateMachine.acceptEvent(event)
    }

    val animationStateMachine =
        StateMachine.buildStateMachine<CharacterState, CharacterEvent>(CharacterState.Idle, ::stateChanged) {
            state(CharacterState.Idle) {
                edge(CharacterEvent.StartWalkingForwards, CharacterState.StartingWalkForwards) {
                }
            }
            state(CharacterState.Walking) {
                edge(CharacterEvent.StopWalking, CharacterState.Idle) {

                }
            }

        }

    private fun changeAnimation(id: String) {
        animationController.setAnimation(id, -1, 1f, animationListener)
    }
}