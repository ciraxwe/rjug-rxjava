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

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommand.Setter

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey

/**
 * Base Hystrix command tht wraps a Closure.
 *
 * <p>The Closure can be any arbitrary code.</p>
 */
class Command extends HystrixCommand<Object> {
    private final Closure closure

    /**
     * Constructor.
     *
     * <p>This will set Marvel as the Command Group.</p>
     *
     * @param command closure to execute when command runs.
     */
    Command(Closure command) {
        super(withGroupKey(HystrixCommandGroupKey.Factory.asKey('Marvel')))
        this.closure = command
    }

    /**
     * Constructor.
     *
     * <p>Allows configuration of the Hystrix Command by providing {@code com.netflix.hystrix.HystrixCommand.Setter}.
     * @param command
     * @param setter
     */
    Command(Closure command, Setter setter) {
        super(setter)
        this.closure = command
    }

    /** {@inheritDoc */
    @Override
    protected Object run() throws Exception {
        closure.call()
    }
}
