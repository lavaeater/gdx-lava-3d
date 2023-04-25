package threedee.bullet

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState
import ktx.math.vec3

class MotionState(val transform: Matrix4) : btMotionState() {
    val position = vec3()
    val forward = vec3()
    val up = vec3()
    val right = vec3()
    private val tmpVector = vec3()

    val backwards: Vector3
        get() = tmpVector.set(forward).rotate(Vector3.Y, 180f)
    val down: Vector3
        get() = tmpVector.set(up).rotate(Vector3.X, 180f)
    val left: Vector3
        get() = tmpVector.set(right).rotate(Vector3.Y, 180f)

    override fun getWorldTransform(worldTrans: Matrix4) {
        worldTrans.set(transform)
    }

    override fun setWorldTransform(worldTrans: Matrix4) {
        transform.set(worldTrans)
        transform.getTranslation(position)
        getDirection(transform)
    }

    private fun getDirection(transform: Matrix4?) {
        forward.set(Vector3.Z)
        up.set(Vector3.Y)
        forward.rot(transform).nor()
        up.rot(transform).nor()
        right.set(forward)
        right.rotate(up, -90f)
    }
}