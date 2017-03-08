/*
 * GraphLayout.java
 *
 * Created on August 4, 2007, 9:58 PM
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

import java.util.Observer;
import net.sourceforge.code2uml.graph.*;

/**
 * Defines methods used to choose coordinates for nodes of graph so that
 * they don't overlap and their edges don't cross too much. Subclasses
 * must also implement addObserver() method and should notify their Observers
 * about progress of graph layout.
 *
 * @author Mateusz Wenus
 */
public interface GraphLayout {

    /**
     * Chooses coordinates for nodes of graph. Should notify its Observers 
     * about progress of its work using ProgressData objects.
     *
     * @param graph graph to layout
     */
    public void layout(Graph graph);
    
    /**
     * Adds a Observer to this object.
     *
     * @param o Observer to add
     */
    public void addObserver(Observer o);
}
