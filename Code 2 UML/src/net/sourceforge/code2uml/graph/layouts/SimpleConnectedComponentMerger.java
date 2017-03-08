/*
 * SimpleConnectedComponentMerger.java
 *
 * Created on September 11, 2007, 11:15 AM
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

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Merges ConnectedComponents which have been laid out into one graph layout.
 *
 * @author Mateusz Wenus
 */
class SimpleConnectedComponentMerger implements ConnectedComponentMerger {
    
    /**
     * Creates a new instance of SimpleConnectedComponentMerger.
     */
    public SimpleConnectedComponentMerger() {
    }
    
    /**
     * Lays out ConnectedComponens from <code>components</code> so that they
     * don't overlap. Does not change distances between NodeComponents
     * belonging to the same ConnectedComponent.
     *
     * @param components ConnectedComponents to merge
     */
    public void merge(Collection<ConnectedComponent> components) {
        ArrayList<ConnectedComponent> al = new ArrayList<ConnectedComponent>(components);
        Collections.sort(al, new ConnectedComponentComparator());
        int area = 0;
        for(ConnectedComponent comp : components)
            area += comp.getWidth() * comp.getHeight();
        double desiredWidth = Math.sqrt(area);
        
        double currentX = 0.0, currentY = 0.0, maxHeight = 0.0;
        for(ConnectedComponent comp : al) {
            
            if(currentX + comp.getWidth() > desiredWidth) {
                currentX = 0.0;
                currentY += maxHeight;
                maxHeight = 0.0;
            }
            
            comp.setLocation(new Point((int) currentX, (int) currentY));
            currentX += comp.getWidth();
            if(comp.getHeight() > maxHeight)
                maxHeight = comp.getHeight();
        }
    }
    
}
