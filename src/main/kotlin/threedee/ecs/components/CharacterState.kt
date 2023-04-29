package threedee.ecs.components

data class AnimProps(val id: String, val speed: Float = 1f, val loopCount: Int = -1, val eventWhenAnimDone: CharacterEvent = CharacterEvent.GoIdle)

sealed class CharacterState(val animProps: AnimProps) {
    object Idle : CharacterState(AnimProps("idle", 0.75f, -1))
    object Walking : CharacterState(AnimProps("walking", 0.75f, -1))
    object StrafingLeft : CharacterState(AnimProps("walking", 0.75f, -1))
    object StrafingRight : CharacterState(AnimProps("walking", 0.75f, -1))
    object LowCrawling : CharacterState(AnimProps("lowcrawl", 0.75f, -1))
    object PistolWalk : CharacterState(AnimProps("pistol-walk", 0.75f, -1))
    object RifleWalk : CharacterState(AnimProps("rifle-walk", 0.75f, -1))
    object WalkingBackwards : CharacterState(AnimProps("walking-backwards", 0.75f, -1))
    object StartingWalkForwards: CharacterState(AnimProps("walking", 0.75f, 1, CharacterEvent.ContinueWalkingForwards))
}

sealed class CharacterEvent {
    object MoveForwards : CharacterEvent()
    object ContinueWalkingForwards : CharacterEvent()
    object Stop : CharacterEvent()
    object StopCrawlingKeepWalking : CharacterEvent()
    object GoIdle : CharacterEvent()
    object StartCrawling : CharacterEvent()
    object PistolWalk : CharacterEvent()
    object RifleWalk : CharacterEvent()
    object MoveBackwards : CharacterEvent()
    object StrafeLeft : CharacterEvent()
    object StrafeRight : CharacterEvent()
}