package threedee.ecs.components

sealed class AnimState(val id: String) {
    object Idle : AnimState("idle")
    object Walking : AnimState("walking")
    object LowCrawl : AnimState("lowcrawl")
    object PistolWalk : AnimState("pistol-walk")
    object RifleWalk : AnimState("rifle-walk")
    object WalkingBackwards : AnimState("walking-backwards")
}

sealed class AnimEvent {
    object Walk : AnimEvent()
    object Stop : AnimEvent()
    object LowCrawl : AnimEvent()
    object PistolWalk : AnimEvent()
    object RifleWalk : AnimEvent()
    object WalkBackwards : AnimEvent()
}