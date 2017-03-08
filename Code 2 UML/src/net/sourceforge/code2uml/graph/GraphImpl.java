/*
 * GraphImpl.java
 *
 * Created on August 3, 2007, 11:02 PM
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

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a graph of classes/interfaces/enums and relationships between them
 * that can be drawn as UML class diagram.
 *
 * @author Mateusz Wenus
 */
public class GraphImpl implements Graph {
    
    private Collection<NodeComponent> nodes = new HashSet<NodeComponent>();
    
    /**
     * Creates a new instance of GraphImpl. 
     */
    public GraphImpl() {
    }
    
    /**
     * Returns nodes of this graph. 
     *
     * @return nodes of this graph
     */
    public Collection<NodeComponent> getNodes() {
        return nodes;
    }

    /**
     * Returns width of this graph.
     * 
     * @return width of this graph
     */
    public int getWidth() {
        int result = 0;
        for(NodeComponent node : nodes) {
            int x = node.getX() + node.getWidth();
            if(x > result)
                result = x;
        }
        return result;
    }

    /**
     * Returns height of this graph. 
     *
     * @return height of this graph
     */
    public int getHeight() {
        int result = 0;
        for(NodeComponent node : nodes) {
            int y = node.getY() + node.getHeight();
            if(y > result)
                result = y;
        }
        return result;
    }
    
}
