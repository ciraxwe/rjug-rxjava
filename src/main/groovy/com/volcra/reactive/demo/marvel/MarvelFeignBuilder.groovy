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

import feign.Feign
import feign.FeignException
import feign.Logger
import feign.Response
import feign.codec.DecodeException
import feign.codec.Decoder
import groovy.json.JsonSlurper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.lang.reflect.Type

import static feign.Logger.Level

/**
 * Helper class to create Feign interfaces with common settings for all clients.
 */
class MarvelFeignBuilder {
    /**
     * Create a Feign client from the given {@code type}.
     *
     * @param type class type
     * @return a Feign client
     */
    static <T> T forClass(Class<T> type) {
        Feign.builder()
                .logger(new CommonsLogger())
                .logLevel(Level.BASIC)
                .decoder(new JsonSlurperDecoder())
                .target MarvelTarget.forClass(type)
    }

    /**
     * Decoder that transforms {@code feign.Response} with a {@link JsonSlurper}.
     */
    private static class JsonSlurperDecoder implements Decoder {
        def parser = new JsonSlurper()

        /** {@inheritDoc */
        @Override
        def decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            if (response.body() == null) {
                null
            } else {
                parser.parse response.body().asReader()
            }
        }
    }

    /**
     * Slf4j Logger Implementation.
     */
    private static class CommonsLogger extends Logger {
        private final Log log = LogFactory.getLog('feign.logger')

        /** {@inheritDoc */
        @Override
        protected void log(String configKey, String format, Object... args) {
            log.info String.format(methodTag(configKey) + format, args)
        }
    }
}
