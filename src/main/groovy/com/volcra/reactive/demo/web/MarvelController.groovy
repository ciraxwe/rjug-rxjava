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

import com.volcra.reactive.demo.marvel.MarvelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.GET

/**
 * Marvel API Example.
 */
@Controller
class MarvelController {
    /**
     * Marvel Service.
     */
    @Autowired
    MarvelService marvelService

    /**
     * Marvel API Endpoint.
     *
     * @param characters list of characters to look up.
     * @return a JSON object
     */
    @ResponseBody
    @RequestMapping(value = '/marvel', method = GET, produces = APPLICATION_JSON_VALUE)
    def marvel(@RequestParam('character') String[] characters) {
        toJson marvelService.findCharacters(characters).toBlocking().toIterable()
    }
}
