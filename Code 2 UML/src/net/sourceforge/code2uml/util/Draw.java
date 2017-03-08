/*
 * Draw.java
 *
 * Created on August 5, 2007, 11:25 PM
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

package net.sourceforge.code2uml.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Util class providing drawing methods.
 *
 * @author Mateusz Wenus
 */
public class Draw {
    
    private static final int arrowheadLength = 10;
    private static final int arrowheadWidth = 4;
    private static final int segmentLength = 8;
    private static final int diamondDiagonal = 5;
    /**
     * Creates a new instance of Draw.
     */
    public Draw() {
    }
    
    /**
     * Draws an arrow from (x1, y1) to (x2, y2) using given Graphics.
     *
     * @param g Graphics
     * @param x1 x coordinate of arrow's start point
     * @param y1 y coordinate of arrow's start point
     * @param x2 x coordiante of arrow's end point
     * @param y2 y coordinate of arrow's end point
     */
    public static void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g;
        int dx = x1 - x2;
        int dy = y1 - y2;
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        
        rotate(g2d, x1, y1, x2, y2);
        
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x1, y1, x1, y1 - length + arrowheadLength);
        drawArrowhead(g2d, x1, y1 - length);
    }
    
    /**
     * Draws a dashed arrow from (x1, y1) to (x2, y2) using given Graphics.
     *
     * @param g Graphics
     * @param x1 x coordinate of arrow's start point
     * @param y1 y coordinate of arrow's start point
     * @param x2 x coordiante of arrow's end point
     * @param y2 y coordinate of arrow's end point
     */
    public static void drawDashedArrow(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g;
        int dx = x1 - x2;
        int dy = y1 - y2;
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        
        rotate(g2d, x1, y1, x2, y2);
        g2d.setColor(Color.BLACK);
        
        for(int y = y1; y > y1 - length + arrowheadLength + segmentLength; y -= 2 * segmentLength)
            g.drawLine(x1, y, x1, y - segmentLength);
        drawArrowhead(g2d, x1, y1 - length);
    }
    
    /**
     * Draws a line from (x1, y1) to (x2, y2) that has a diamond at its end
     * (near point (x2, y2)). The diamond may be filled.
     *
     * @param g Graphics
     * @param x1 x coordiante of line's start point
     * @param y1 y coordiante of line's start point
     * @param x2 x coordinate of line's end point
     * @param y2 y coordinate of line's end point
     * @param filled true if and only if the diamond should be filled
     */
    public static void drawDiamondEndLine(Graphics g, int x1, int y1, int x2, int y2, boolean filled) {
        Graphics2D g2d = (Graphics2D) g;
        int dx = x1 - x2;
        int dy = y1 - y2;
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        
        rotate(g2d, x1, y1, x2, y2);
        g2d.setColor(Color.BLACK);
        int diamondBaseY =  y1 - length + 4 * diamondDiagonal;
        g2d.drawLine(x1, y1, x1, diamondBaseY);
        
        int[] xPoints = {x1, x1 - diamondDiagonal, x1, x1 + diamondDiagonal};
        int[] yPoints = {diamondBaseY, diamondBaseY - 2 * diamondDiagonal,
                         y1 - length, diamondBaseY - 2 * diamondDiagonal};

        g2d.drawPolygon(xPoints, yPoints, 4);
        if(filled)
            g2d.fillPolygon(xPoints, yPoints, 4);
    }
    
    /**
     * Draws arrowhead from points (x, y), (x - arrowheadWidth, y + arrowheadLength),
     * (x + arrowheadWidth, y + arrowheadLength). Uses current <code>g</code>'s color.
     *
     * @param g Graphics to use
     * @param x x coordinate of top point of drawn arrowhead
     * @param y y coordinate of top point of drawn arrowhead
     */
    private static void drawArrowhead(Graphics g, int x, int y) {
        g.drawLine(x, y, x - arrowheadWidth, y + arrowheadLength);
        g.drawLine(x - arrowheadWidth, y + arrowheadLength,
                x + arrowheadWidth, y + arrowheadLength);
        g.drawLine(x + arrowheadWidth, y + arrowheadLength, x, y);
    }
    
    /**
     * Applies rotation transoframtion to given graphics object. It is
     * performed around (x1, y1) so that after the rotation (x2, y2) lies above
     * (x1, y1). Formally: <br/>
     * (x2, y2) --> rotation --> (x2', y2'), where: <br/>
     * x2' = x1, <br/>
     * y2' = y1 - sqrt((x1 - x2)^2 + (y1 - y2)^2) <br/>
     *
     * @param g graphics object to apply rotation to
     * @param x1 x coordinate of point around which rotation is performed
     * @param y1 y coordinate of point around which rotation is performed
     * @param x2 x coordinate of point which will be above (x1, y1)
     * @param y2 y coordinate of point which will be above (x1, y1)
     */
    private static void rotate(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double dx = Math.abs(x1 - x2);
        double dy = Math.abs(y1 - y2);
        
        double alpha, beta;
        
        if(dx == 0 || dy == 0) {
            alpha = 0.0;
            beta = 0.0;
        } else {
            alpha = Math.atan(dy / dx);
            beta = Math.atan(dx / dy);
        }
        
        double rotation = 0;
        if(x2 < x1)
            if(y2 < y1)
                rotation = 3.0 / 2.0 * Math.PI + alpha;
            else
                rotation = Math.PI + beta;
        else
            if(y2 < y1)
                rotation = beta;
            else
                rotation = Math.PI / 2 + alpha;
        
        
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        g2d.rotate(rotation, x1, y1);
    }
}
