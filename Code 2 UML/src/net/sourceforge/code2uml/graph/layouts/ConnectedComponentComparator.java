/*
 * ConnectedComponentComparator.java
 *
 * Created on September 11, 2007, 11:20 AM
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

import java.util.Comparator;

/**
 * Copares ConnectedComponents for order. Used by ConnectedComponentMerger.
 * 
 * @author Mateusz Wenus
 */
class ConnectedComponentComparator implements Comparator<ConnectedComponent> {
    
    /** Creates a new instance of ConnectedComponentComparator. */
    public ConnectedComponentComparator() {
    }

    /**
     * Compares two ConnectedComponents for order. Returns 0 if those components
     * have equal sizes, 1 if the first is wider than the second or if they have
     * the same widths and the first has greater height, -1 otherwise. 
     * <br/><br/>
     * Note: ordering imposed by this comparator is not consistent with equals.
     * 
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     * 	       second
     */
    public int compare(ConnectedComponent o1, ConnectedComponent o2) {
        if(o1.getWidth() == o2.getWidth()) {
            if(o1.getHeight() == o2.getHeight())
                return 0;
            else
                return o1.getHeight() > o2.getHeight()? 1 : -1;
        } else {
            return o1.getWidth() > o2.getWidth()? 1 : -1;
        }
    }
    
}
