/*
 * RectangularGraphLayout.java
 *
 * Created on August 10, 2007, 7:59 PM
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import net.sourceforge.code2uml.graph.*;

/**
 * Lays out graph nodes in a ractangle, ignoring edges between them.
 *
 * @author Mateusz Wenus
 */
public class RectangularGraphLayout extends Observable implements GraphLayout {
    
    private final int padding = 30;
    
    /** Creates a new instance of RectangularGraphLayout. */
    public RectangularGraphLayout() {
    }
    
    /**
     * Chooses coordinates for nodes of graph. Notifies its Observers 
     * whenever it chooses coordinates for a node.
     *
     * @param g graph to layout
     */
    public void layout(Graph g) {
        int maxWidth = 0, maxHeight = 0;
        for(NodeComponent node : g.getNodes()) {
            if(g.getWidth() > maxWidth)
                maxWidth = g.getWidth();
            if(g.getHeight() > maxHeight)
                maxHeight = g.getHeight();
        }
        
        int count = (int)Math.sqrt(g.getNodes().size());
        int x = padding, y = padding;
        int idx = 0;
        for(NodeComponent node : g.getNodes()) {
            node.setLocation(x, y);
            idx++;
            if(idx >= count) {
                x = padding;
                y += maxHeight + padding;
                idx = 0;
            } else {
                x += maxWidth + padding;
            }
            
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }
}
