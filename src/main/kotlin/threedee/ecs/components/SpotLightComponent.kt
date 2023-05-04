package threedee.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec3
import net.mgsx.gltf.scene3d.lights.SpotLightEx
import threedee.general.Direction

class SpotLightComponent: Component, Pool.Poolable {
    val offset = vec3()
    var offsetDirection: Direction = Direction.Forward
    var spotLightEx: SpotLightEx = SpotLightEx()
    override fun reset() {
        offsetDirection = Direction.Forward
        offset.setZero()
        spotLightEx = SpotLightEx()
    }

    companion object {
        val mapper = mapperFor<SpotLightComponent>()
        fun has(entity: Entity): Boolean {
            return mapper.has(entity)
        }
        fun get(entity: Entity): SpotLightComponent {
            return mapper.get(entity)
        }
    }
}
