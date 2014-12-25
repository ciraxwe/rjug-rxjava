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

import feign.Request
import feign.RequestTemplate
import feign.Target
import org.springframework.core.io.ClassPathResource
import org.springframework.util.DigestUtils

/**
 * Custom {@code feign.Target} implementation that provides authentication.</p>
 */
class MarvelTarget<T> extends Target.HardCodedTarget<T> {
    private static URL = 'http://gateway.marvel.com'

    private static final PROPS = new Properties()
    private static final APP_PROPERTIES = new ClassPathResource('application.properties')

    private String apiKey
    private String privateKey

    /**
     * Constructor used internally by the static method {@link MarvelTarget#forClass(java.lang.Class)} .
     *
     * @param type class type
     */
    private MarvelTarget(Class<T> type) {
        super(type, URL)

        PROPS.load APP_PROPERTIES.inputStream
        apiKey = PROPS.getProperty('app.marvel.api.key')
        privateKey = PROPS.getProperty('app.marvel.api.private')
    }

    /**
     * Creates an instance of {@link MarvelTarget} from the given {@code type}.
     *
     * @param type class type
     * @return an instance of {@link MarvelTarget} from the given {@code type}
     */
    static <T> MarvelTarget<T> forClass(Class<T> type) {
        new MarvelTarget<T>(type)
    }

    /**
     * {@inheritDoc}
     *
     * <p>Configure the template with additional authentication parameters required by the Marvel API.</p>
     */
    @Override
    Request apply(RequestTemplate input) {
        def ts = new Date().time
        def hex = DigestUtils.md5DigestAsHex("$ts$privateKey$apiKey".bytes)

        input.insert 0, url()
        input.query('apikey', apiKey) query('ts', ts as String) query 'hash', hex
        input.request()
    }
}

