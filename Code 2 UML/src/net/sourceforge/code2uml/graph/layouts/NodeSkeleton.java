/*
 * NodeSkeleton.java
 *
 * Created on September 5, 2007, 6:33 PM
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
import net.sourceforge.code2uml.graph.*;
import net.sourceforge.code2uml.util.Vector2D;

/**
 * Stores data (positions and size) of a NodeComponent in double precision.
 * Useful for layout algorithms which perform precise calculations.
 *
 * @author Mateusz Wenus
 */
public class NodeSkeleton {
    
    private double x, y;
    private int width, height;
    private NodeComponent node;
    
    /**
     * Creates a new instance of NodeSkeleton.
     */
    public NodeSkeleton() {
    }
    
    /**
     * Creates a new instance of NodeSkeleton that will store data of <code>
     * node</code>. The instance's x, y, width and height and initialized
     * with appropriate values of <code>node</code>.
     *
     * @param node NodeComponent associated with this NodeSkeleton
     */
    public NodeSkeleton(NodeComponent node) {
        x = node.getX();
        y = node.getY();
        width = node.getWidth();
        height = node.getHeight();
        this.setNode(node);
    }
    
    /**
     * Returns x coordinate of this NodeSkeleton.
     *
     * @return x coordinate of this NodeSkeleton
     */
    public double getX() {
        return x;
    }
    
    /**
     * Sets x coordinate of this NodeSkeleten. This method does not modify the
     * NodeComponent associated with this NodeSkeleton.
     *
     * @param x the new value of x coordinate of this NodeSkeleton
     */
    public void setX(double x) {
        this.x = x;
    }
    
    /**
     * Returns y coordinate of this NodeSkeleton.
     *
     * @return y coordinate of this NodeSkeleton
     */
    public double getY() {
        return y;
    }
    
    /**
     * Sets y coordinate of this NodeSkeleten. This method does not modify the
     * NodeComponent associated with this NodeSkeleton.
     *
     * @param y the new value of y coordinate of this NodeSkeleton
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Returns width of this NodeSkeleton.
     *
     * @return width of this NodeSkeleton
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Sets width of this NodeSkeleten. This method does not modify the
     * NodeComponent associated with this NodeSkeleton.
     *
     * @param width new value of width of this NodeSkeleton
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Returns height of this NodeSkeleton.
     *
     * @return height of this NodeSkeleton
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Sets height of this NodeSkeleten. This method does not modify the
     * NodeComponent associated with this NodeSkeleton.
     *
     * @param height new value of height of this NodeSkeleton
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Returns the NodeComponent associated with this NodeSkeleton.
     *
     * @return the NodeComponent associated with this NodeSkeleton
     */
    public NodeComponent getNode() {
        return node;
    }
    
    /**
     * Associates this NodeSkeleton with NodeComponent <code>node</code>, sets
     * x, y, width and height of this NodeSkeleton to appropraite values of
     * <code>node</code>
     *
     * @param node new node that will be associated with this NodeSkeleton
     */
    public void setNode(NodeComponent node) {
        this.node = node;
    }
    
    /**
     * Sets (x, y) coordinates of this NodeSkeleton to
     * (x + v.getX(), y + v.getY()).
     *
     * @param v a vector that will be used to offset this NodeSkeleton
     */
    public void offset(Vector2D v) {
        x += v.getX();
        y += v.getY();
    }
    
    /**
     * Returns coordinates of point in the middle of this NodeSkeleton.
     *
     * @return coordinates of point in the middle of this NodeSkeleton
     */
    public Vector2D getMiddle() {
        return new Vector2D(x + width / 2, y + height / 2);
    }
    
    /**
     * Returns coordinates of point in the middle of top edge of this
     * NodeSkeleton.
     *
     * @return coordinates of point in the middle of top edge of this
     *         NodeSkeleton
     */
    public Vector2D getTopMiddle() {
        return new Vector2D(x + width / 2, y);
    }
    
    /**
     * Returns coordinates of point in the middle of bottom edge of this
     * NodeSkeleton.
     *
     * @return coordinates of point in the middle of bottom edge of this
     *         NodeSkeleton
     */
    public Vector2D getBottomMiddle() {
        return new Vector2D(x + width / 2, y + height);
    }
    
    /**
     * Returns coordinates of point in the middle of left edge of this
     * NodeSkeleton.
     *
     * @return coordinates of point in the middle of left edge of this
     *         NodeSkeleton
     */
    public Vector2D getLeftMiddle() {
        return new Vector2D(x, y + height / 2);
    }
    
    /**
     * Returns coordinates of point in the middle of right edge of this
     * NodeSkeleton.
     *
     * @return coordinates of point in the middle of right edge of this
     *         NodeSkeleton
     */
    public Vector2D getRightMiddle() {
        return new Vector2D(x + width, y + height / 2);
    }
    
    /**
     * Returns a Vector2D which stores coordinates of this NodeSkeleton.
     *
     * @return a Vector2D which stores coordinates of this NodeSkeleton
     */
    public Vector2D getLocation() {
        return new Vector2D(x, y);
    }
    
    /**
     * Updates the (x, y) coordiantes, width and height of the NodeComponent
     * associated with this NodeSkeleton.
     */
    public void updateNodeComponent() {
        if(node != null) {
            node.setLocation((int) x, (int) y);
            node.setSize((int) width, (int) height);
        }
    }
}
