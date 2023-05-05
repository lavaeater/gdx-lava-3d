package threedee.bullet

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState


class MotionState(val transform: Matrix4) : btMotionState() {
    private val node = Node3d(transform)

    val position get() = node.position
    val forward get() =  node.forward
    val up get() = node.up
    val right get() =  node.right

    val currentOrientation get() = node.currentOrientation

    val backwards get() = node.backwards
    val down get() = node.down
    val left get() = node.left

    override fun getWorldTransform(worldTrans: Matrix4) {
        node.getWorldTransform(worldTrans)
    }

    override fun setWorldTransform(worldTrans: Matrix4) {
        node.setWorldTransform(worldTrans)
    }
}