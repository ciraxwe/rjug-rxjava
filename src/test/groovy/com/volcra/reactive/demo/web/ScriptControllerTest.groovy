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



package com.volcra.reactive.demo.web

import com.volcra.reactive.demo.shell.ScriptRunner
import groovy.mock.interceptor.MockFor
import org.codehaus.groovy.control.CompilerConfiguration
import org.hamcrest.Matchers

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Test class for {@link com.volcra.reactive.demo.web.ScriptController}.
 */
class ScriptControllerTest extends GroovyTestCase {

    private static final String SCRIPT = '1 + 1'

    /**
     * Test method for {@link com.volcra.reactive.demo.web.ScriptController#run(java.lang.String)}.
     */
    void testRun() {
        def mock = new MockFor(ScriptRunner)

        mock.demand.run { script, configuration -> ['script': script] }

        def controller = new ScriptController(runner: mock.proxyInstance())
        def result = controller.run(SCRIPT)

        assert result != null
        assertThat result['script'] as String, equalTo(SCRIPT)
    }
}
