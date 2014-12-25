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
import org.codehaus.groovy.control.CompilerConfiguration
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.POST

/**
 * Script Controller.
 *
 * <p>Executes Groovy Code<./p>
 */
@Controller
class ScriptController {
    /**
     * Script runner.
     */
    @Autowired
    ScriptRunner runner

    /**
     * Compiler configuration.
     */
    @Autowired
    CompilerConfiguration configuration

    /**
     * Takes a Groovy Script and delegates to {@link ScriptRunner}.
     *
     * @param script groovy script
     * @return script execution metadata
     */
    @ResponseBody
    @RequestMapping(value = '/script', method = POST, produces = APPLICATION_JSON_VALUE)
    def run(@RequestParam("script") @NotEmpty String script) {
        runner.run script, configuration
    }
}
