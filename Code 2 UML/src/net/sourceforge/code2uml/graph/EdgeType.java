/*
 * EdgeType.java
 *
 * Created on 1 August 2007, 16:32
 *
 * Copyright 2007 Mateusz Wenus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sourceforge.code2uml.graph;

/**
 * Describes type of relationship between two classes/interfaces/enums.
 *
 * @author Mateusz Wenus
 */
public enum EdgeType {
    GENERALIZATION, REALIZATION, AGGREGATION, COMPOSITION
}
