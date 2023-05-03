package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class CharacterAnimationComponent: Component, Pool.Poolable {

    var characterAnimationState = CharacterAnimationState(AnimationController(null))

    override fun reset() {
         characterAnimationState = CharacterAnimationState(AnimationController(null))
    }

    companion object {
        val mapper = mapperFor<CharacterAnimationComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): CharacterAnimationComponent {
            return mapper.get(entity)
        }
    }

}

class CharacterAnimationStateComponent: Component, Pool.Poolable {
    var stateMachine = CharacterStateMachine(AnimationController(null))
    fun acceptEvent(event: CharacterEvent) = stateMachine.acceptEvent(event)
    fun update(deltaTime: Float) = stateMachine.update(deltaTime)

    val currentState get() = stateMachine.currentState.state


    override fun reset() {
        stateMachine = CharacterStateMachine(AnimationController(null))
    }

    companion object {
        val mapper = mapperFor<CharacterAnimationStateComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): CharacterAnimationStateComponent {
            return mapper.get(entity)
        }
    }
}