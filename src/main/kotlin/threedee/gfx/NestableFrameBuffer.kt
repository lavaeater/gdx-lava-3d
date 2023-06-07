package threedee.gfx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.glutils.FrameBuffer


/*
 * Copyright 2020 damios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



/**
 * An implementation of libGDX's [FrameBuffer] that supports nested
 * framebuffers. This allows using multiple framebuffers inside each other:
 *
 * <pre>
 * `fbo0.begin();
 * // Stuff is rendered into fbo0
 * fbo1.begin();
 * // Stuff is rendered into fbo1
 * fbo1.end();
 * // Stuff is rendered into fbo0 again
 * // this is where the default FrameBuffer implementation would break
 * fbo0.end();
` *
</pre> *
 *
 * @author damios
 * @see [The
 * wiki entry detailing the reasoning behind the implementation](https://github.com/crykn/libgdx-screenmanager/wiki/Custom-FrameBuffer-implementation)
 */
class NestableFrameBuffer : FrameBuffer {
    private var previousFBOHandle = -1
    private var previousViewport = IntArray(4)
    var isBound = false
        private set
    private val hasDepth: Boolean

    /**
     * Creates a new NestableFrameBuffer having the given dimensions and
     * potentially a depth buffer attached.
     *
     * @param format
     * the format of the color buffer; according to the OpenGL ES 2.0
     * spec, only [Format.RGB565], [Format.RGBA4444] and
     * `RGB5_A1` are color-renderable
     * @param width
     * the width of the framebuffer in pixels
     * @param height
     * the height of the framebuffer in pixels
     * @param hasDepth
     * whether to attach a depth buffer
     */
    @JvmOverloads
    constructor(
        format: Pixmap.Format?, width: Int, height: Int,
        hasDepth: Boolean, hasStencil: Boolean = false
    ) : super(format, width, height, hasDepth, hasStencil) {
        this.hasDepth = hasDepth
    }

    protected constructor(bufferBuilder: NestableFrameBufferBuilder) : super(bufferBuilder) {
        hasDepth = bufferBuilder.hasDepthRenderBuffer()
    }

    /**
     * Binds the framebuffer and sets the viewport accordingly, so everything
     * gets drawn to it.
     */
    override fun begin() {
        Preconditions.checkState(
            !isBound,
            "end() has to be called before another draw can begin!"
        )
        isBound = true
        previousFBOHandle = GLUtils.boundFboHandle
        bind()
        previousViewport = GLUtils.viewport
        setFrameBufferViewport()
    }

    /**
     * Makes the framebuffer current so everything gets drawn to it.
     *
     *
     * The static [.unbind] method is always rebinding the
     * *default* framebuffer afterwards.
     *
     * @see .begin
     */
    @Deprecated(
        """Doesn't support nesting!
	  """
    )
    override fun bind() {
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, framebufferHandle)
    }

    /**
     * Unbinds the framebuffer, all drawing will be performed to the
     * [previous framebuffer][.previousFBOHandle] (usually the normal
     * one) from here on.
     */
    override fun end() {
        end(
            previousViewport[0], previousViewport[1], previousViewport[2],
            previousViewport[3]
        )
    }

    /**
     * Unbinds the framebuffer and sets viewport sizes, all drawing will be
     * performed to the [previous framebuffer][.previousFBOHandle]
     * (usually the normal one) from here on.
     *
     * @param x
     * the x-axis position of the viewport in pixels
     * @param y
     * the y-asis position of the viewport in pixels
     * @param width
     * the width of the viewport in pixels
     * @param height
     * the height of the viewport in pixels
     */
    override fun end(x: Int, y: Int, width: Int, height: Int) {
        Preconditions.checkState(isBound, "begin() has to be called first!")
        isBound = false
        check(GLUtils.boundFboHandle == framebufferHandle) {
            ("The currently bound framebuffer ("
                    + GLUtils.boundFboHandle
                    + ") doesn't match this one. Make sure the nested framebuffers are closed in the same order they were opened in!")
        }
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, previousFBOHandle)
        Gdx.gl20.glViewport(x, y, width, height)
    }

    override fun build() {
        val previousFBOHandle = GLUtils.boundFboHandle
        super.build()
        Gdx.gl20.glBindFramebuffer(GL20.GL_FRAMEBUFFER, previousFBOHandle)
    }

    /**
     * @return whether this framebuffer was created with a depth buffer
     */
    fun hasDepth(): Boolean {
        return hasDepth
    }

    /**
     * A builder for a [NestableFrameBuffer]. Useful to add certain
     * attachments.
     *
     * @author damios
     */
    class NestableFrameBufferBuilder(width: Int, height: Int) : FrameBufferBuilder(width, height) {
        override fun build(): FrameBuffer {
            return NestableFrameBuffer(this)
        }

        fun hasDepthRenderBuffer(): Boolean {
            return hasDepthRenderBuffer
        }
    }
}

