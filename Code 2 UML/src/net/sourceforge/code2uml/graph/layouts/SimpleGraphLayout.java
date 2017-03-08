/*
 * SimpleGraphLayout.java
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

import java.util.Observable;
import net.sourceforge.code2uml.graph.*;

/**
 * Pretends spacing graph. Actually sets all nodes' cordinates to (0, 0). 
 * This class is intended for testing only.
 *
 * @author Mateusz Wenus
 */
public class SimpleGraphLayout extends Observable implements GraphLayout {
    
    /** 
     * Creates a new instance of SimpleGraphLayout. 
     */
    public SimpleGraphLayout() {
    }

    /**
     * Sets all nodes' coordinates to (0, 0). 
     *
     * @param graph graph with all nodes' coordinates set to (0, 0)
     */
    public void layout(Graph graph) {
        for(NodeComponent node : graph.getNodes()) {
            node.setLocation(0, 0);
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }
    
}
