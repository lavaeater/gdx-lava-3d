package threedee.gfx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import java.nio.ByteBuffer
import java.nio.ByteOrder

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
 * OpenGL utilities.
 *
 * @author damios
 */
class GLUtils private constructor() {
    init {
        throw UnsupportedOperationException()
    }

    companion object {
        /**
         * The buffer used internally. A size of 64 bytes is required as at most 16
         * integer elements can be returned.
         */
        private val INT_BUFF = ByteBuffer
            .allocateDirect(16 * Integer.BYTES).order(ByteOrder.nativeOrder())
            .asIntBuffer()

        @get:Synchronized
        val boundFboHandle: Int
            /**
             * Returns the name of the currently bound framebuffer
             * (`GL_FRAMEBUFFER_BINDING`).
             *
             * @return the name of the currently bound framebuffer; the initial value is
             * `0`, indicating the default framebuffer
             */
            get() {
                val intBuf = INT_BUFF
                Gdx.gl.glGetIntegerv(GL20.GL_FRAMEBUFFER_BINDING, intBuf)
                return intBuf[0]
            }

        @get:Synchronized
        val viewport: IntArray
            /**
             * @return the current gl viewport (`GL_VIEWPORT`) as an array,
             * containing four values: the x and y window coordinates of the
             * viewport, followed by its width and height.
             */
            get() {
                val intBuf = INT_BUFF
                Gdx.gl.glGetIntegerv(GL20.GL_VIEWPORT, intBuf)
                return intArrayOf(
                    intBuf[0], intBuf[1], intBuf[2],
                    intBuf[3]
                )
            }
    }
}

