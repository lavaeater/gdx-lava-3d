package threedee.ecs.components

sealed class CharacterState(val id: String) {
    object Idle : CharacterState("idle")
    object Walking : CharacterState("walking")
    object LowCrawl : CharacterState("lowcrawl")
    object PistolWalk : CharacterState("pistol-walk")
    object RifleWalk : CharacterState("rifle-walk")
    object WalkingBackwards : CharacterState("walking-backwards")
}

sealed class CharacterEvent {
    object Walk : CharacterEvent()
    object Stop : CharacterEvent()
    object LowCrawl : CharacterEvent()
    object PistolWalk : CharacterEvent()
    object RifleWalk : CharacterEvent()
    object WalkBackwards : CharacterEvent()
}