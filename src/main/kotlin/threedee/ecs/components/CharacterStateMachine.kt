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
                edge(CharacterEvent.MoveBackwards, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StrafeRight, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StartLowCrawl, CharacterState.LowCrawling) {}
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
            }
            state(CharacterState.StartingWalkForwards) {
                edge(CharacterEvent.ContinueWalkingForwards, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.MoveBackwards, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StrafeRight, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StartLowCrawl, CharacterState.LowCrawling) {}
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
            }
            state(CharacterState.WalkingForwards) {
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
                edge(CharacterEvent.StartLowCrawl, CharacterState.LowCrawling) {}
                edge(CharacterEvent.MoveBackwards, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StrafeLeft, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.StrafeRight, CharacterState.WalkingForwards) {}
                edge(CharacterEvent.MoveForwards, CharacterState.WalkingForwards) {}
            }
            state(CharacterState.LowCrawling) {
                edge(CharacterEvent.Stop, CharacterState.Idle) {}
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