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

import feign.RequestLine

import javax.inject.Named

/**
 * Marvel API.
 *
 * <p>See http://developer.marvel.com/docs</p>
 */
interface Marvel {
    /**
     * Finds a Marvel Character by name.
     *
     * @param name name
     * @return a JSON as Map of String, Object
     */
    @RequestLine('GET /v1/public/characters?name={name}')
    Map<String, Object> findByName(@Named('name') String name)

    /**
     * Returns all comics associate to the {@code characterId} with issue number 1.
     *
     * @param characterId character id
     * @return a JSON as Map of String, Object
     */
    @RequestLine('GET /v1/public/characters/{characterId}/comics?issueNumber=1')
    Map<String, Object> comics(@Named('characterId') Integer characterId)
}
