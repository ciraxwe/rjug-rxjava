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



package com.volcra.reactive.demo.shell

import groovy.ui.SystemOutputInterceptor
import org.codehaus.groovy.control.CompilerConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Groovy Script Runner.
 */
@Component
class ScriptRunner {
    private static final Logger LOG = LoggerFactory.getLogger(this.class)

    /**
     * Runs the Groovy Script.
     *
     * <ul>
     *     <li><strong>script:</strong> The original script.</li>
     *     <li><strong>output:</strong> Console output.</li>
     *     <li><strong>start:</strong> Script start time.</li>
     *     <li><strong>endTime:</strong> Script end time.</li>
     * </ul>
     *
     * @param script script to run
     * @return metadata about the run
     */
    def run(String script, CompilerConfiguration compiler = CompilerConfiguration.DEFAULT) {
        def resultMap = ['script': script, 'start': new Date()]
        def shell = new GroovyShell(this.class.classLoader, new Binding(), compiler)

        def outputCollector = new SystemOutputInterceptorClosure()
        def systemOutputInterceptor = new SystemOutputInterceptor(outputCollector)
        systemOutputInterceptor.start()

        try {
            shell.evaluate script
        } catch (e) {
            LOG.error("error", e as Exception)
            resultMap << ['error': e.message]
        } finally {
            systemOutputInterceptor.stop()
        }

        resultMap << ['endTime': new Date(), 'output': outputCollector.output()]
    }
}

/**
 * Captures the console output into a local buffer.
 */
class SystemOutputInterceptorClosure extends Closure {
    private buffer = new StringBuffer()

    /**
     * Constructor.
     */
    SystemOutputInterceptorClosure() {
        super(null)
    }

    /** {@inheritDoc */
    @Override
    def call(Object params) {
        buffer.append params
        false
    }

    /** {@inheritDoc */
    @Override
    def call(Object... args) {
        buffer.append args
        false
    }

    /**
     * Returns the buffer content.
     *
     * @return buffer content
     */
    def output() {
        buffer.toString().trim()
    }
}
