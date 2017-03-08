/*
 * ForceBasedGraphLayout.java
 *
 * Created on September 5, 2007, 9:27 AM
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

package net.sourceforge.code2uml.graph.layouts.force;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import net.sourceforge.code2uml.graph.*;
import net.sourceforge.code2uml.graph.layouts.*;
import net.sourceforge.code2uml.util.MathUtils;
import net.sourceforge.code2uml.util.ProgressData;
import net.sourceforge.code2uml.util.Vector2D;

/**
 * Lays out a Graph using force-based layout algorithm. In order to do so
 * this class extends SplittingGraphLayout and applies its layout algorithm
 * to each of ConnectedComponents which it is supported by its superclass.
 *
 * @author Mateusz Wenus
 */
public class ForceBasedGraphLayout extends SplittingGraphLayout {
    
    private ConnectedComponentLayout layout = new ConnectedComponentLayout();
    
    /** 
     * Creates a new instance of ForceBasedGraphLayout. 
     */
    public ForceBasedGraphLayout() {
    }

    /**
     * Lays out <code>components</code> using force-based layout algorithm. 
     * Notifies Observers after laying out each of those ConnectedComponents.
     * 
     * @param components ConnectedComponents to lay out
     */
    protected void layout(Collection<ConnectedComponent> components) {
        int nodesCount = 0, laidoutCount = 0;
        
        for(ConnectedComponent comp : components) {
            nodesCount += comp.getNodes().size();
        }
        
        for(ConnectedComponent comp : components) {
            layout.layout(comp);
            laidoutCount += comp.getNodes().size();
            setChanged();
            notifyObservers(new ProgressData(100.0 * laidoutCount / nodesCount));
            clearChanged();
        }
    }
  
    
}
