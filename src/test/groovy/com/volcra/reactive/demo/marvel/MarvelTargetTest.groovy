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

package com.volcra.reactive.demo.marvel

import feign.RequestTemplate

/**
 * Test class for {@link com.volcra.reactive.demo.marvel.MarvelTarget}
 */
class MarvelTargetTest extends GroovyTestCase {
    /**
     * Test method for {@link com.volcra.reactive.demo.marvel.MarvelTarget#apply(feign.RequestTemplate)}
     */
    void testApply() {
        def target = MarvelTarget.forClass(Marvel)
        def template = new RequestTemplate()

        template.method 'test'

        def request = target.apply(template)

        assert request.toString().contains('apikey')
        assert request.toString().contains('ts')
        assert request.toString().contains('hash')
    }
}
