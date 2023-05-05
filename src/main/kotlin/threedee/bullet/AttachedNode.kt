package threedee.bullet

import com.badlogic.gdx.graphics.g3d.model.Node
import com.badlogic.gdx.math.Matrix4

class AttachedNode(val node: Node) : Node3d(node.globalTransform) {
    fun update() {
        setWorldTransform(node.globalTransform)
    }
}