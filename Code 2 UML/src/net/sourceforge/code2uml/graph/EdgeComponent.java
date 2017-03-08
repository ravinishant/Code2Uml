/*
 * EdgeComponent.java
 *
 * Created on 1 August 2007, 16:43
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

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import net.sourceforge.code2uml.util.Draw;
import net.sourceforge.code2uml.util.MathUtils;

/**
 * Represents a relation between two elements of an UML class diagram.
 * To display an EdgeComponent simply add it to a Container (EdgeComponent
 * extends JComponent). If any of nodes an EdgeComponent connects changes
 * its location, the EdgeComponent automatically moves as well.
 *
 * @author Mateusz Wenus
 */
public class EdgeComponent extends JComponent implements ComponentListener {
    
    private static final int minSize = 8;
    private NodeComponent from, to;
    private EdgeType type;
    
    /**
     * Creates a new instance of EdgeComponent.
     */
    public EdgeComponent() {
    }
    
    /**
     * Returns the tail node of this edge.
     *
     * @return the tail node of this edge
     */
    public NodeComponent getFrom() {
        return from;
    }
    
    /**
     * Sets the tail node of this edge.
     *
     * @param from the tail node of this edge
     */
    public void setFrom(NodeComponent from) {
        if(this.from != null)
            this.from.removeComponentListener(this);
        this.from = from;
        from.addComponentListener(this);
    }
    
    
    
    /**
     * Returns the head node of this edge.
     *
     * @return the head node of this edge
     */
    public NodeComponent getTo() {
        return to;
    }
    
    /**
     * Sets the head node of this edge.
     *
     * @param to the head node of this edge
     */
    public void setTo(NodeComponent to) {
        if(this.to != null)
            this.to.removeComponentListener(this);
        this.to = to;
        this.to.addComponentListener(this);
    }
    
    /**
     * Returns the type of relation between nodes.
     *
     * @return the type of relation between nodes
     */
    public EdgeType getType() {
        return type;
    }
    
    /**
     * Sets the type of relation between nodes.
     *
     * @param type type of relations between nodes
     */
    public void setType(EdgeType type) {
        this.type = type;
    }
    
    /**
     * Paints the edge.
     *
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        //g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        Point[] p = selectPoints();
        
        int x1 = (int)p[0].getX() - getX();
        int x2 = (int)p[1].getX() - getX();
        int y1 = (int)p[0].getY() - getY();
        int y2 = (int)p[1].getY() - getY();
        
        /*int x1 = (int)from.getTopMiddle().getX() - getX();
        int x2 = (int)to.getBottomMiddle().getX() - getX();
        int y1 = (int)from.getTopMiddle().getY() - getY();
        int y2 = (int)to.getBottomMiddle().getY() - getY();*/
        switch(getType()) {
            case GENERALIZATION:
                Draw.drawArrow(g, x1, y1, x2, y2);
                break;
            case REALIZATION:
                Draw.drawDashedArrow(g, x1, y1, x2, y2);
                break;
            case AGGREGATION:
                Draw.drawDiamondEndLine(g, x2, y2, x1, y1, false);
                break;
            case COMPOSITION:
                Draw.drawDiamondEndLine(g, x2, y2, x1, y1, true);
                break;
            default:
                g.drawLine(x1, y1, x2, y2);
                break;
        }
        g.dispose();
    }
    
    /*private Point[] selectClosestPoints(NodeComponent from, NodeComponent to) {
        Point[] tab = new Point[4];
        if(from.getX() > to.getX()) {
            tab[0] = from.getLeftMiddle();
            tab[2] = to.getRightMiddle();
        } else {
            tab[0] = from.getRightMiddle();
            tab[2] = to.getLeftMiddle();
        }
        if(from.getY() > to.getY()) {
            tab[1] = from.getTopMiddle();
            tab[3] = to.getBottomMiddle();
        } else {
            tab[1] = from.getBottomMiddle();
            tab[3] = to.getTopMiddle();
        }
        Map<Double, Point[]> distances = new HashMap<Double, Point[]>();
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                distances.put(MathUtils.dist(tab[i], tab[2 + j]), new Point[]{tab[i], tab[2 + j]});
            }
        }
        double currentDist = Double.MAX_VALUE;
        Point[] result = null;
        for(Double d : distances.keySet()) {
            if(d < currentDist) {
                currentDist = d;
                result = distances.get(d);
            }
        }
        return result;
    }*/
    
    private Point[] selectPoints() {
        if(type.equals(EdgeType.REALIZATION) || type.equals(EdgeType.GENERALIZATION))
            return new Point[]{from.getTopMiddle(), to.getBottomMiddle()};
        else if(from.getMiddle().getX() > to.getMiddle().getX())
            return new Point[]{from.getLeftMiddle(), to.getRightMiddle()};
        else
            return new Point[]{from.getRightMiddle(), to.getLeftMiddle()};
    }
    
    private void update() {
        Point[] p = selectPoints();
        int newX = (int) Math.min(p[0].getX(), p[1].getX()) - minSize / 2;
        int newWidth = (int) Math.abs(p[0].getX() - p[1].getX()) + minSize;
        int newY = (int) Math.min(p[0].getY(), p[1].getY()) - minSize / 2;
        int newHeight = (int) Math.abs(p[0].getY() - p[1].getY()) + minSize;
        setLocation(newX, newY);
        setSize(newWidth, newHeight);
    }
    
    /**
     * Invoked when the component's size changes.
     *
     * @param e ComponentEvent
     */
    public void componentResized(ComponentEvent e) {
        update();
    }
    
    
    /**
     * Invoked when the component's position changes.
     *
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e) {
        update();
    }
    
    
    /**
     * Invoked when the component has been made visible.
     *
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e) {
    }
    
    
    /**
     * Invoked when the component has been made invisible.
     *
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e) {
    }
}
