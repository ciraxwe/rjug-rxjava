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

import com.netflix.hystrix.HystrixCommandGroupKey

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey

/**
 * A Hystrix Command where Command Group key is Character.
 */
class CharacterCommand extends Command {
    /**
     * Constructor.
     *
     * @param command closure to execute
     */
    CharacterCommand(Closure command) {
        super(command, withGroupKey(HystrixCommandGroupKey.Factory.asKey('Character')))
    }
}
