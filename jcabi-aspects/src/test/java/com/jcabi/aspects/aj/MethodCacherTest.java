/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.aspects.aj;

import com.jcabi.aspects.Cacheable;
import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link MethodCacher}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class MethodCacherTest {

    /**
     * MethodCacher can cache calls.
     * @throws Throwable If something goes wrong
     * @checkstyle IllegalThrows (5 lines)
     */
    @Test
    public void cachesSimpleCall() throws Throwable {
        final MethodCacherTest.Foo foo = new MethodCacherTest.Foo();
        final String first = foo.get();
        MatcherAssert.assertThat(first, Matchers.equalTo(foo.get()));
        foo.flush();
        MatcherAssert.assertThat(
            foo.get(),
            Matchers.not(Matchers.equalTo(first))
        );
    }

    /**
     * MethodCacher can cache static calls.
     * @throws Throwable If something goes wrong
     * @checkstyle IllegalThrows (5 lines)
     */
    @Test
    public void cachesSimpleStaticCall() throws Throwable {
        final String first = MethodCacherTest.Foo.staticGet();
        MatcherAssert.assertThat(
            first,
            Matchers.equalTo(MethodCacherTest.Foo.staticGet())
        );
        MethodCacherTest.Foo.staticFlush();
        MatcherAssert.assertThat(
            MethodCacherTest.Foo.staticGet(),
            Matchers.not(Matchers.equalTo(first))
        );
    }

    /**
     * Dummy class, for tests above.
     */
    private static final class Foo {
        /**
         * Random.
         */
        private static final Random RANDOM = new Random();
        /**
         * Download some text.
         * @return Downloaded text
         */
        @Cacheable
        public String get() {
            return Long.toString(MethodCacherTest.Foo.RANDOM.nextLong());
        }
        /**
         * Flush it.
         */
        @Cacheable.Flush
        public void flush() {
            // nothing to do
        }
        /**
         * Download some text.
         * @return Downloaded text
         */
        @Cacheable
        public static String staticGet() {
            return Long.toString(MethodCacherTest.Foo.RANDOM.nextLong());
        }
        /**
         * Flush it.
         */
        @Cacheable.Flush
        public static void staticFlush() {
            // nothing to do
        }
    }

}
