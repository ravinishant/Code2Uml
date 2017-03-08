/*
 * ConnectedComponentLayout.java
 *
 * Created on September 7, 2007, 4:40 PM
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

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;
import net.sourceforge.code2uml.graph.layouts.NodeSkeleton;
import net.sourceforge.code2uml.util.Vector2D;

/**
 * Lays out a ConnectedComponent using force-based layout algorithm.
 *
 * @author Mateusz Wenus
 */
class ConnectedComponentLayout {
    
    private double desiredDist = 60.0;
    private CoulombForce coulombForce = new CoulombForce();
    private HookForce hookForce = new HookForce();
    
    /**
     * Creates a new instance of ConnectedComponentLayout.
     */
    public ConnectedComponentLayout() {
    }
    
    /**
     * Chooses location for node <code>node</code>. Exact behaviour depends on
     * value of <code>from</code>: <br/>
     * # if it is null, <code>node</code> is placed on some starting 
     *   location <br/>
     * # if it is not null, <code>node</code> is placed next to 
     *   <code>from</code>, depending on type of their relation: <br/>
     * - if <code>node</code> is supertype of from it is placed above <br/>
     * - if it is subtype it is placed below <br/>
     * - if it contains from it is placed on the left <br/>
     * - if it is contained by from it is placed on the right <br/>
     *
     * @param node NodeComponent which location will be chosen
     * @param from null or NodeComponent to which <code>node</code>'s position
     *        will be relative
     * @param edge edge connecting <code>node</code> and <code>from</code>
     * @param n distance of <code>node</code> from NodeComponent which was 
     *        visited first
     */
    private void visit(NodeComponent node, NodeComponent from, EdgeComponent edge, int n) {
        if(node.getX() != 0 || node.getY() != 0)
            return;
        
        if(from != null) {
            double x = 0, y = 0;
            switch(edge.getType()) {
                case GENERALIZATION:
                    y = -desiredDist;
                    break;
                case REALIZATION:
                    y = -desiredDist;
                    break;
                case COMPOSITION:
                    x = desiredDist;
                    break;
                case AGGREGATION:
                    x = desiredDist;
                    break;
            }
            if(edge.getFrom() == node) {
                x = -x;
                y = -y;
            }
            for(int i = 0; i < n; i++) {
                x *= 0.9;
                y *= 0.9;
            }
            
            node.setLocation(from.getX() + (int)x, from.getY() + (int)y);
        } else {
            node.setLocation(1000, 1000);
        }
        for(EdgeComponent e : node.getOutEdges()) {
            visit(e.getTo(), node, e, n + 1);
        }
        for(EdgeComponent e : node.getInEdges()) {
            visit(e.getFrom(), node, e, n + 1);
        }
    }
    
    /**
     * Lays out NodeComponents from <code>component</code> so that nodes which
     * are connected are located close to each other. Quality of force-based
     * layout algorithm depends on starting graph layout so this method should
     * produce good layouts.
     *
     * @param component ConnectedComponent to pre-layout
     */
    private void prelayout(ConnectedComponent component) {
        visit(component.getNodes().iterator().next(), null, null, 0);
    }
    
    /**
     * Lays out NodeComponents belonging to ConnectedComponent <code>component
     * </code>.
     *
     * @param component ConnectedComponent which NodeComponents will be laid
     *        out
     */
    public void layout(ConnectedComponent component) {
        double padding = component.getPadding();
        prelayout(component);
        int size = component.getNodes().size();
        
        Map<NodeComponent, NodeSkeleton> skeletons = new HashMap<NodeComponent, NodeSkeleton>(size);
        Map<NodeSkeleton, Vector2D> newLocations = new HashMap<NodeSkeleton, Vector2D>(size);
        
        for(NodeComponent node : component.getNodes()) {
            skeletons.put(node, new NodeSkeleton(node));
        }
        
        int i = 0;
        boolean done = false;
        while(!done) {
            
            for(NodeComponent node : component.getNodes()) {
                NodeSkeleton currentSkeleton = skeletons.get(node);
                Vector2D force = new Vector2D();
                
                for(NodeComponent otherNode : component.getNodes()) {
                    if(node != otherNode)
                        force.add(coulombForce.calculateCoulomb(currentSkeleton, skeletons.get(otherNode)));
                }
                
                for(EdgeComponent edge : node.getOutEdges()) {
                    force.add(hookForce.calculateHook(currentSkeleton, skeletons.get(edge.getTo()), edge));
                }
                for(EdgeComponent edge : node.getInEdges()) {
                    force.add(hookForce.calculateHook(currentSkeleton, skeletons.get(edge.getFrom()), edge));
                }
                
                newLocations.put(currentSkeleton, currentSkeleton.getLocation());
                newLocations.get(currentSkeleton).add(force);
                Vector2D newLocation = newLocations.get(currentSkeleton);
                
                if(newLocation.getX() < padding)
                    newLocation.setX(padding);
                if(newLocation.getY() < padding)
                    newLocation.setY(padding);
            }
            
            for(NodeSkeleton skeleton : newLocations.keySet()) {
                Vector2D v = newLocations.get(skeleton);
                skeleton.setX(v.getX());
                skeleton.setY(v.getY());
            }
            
            i++;
            done = (i > 20);
        }
        
        for(NodeComponent node : component.getNodes()) {
            NodeSkeleton skeleton = skeletons.get(node);
            skeleton.updateNodeComponent();
        }
        postlayout(component);
    }
    
    /**
     * Finalizes components's layout.
     *
     * @param component ConnectedComponent
     */
    private void postlayout(ConnectedComponent component) {
        component.packNodes();
        component.updateSize();
    }
}
