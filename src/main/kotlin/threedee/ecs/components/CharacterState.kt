package threedee.ecs.components

data class AnimProps(val id: String, val speed: Float = 1f, val loopCount: Int = -1)

sealed class CharacterState(val animProps: AnimProps) {
    object Idle : CharacterState(AnimProps("idle", 1f, -1))
    object Walking : CharacterState(AnimProps("walking", 1f, -1))
    object LowCrawl : CharacterState(AnimProps("lowcrawl", 1f, -1))
    object PistolWalk : CharacterState(AnimProps("pistol-walk", 1f, -1))
    object RifleWalk : CharacterState(AnimProps("rifle-walk", 1f, -1))
    object WalkingBackwards : CharacterState(AnimProps("walking-backwards", 1f, -1))
    object StartingWalkForwards: CharacterState(AnimProps("start-walking-forwards", 1f, 1))
}

sealed class CharacterEvent {
    object StartWalkingForwards : CharacterEvent()
    object ContinueWalkingForwards : CharacterEvent()
    object StopWalking : CharacterEvent()
    object LowCrawl : CharacterEvent()
    object PistolWalk : CharacterEvent()
    object RifleWalk : CharacterEvent()
    object WalkBackwards : CharacterEvent()
}