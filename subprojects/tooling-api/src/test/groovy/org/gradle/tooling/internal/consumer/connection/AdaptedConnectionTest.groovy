/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.tooling.internal.consumer.connection

import org.gradle.tooling.internal.consumer.parameters.ConsumerOperationParameters
import org.gradle.tooling.internal.protocol.ConnectionVersion4
import org.gradle.tooling.internal.protocol.ProjectVersion3
import spock.lang.Specification

class AdaptedConnectionTest extends Specification {
    final ConnectionVersion4 target = Mock()
    final ConsumerOperationParameters parameters = Mock()
    final AdaptedConnection connection = new AdaptedConnection(target)

    def "builds model using getModel() method"() {
        ProjectVersion3 model = Mock()

        when:
        def result = connection.run(ProjectVersion3.class, parameters)

        then:
        result == model

        and:
        1 * target.getModel(ProjectVersion3.class, parameters) >> model
        0 * target._
    }

    def "runs build using executeBuild() method"() {
        when:
        connection.run(Void.class, parameters)

        then:
        1 * target.executeBuild(parameters, parameters)
        0 * target._
    }
}
