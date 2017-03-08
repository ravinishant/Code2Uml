/*
 * HookForce.java
 *
 * Created on September 7, 2007, 12:26 PM
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

import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.graph.layouts.NodeSkeleton;
import net.sourceforge.code2uml.util.MathUtils;
import net.sourceforge.code2uml.util.Vector2D;

/**
 * Calculates force acting between two graph nodes connected by an edge.
 *
 * @author Mateusz Wenus
 */
class HookForce {
    
    private static final double hookFactor = 0.1;
    private static final double desiredHookDist = 30.0;
    private static final double maxForce = 1000.0;
    private static final double minDist = 0.0001;
    
    /** 
     * Creates a new instance of HookForce. 
     */
    public HookForce() {
    }
    
    /**
     * Returns vector describing force acting on node <code>node1</code> 
     * connected to <code>node2</code> by an edge <code>edge</code>.
     *
     * @param node1 node that force acts on
     * @param node2 node to which <code>node1</code> is connected
     * @param edge edge connecting <code>node1</code> and <code>node2</code>
     * @return vector describing force acting on node <code>node1</code>
     */
    public Vector2D calculateHook(NodeSkeleton node1, NodeSkeleton node2, EdgeComponent edge) {
        
        /**
         * point1 is a point on node1 to which edge is attached
         * point2 is such point on node2
         */
        Vector2D point1, point2;
        
        /**
         * fromPoint is a point where the edge starts (it is either point1 or point2)
         * toPoint is a point where the edge ends (it is either point1 or point2)
         */
        Vector2D fromPoint, toPoint;
        
        /**
         * fromSkeleton is a NodeSkeleton associated with a NodeComponent which 
         *              is the start of edge
         * toSkeleton is a NodeSkeleton associated with a NodeComponent which 
         *              is the end of edge
         */
        NodeSkeleton fromSkeleton, toSkeleton;
        
        if(node1.getNode() == edge.getFrom()) {
            fromSkeleton = node1;
            toSkeleton = node2;
        } else {
            fromSkeleton = node2;
            toSkeleton = node1;
        }
        
        if(edge.getType().equals(EdgeType.REALIZATION) || edge.getType().equals(EdgeType.GENERALIZATION)) {
            fromPoint = fromSkeleton.getTopMiddle();
            toPoint = toSkeleton.getBottomMiddle();
        } else {
            if(fromSkeleton.getMiddle().getX() > toSkeleton.getMiddle().getX()) {
                fromPoint = fromSkeleton.getLeftMiddle();
                toPoint = toSkeleton.getRightMiddle();
            } else {
                fromPoint = fromSkeleton.getRightMiddle();
                toPoint = toSkeleton.getLeftMiddle();
            }
        }
        
        if(node1.getNode() == edge.getFrom()) {
            point1 = fromPoint;
            point2 = toPoint;
        } else {
            point2 = fromPoint;
            point1 = toPoint;
        }
        
        double dist = Math.max(minDist, MathUtils.dist(fromPoint, toPoint));
        double force = -hookFactor * (dist - desiredHookDist);
        if(Math.abs(force) > maxForce)
            force = Math.signum(force) * maxForce;
        Vector2D result = MathUtils.createUnitVector(point2, point1);
        
        result.setX(result.getX() * force);
        result.setY(result.getY() * force);
        
        /*System.out.println("Hook:");
        System.out.println("node1 " + fromPoint);
        System.out.println("node2 " + toPoint);
        System.out.println("value " + force);
        System.out.println("force " + result);*/
        return result;
    }
}
