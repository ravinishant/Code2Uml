/*
 * ConnectedComponentFactory.java
 *
 * Created on September 7, 2007, 3:55 PM
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
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.NodeComponent;

/**
 * Divides a Graph into ConnectedComponents.
 *
 * @author Mateusz Wenus
 */
public class ConnectedComponentFactory {
    
    private int padding = 0;
    
    /** 
     * Creates a new instance of ConnectedComponentFactory which will create
     * ConnectedComponents with default padding (0). 
     */
    public ConnectedComponentFactory() {
    }
    
    /** 
     * Creates a new instance of ConnectedComponentFactory which will create
     * ConnectedComponents with specified padding.
     *
     * @param padding padding of ConnectedComponents created by this instance of
     *        ConnectedcomponentFactory 
     */
    public ConnectedComponentFactory(int padding) {
        this.padding = padding;
    }
    
    /**
     * Adds <code>node</code> and all nodes which are conntected with it to 
     * component <code>component</code>.
     *
     * @param component ConnectedComponent which is being constructed
     * @param node NodeComponent to add
     */
    private void addToComponent(ConnectedComponent component, NodeComponent node) {
        if(component.getNodes().contains(node))
            return;
        
        component.addNode(node);
        for(EdgeComponent e : node.getInEdges()) {
            addToComponent(component, e.getFrom());
        }
        for(EdgeComponent e : node.getOutEdges()) {
            addToComponent(component, e.getTo());
        }
    }
    
    /**
     * Creates a ConnectedComponent which contains node <code>node</code> and
     * all other nodes conntected with it.
     *
     * @param node NodeComponent which will belong to this ConnectedComponent
     *        (with all other nodes that are conntected with it)
     * @return the ConnectedComponent created by this method
     */
    private ConnectedComponent createComponent(NodeComponent node) {
        ConnectedComponent result = new ConnectedComponent(padding);
        addToComponent(result, node);
        return result;
    }
    
    /**
     * Returns ConnectedComponents of which graph <code>g</code> consists.
     *
     * @param g graph to create ConnectedComponents from
     * @return ConnectedComponents of which graph <code>g</code> consists
     */
    public Collection<ConnectedComponent> createFromGraph(Graph g) {
        Collection<NodeComponent> nodes = new ArrayList<NodeComponent>(g.getNodes());
        Collection<ConnectedComponent> components = new ArrayList<ConnectedComponent>();
        while(!nodes.isEmpty()) {
            ConnectedComponent component = createComponent(nodes.iterator().next());
            
            components.add(component);
            
            nodes.removeAll(component.getNodes());
        }
        return components;
    }
}
