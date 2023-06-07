package threedee.gfx

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
 * Static methods to help check whether some argument, state or object is valid.
 * If the precondition in question is not met, an unchecked exception is thrown.
 *
 *
 * Inspired by
 * [
 * guava](https://github.com/google/guava/wiki/PreconditionsExplained).
 *
 * @author damios
 */
class Preconditions private constructor() {
    init {
        throw UnsupportedOperationException()
    }

    companion object {
        /**
         * Ensures the truth of the given expression.
         *
         * @param expr
         * @throws IllegalArgumentException
         * if `expr` is false
         */
        fun checkArgument(expr: Boolean) {
            require(expr)
        }

        /**
         * Ensures the truth of the given expression.
         *
         * @param expr
         * @param msg
         * the exception message used
         * @throws IllegalArgumentException
         * if `expr` is false
         */
        fun checkArgument(expr: Boolean, msg: String) {
            require(expr) { msg }
        }

        /**
         * Ensures the truth of the given expression.
         *
         * @param expr
         * @throws IllegalStateException
         * if `expr` is false
         */
        fun checkState(expr: Boolean) {
            check(expr)
        }

        /**
         * Ensures the truth of the given expression.
         *
         * @param expr
         * @param msg
         * the exception message used
         * @throws IllegalStateException
         * if `expr` is false
         */
        fun checkState(expr: Boolean, msg: String) {
            check(expr) { msg }
        }

        /**
         * Ensures that the given object reference is not null.
         *
         * @param obj
         * @throws NullPointerException
         * if `obj` is `null`
         */
        fun checkNotNull(obj: Any?) {
            if (obj == null) {
                throw NullPointerException()
            }
        }

        /**
         * Ensures that the given object reference is not null.
         *
         * @param obj
         * @param msg
         * the exception message used
         * @throws NullPointerException
         * if `obj` is `null`
         */
        fun checkNotNull(obj: Any?, msg: String?) {
            if (obj == null) {
                throw NullPointerException(msg)
            }
        }

        /**
         * Ensures that the given collection is not empty.
         *
         * @param <E>
         * @param coll
         * @throws IllegalStateException
         * if `coll` is empty
        </E> */
        fun <E> checkNotEmpty(coll: Collection<E>) {
            checkArgument(!coll.isEmpty())
        }

        /**
         * Ensures that the given collection is not empty.
         *
         * @param <E>
         * @param coll
         * @param msg
         * the exception message used
         * @throws IllegalStateException
         * if `coll` is empty
        </E> */
        fun <E> checkNotEmpty(coll: Collection<E>, msg: String) {
            checkArgument(!coll.isEmpty(), msg)
        }
    }
}

