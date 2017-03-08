/*
 * CoulombForce.java
 *
 * Created on September 7, 2007, 12:27 PM
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

import net.sourceforge.code2uml.graph.layouts.NodeSkeleton;
import net.sourceforge.code2uml.util.MathUtils;
import net.sourceforge.code2uml.util.Vector2D;

/**
 * Calculates the push-away force acting between two nodes of a graph.
 *
 * @author Mateusz Wenus
 */
class CoulombForce {
    
    private double coulombFactor = 50.0;
    private static final double maxForce = 1000.0;
    private static final double minDist = 0.0001;
    
    /** 
     * Creates a new instance of CoulombForce. 
     */
    public CoulombForce() {
    }
    
    /**
     * Returns vector describing force acting on node <code>node1</code> which
     * is pushed away from <code>node2</code>.
     *
     * @param node1 node on which force acts (node which is pushed away)
     * @param node2 node which pushed <code>node1</code> away
     * @return vector describing force acting on <code>node1</code>
     */
    public Vector2D calculateCoulomb(NodeSkeleton node1, NodeSkeleton node2) {
        double charge = (node1.getWidth() + node1.getHeight() + 
                         node2.getWidth() + node2.getHeight()) / 4.0;
        double dist = Math.max(minDist, MathUtils.dist(node1.getMiddle(), 
                                                       node2.getMiddle()));
        if(dist > 3 * charge)
            return new Vector2D();
        
        double force = coulombFactor * charge * charge / dist / dist;
        if(Math.abs(force) > maxForce)
            force = Math.signum(force) * maxForce;
        Vector2D result = MathUtils.createUnitVector(node2.getMiddle(),
                                                     node1.getMiddle());
        result.setX(result.getX() * force);
        result.setY(result.getY() * force);
        /*System.out.println("Coulomb:");
        System.out.println("node1 " + node1.getMiddle());
        System.out.println("node2 " + node2.getMiddle());
        System.out.println("value " + force);
        System.out.println("force " + result);*/
        return result;
    }
}
