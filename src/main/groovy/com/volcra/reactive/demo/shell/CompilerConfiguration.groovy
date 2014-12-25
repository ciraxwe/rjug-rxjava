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

import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.springframework.stereotype.Component

/**
 * Compiler Configuration and Customization class.
 */
@Component
class CompilerConfiguration extends org.codehaus.groovy.control.CompilerConfiguration {
    /**
     * Configures default imports.
     */
    CompilerConfiguration() {
        def customizer = new ImportCustomizer()

        customizer.addImports 'javax.inject.Named', 'groovy.json.JsonOutput', 'com.google.common.collect.FluentIterable'
        customizer.addStarImports 'feign', 'rx', 'rx.util.async', 'rx.observables',
                'com.volcra.reactive.demo.marvel', 'com.volcra.reactive.demo.reactive',
                'com.google.common.base', 'java.util.concurrent'
        customizer.addStaticImport 'groovy.json.JsonOutput', 'toJson'
        customizer.addStaticImport 'groovy.json.JsonOutput', 'prettyPrint'

        addCompilationCustomizers customizer
    }
}
