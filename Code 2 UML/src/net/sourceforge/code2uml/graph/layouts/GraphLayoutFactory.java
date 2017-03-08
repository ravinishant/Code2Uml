/*
 * GraphLayoutFactory.java
 *
 * Created on August 4, 2007, 9:59 PM
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

package net.sourceforge.code2uml.graph.layouts;

import net.sourceforge.code2uml.graph.*;

/**
 * Defines methods used to get the right GraphLayout.
 *
 * @author Mateusz Wenus
 */
public interface GraphLayoutFactory {
    
    /**
     * Returns requested GraphLayout. 
     *
     * @param name name of GraphLayout
     * @return requested of GraphLayout or SimpleGraphLayout if it could not be found
     */
    public GraphLayout getLayout(String name);
}
