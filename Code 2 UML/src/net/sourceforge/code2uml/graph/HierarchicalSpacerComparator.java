/*
 * HierarchicalSpacerComparator.java
 *
 * Created on August 12, 2007, 10:35 AM
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

import java.util.Comparator;

/**
 * Imposes total ordering on NodeComponent such that: <br/>
 * node1 < node2 <=> node1.getX() < node2.getX() || 
 *                   (node1.getX() == node2.getX() && node1.getY() < node2.getY()) 
 * <br/>
 * <br/>
 * Note: Note: this comparator imposes orderings that are inconsistent with equals.
 * This means that it should not be applied to sorted sets or sorted maps (see
 * java.util.Comparator documentation).
 *
 * @author Mateusz Wenus
 */
public class HierarchicalSpacerComparator implements Comparator<NodeComponent> {
    
    /** 
     * Creates a new instance of HierarchicalSpacerComparator. 
     */
    public HierarchicalSpacerComparator() {
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, 
     * or a positive integer as the first argument is less than, equal to, or 
     * greater than the second.
     *
     * @param o1 first argument to be compared
     * @param o2 second argument to be compared
     * @return a negative integer, zero, or a positive integer as the first 
     *         argument is less than, equal to, or greater than the second
     */
    public int compare(NodeComponent o1, NodeComponent o2) {
        if(o1.getX() == o2.getX() && o1.getY() == o2.getY())
            return 0;
        if(o1.getX() < o2.getX() || (o1.getX() == o2.getX() && o1.getY() < o2.getY()))
            return -1;
        else
            return 1;
    }
    
}
