package threedee.ecs.components

import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import threedee.general.Direction
import threedee.general.Modifier

class CharacterAnimationState(
    private val animationController: AnimationController
) {
    private val animationListener: AnimationController.AnimationListener =
        object : AnimationController.AnimationListener {
            override fun onEnd(animation: AnimationController.AnimationDesc) {
            }

            override fun onLoop(animation: AnimationController.AnimationDesc) {

            }

        }

    /**
     * We need a simple criteria object that can check the charactercontrol if it is
     * in a certain state - and if so return the correct animation
     *
     * It also needs to be sort of cascading, I guess? So certain states take precedent over other states
     *
     */
    fun checkCharacterState(characterControlComponent: CharacterControlComponent) {
        if (characterControlComponent.isDirty) {
            changeAnimation(getAnimationProps(characterControlComponent))
        }
    }

    private fun getAnimationProps(characterControlComponent: CharacterControlComponent): AnimProps {
        /**
         * This is SOOO obviously something for a flag system
         */
        if (characterControlComponent.mask and Modifier.Crawling.flag != 0L) {
            return AnimPropMap.animProps["lowcrawl"]!!
        }
        if (characterControlComponent.mask and (Modifier.Aiming.flag) != 0L) {
            return AnimPropMap.animProps["rifle-walk"]!!
        }
        if(characterControlComponent.mask and (Direction.Forward.flag or Direction.Reverse.flag) != 0L) {
            return AnimPropMap.animProps["walking"]!!
        }

        return AnimPropMap.animProps["idle"]!!
    }

    private fun changeAnimation(animProps: AnimProps) {
        animationController.setAnimation(animProps.id, animProps.loopCount, animProps.speed, animationListener)
    }

    fun update(deltaTime: Float, characterControlComponent: CharacterControlComponent) {
        checkCharacterState(characterControlComponent)
        animationController.update(deltaTime)
    }


}