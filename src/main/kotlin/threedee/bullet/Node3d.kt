package threedee.bullet

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import ktx.math.vec3


/**
 * A nice little utility class to keep track of orientations
 * in 3d space. Can be used to attach to some kind of object
 */
open class Node3d(val transform: Matrix4) {
    val position = vec3()
    val forward = vec3()
    val up = vec3()
    val right = vec3()
    private val tmpVector = vec3()
    val currentOrientation = Quaternion()
    val backwards: Vector3
        get() = tmpVector.set(forward).rotate(Vector3.Y, 180f)
    val down: Vector3
        get() = tmpVector.set(up).rotate(Vector3.X, 180f)
    val left: Vector3
        get() = tmpVector.set(right).rotate(Vector3.Y, 180f)

    open fun getWorldTransform(worldTrans: Matrix4) {
        worldTrans.set(transform)
    }

    open fun setWorldTransform(worldTrans: Matrix4) {
        transform.set(worldTrans)
        transform.getTranslation(position)
        getDirection(transform)
    }

    open fun getDirection(transform: Matrix4) {
        forward.set(Vector3.Z)
        up.set(Vector3.Y)
        forward.rot(transform).nor()
        up.rot(transform).nor()
        right.set(forward)
        right.rotate(up, -90f)
        transform.getRotation(currentOrientation)
    }
}