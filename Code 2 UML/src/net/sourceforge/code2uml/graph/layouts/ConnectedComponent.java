/*
 * ConnectedComponent.java
 *
 * Created on September 7, 2007, 3:46 PM
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
import java.util.HashSet;
import java.util.Set;
import net.sourceforge.code2uml.graph.NodeComponent;

/**
 * Represents a weakly connected component of a directed graph.
 *
 * @author Mateusz Wenus
 */
public class ConnectedComponent {
    
    private int x = 0, y = 0;
    private int width = 0, height = 0;
    private int padding = 0;
    private Set<NodeComponent> nodes = new HashSet<NodeComponent>();
    
    /**
     * Creates a new instance of ConnectedComponent with default padding (0).
     */
    public ConnectedComponent() {
    }
    
    /**
     * Creates a new instance of ConnectedComponent with specified padding.
     *
     * @param padding left, right, top and bottom padding of this 
     *        ConnectedComponent
     */
    public ConnectedComponent(int padding) {
        this.padding = padding;
    }
    
    /**
     * Returns x coordinate of this ConnectedComponent.
     *
     * @return x coordinate of this ConnectedComponent
     */
    public int getX() {
        return x;
    }
    
    /**
     * Sets x coordinate of this ConnectedComponent and changes x coordinates
     * of all nodes belonging to it: <br/>
     * <code> newNodeX = oldNodeX + newComponentX - oldComponentX </code>
     *
     * @param x new value of x coordinate of this ConnectedComponent
     */
    public void setX(int x) {
        setLocation(new Point(x, getY()));
    }
    
    /**
     * Returns y coordinate of this ConnectedComponent.
     *
     * @return y coordinate of this ConnectedComponent
     */
    public int getY() {
        return y;
    }
    
    /**
     * Sets y coordinate of this ConnectedComponent and changes y coordinates
     * of all nodes belonging to it: <br/>
     * <code> newNodeY = oldNodeY + newComponentY - oldComponentY </code>
     *
     * @param y new value of y coordinate of this ConnectedComponent
     */
    public void setY(int y) {
        setLocation(new Point(getX(), y));
    }
    
    /**
     * Returns width of this ConnectedComponent.
     *
     * @return width of this ConnectedComponent
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Sets width of this ConnectedComponent.
     *
     * @param width new width of this ConnectedComponent
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Returns height of this ConnectedComponent.
     *
     * @return height of this ConnectedComponent
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Sets height of this ConnectedComponent.
     *
     * @param height new height of this ConnectedComponent
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Returns padding of NodeComponents in this ConnectedComponent.
     *
     * @return padding of NodeComponents in this ConnectedComponent
     */
    public int getPadding() {
        return padding;
    }
    
    /**
     * Sets padding of NodeComponents in this ConnectedComponent.
     *
     * @param padding new value of padding of nodes in this ConnectedComponent
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }
    
    /**
     * Returns nodes of which this ConnectedComponent consists.
     *
     * @return nodes of which this ConnectedComponent consists
     */
    public Set<NodeComponent> getNodes() {
        return nodes;
    }
    
    /**
     * Adds a new node to nodes belonging to this ConnectedComponent.
     *
     * @param node new node to add
     */
    public void addNode(NodeComponent node) {
        nodes.add(node);
    }
    
    /**
     * Sets the new location of this ConnectedComponent and updates coordinates
     * of nodes belonging to it: <br/>
     * <code> newNodeX = oldNodeX + newComponentX - oldComponentX <br/>
     *        newNodeY = oldNodeY + newComponentY - oldComponentY </code>
     *
     * @param p new location of this ConnectedComponent
     */
    public void setLocation(Point p) {
        int oldX = x;
        int oldY = y;
        x = (int) p.getX();
        y = (int) p.getY();
        int dx = x - oldX;
        int dy = y - oldY;
        for(NodeComponent node : nodes) {
            node.setLocation(node.getX() + dx, node.getY() + dy);
        }
    }
    
    /**
     * Offsets all nodes of this ConnectedComponent so that: <br/>
     * - minimum x coordinate of that nodes is equal to padding <br/>
     * - minimum y coordinate of that nodes is equal to padding <br/>
     * - distances between nodes remain unchanged
     */
    public void packNodes() {
        int minX, minY;
        minX = minY = Integer.MAX_VALUE;
        for(NodeComponent node : nodes) {
            int x = node.getX();
            int y = node.getY();
            
            if(x < minX)
                minX = x;
            
            if(y < minY)
                minY = y;
        }
        
        for(NodeComponent node : nodes) {
            node.setLocation(node.getX() - minX + padding, node.getY() - minY + padding);
        }
    }
    
    /**
     * Updates width and height of this ConnectedComponent so that they are
     * equal to (maxNodeX - minNodeX, maxNodeY - minNodeY).
     */
    public void updateSize() {
        int minX, maxX, minY, maxY;
        minX = minY = Integer.MAX_VALUE;
        maxX = maxY = 0;
        for(NodeComponent node : nodes) {
            int x = node.getX();
            int y = node.getY();
            
            if(x < minX)
                minX = x;
            if(x + node.getWidth() > maxX)
                maxX = x + node.getWidth();
            
            if(y < minY)
                minY = y;
            if(y + node.getHeight() > maxY)
                maxY = y + node.getHeight();
        }
        
        width = maxX - minX + 2 * padding;
        height = maxY - minY + 2 * padding;
    }
}
