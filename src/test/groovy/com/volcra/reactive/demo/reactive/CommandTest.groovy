/*
 * Copyright 2014 Volcra
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.volcra.reactive.demo.reactive

/**
 * Test class for {@link com.volcra.reactive.demo.reactive.Command}
 */
class CommandTest extends GroovyTestCase {

    /**
     * Test method for {@link com.volcra.reactive.demo.reactive.Command#run()}
     */
    void testRun() {
        def command = new Command({ 'test' })

        assert command.run() == 'test'
    }
}
