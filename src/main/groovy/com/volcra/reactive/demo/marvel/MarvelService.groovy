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

import com.volcra.reactive.demo.reactive.Command
import org.springframework.stereotype.Service
import rx.Observable

/**
 * Marvel Service.
 *
 * <p>Depends on the Marvel API and the Hystrix Command to make calls to the retrieve character and commic information.</p>
 */
@Service
class MarvelService {
    private final api = MarvelFeignBuilder.forClass(Marvel)

    /**
     * Return an Observable map.
     *
     * @param names name of characters to find
     * @return an Observable Map
     */
    Observable<Map> findCharacters(String... names) {
        Observable.from(names)
        .flatMap { name ->
            new Command({ api.findByName name }).observe().map { character ->
                def c = character.data.results.first()
                [id       : c.id, name: c.name, description: c.description,
                 thumbnail: "$c.thumbnail.path/portrait_xlarge.$c.thumbnail.extension"]
            }
        }
        .flatMap { character ->
            new Command({ api.comics character.id }).observe().map { comic ->
                def c = comic.data.results.first()
                character << [comic: [title    : c.title, price: c.prices.first().price,
                                       thumbnail: "$c.thumbnail.path/portrait_xlarge.$c.thumbnail.extension"]]
            }
        }
    }
}
