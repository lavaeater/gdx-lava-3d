package threedee.ecs.components

import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import statemachine.StateMachine

class CharacterStateMachine(
    private val animationController: AnimationController
) {

    private val animationListener: AnimationController.AnimationListener =
        object : AnimationController.AnimationListener {
            override fun onEnd(animation: AnimationController.AnimationDesc) {
                acceptEvent(currentAnimProps.eventWhenAnimDone)
            }

            override fun onLoop(animation: AnimationController.AnimationDesc) {

            }

        }

    var currentAnimProps = CharacterState.Idle.animProps
    private fun stateChanged(state: CharacterState, event: CharacterEvent?) {
        if (event != null) {
            currentAnimProps = state.animProps
            changeAnimation(state.animProps)
        }
    }

    fun update(deltaTime: Float) {
        animationController.update(deltaTime)
    }

    fun acceptEvent(event: CharacterEvent) {
        animationStateMachine.acceptEvent(event)
    }

    val animationStateMachine =
        StateMachine.buildStateMachine<CharacterState, CharacterEvent>(CharacterState.Idle, ::stateChanged) {
            state(CharacterState.Idle) {
                edge(CharacterEvent.MoveForwards, CharacterState.StartingWalkForwards) {}
                edge(CharacterEvent.MoveBackwards, CharacterState.Walking) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.Walking) {}
                edge(CharacterEvent.StrafeRight, CharacterState.Walking) {}
                edge(CharacterEvent.StartCrawling, CharacterState.LowCrawling) {}
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
            }
            state(CharacterState.StartingWalkForwards) {
                edge(CharacterEvent.ContinueWalkingForwards, CharacterState.Walking) {}
                edge(CharacterEvent.MoveBackwards, CharacterState.Walking) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.Walking) {}
                edge(CharacterEvent.StrafeRight, CharacterState.Walking) {}
                edge(CharacterEvent.StartCrawling, CharacterState.LowCrawling) {}
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
            }
            state(CharacterState.Walking) {
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
                edge(CharacterEvent.StartCrawling, CharacterState.LowCrawling) {}
                edge(CharacterEvent.MoveBackwards, CharacterState.Walking) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.Walking) {}
                edge(CharacterEvent.StrafeRight, CharacterState.Walking) {}
                edge(CharacterEvent.MoveForwards, CharacterState.Walking) {}
            }
            state(CharacterState.LowCrawling) {
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
                edge(CharacterEvent.StopCrawlingKeepWalking, CharacterState.Walking) {}
                edge(CharacterEvent.MoveForwards, CharacterState.LowCrawling) {}
                edge(CharacterEvent.MoveBackwards, CharacterState.LowCrawling) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.LowCrawling) {}
                edge(CharacterEvent.StrafeRight, CharacterState.LowCrawling) {}
            }
        }.apply { initialize() }
    val currentState get() = animationStateMachine.currentState

    private fun changeAnimation(animProps: AnimProps) {
        animationController.setAnimation(animProps.id, animProps.loopCount, animProps.speed, animationListener)
    }
}