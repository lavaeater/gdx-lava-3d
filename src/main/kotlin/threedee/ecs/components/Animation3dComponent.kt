package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g3d.model.Animation
import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.collections.GdxArray
import net.mgsx.gltf.scene3d.animation.AnimationsPlayer
import statemachine.StateMachine

class Animation3dComponent : Component, Pool.Poolable {
    var animationController = AnimationController(null)
    var animationPlayer = AnimationsPlayer(null)
    var animations = GdxArray<Animation>()
    var currentAnimation = 1
    var animationStateMachine = StateMachine.buildStateMachine<AnimState, AnimEvent>(AnimState.Idle, {
        state, event ->
        animationController.setAnimation(state.id, -1)
    } ) {

    }

    override fun reset() {
        currentAnimation = 1
        animations = GdxArray()
        animationController = AnimationController(null)
        animationPlayer = AnimationsPlayer(null)
    }

    companion object {
        val mapper = mapperFor<Animation3dComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }

        fun get(entity: Entity): Animation3dComponent {
            return mapper.get(entity)
        }
    }
}