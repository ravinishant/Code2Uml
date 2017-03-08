/*
 * Vector2D.java
 *
 * Created on September 5, 2007, 9:39 AM
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

import java.awt.Dimension;
import java.awt.Point;

/**
 * Represents two-dimensional vector with (x, y) coordinates stored in double
 * precision.
 *
 * @author Mateusz Wenus
 */
public class Vector2D {
    
    private double x;
    private double y;
    
    /** 
     * Creates a new instance of Vector2D. 
     */
    public Vector2D() {
    }

    /**
     * Creates a new instance of Vector2D and initializes it with given values.
     *
     * @param x value of x coordinate of this vector
     * @param y value of y coordinate of this vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Creates a new instance of Vector2d using values from Point p.
     *
     * @param p Point which contains coordinates of the new vector.
     */
    public Vector2D(Point p) {
        setX(p.getX());
        setY(p.getY());
    }

    /**
     * Returns x coordinate of this vector.
     *
     * @return x coordinate of this vector
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x coordinate of this vector.
     * 
     * @param x new value of x coordinate of this vector
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns y coordinate of this vector.
     *
     * @return y coordinate of this vector
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y coordinate of this vector.
     * 
     * @param y new value of x coordinate of this vector
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Increases coordinates of this vector by coordinates of vector <code>v
     * </code>
     *
     * @param v Vector2D which will be added to this vector
     */
    public void add(Vector2D v) {
        x += v.getX();
        y += v.getY();
    }
    
    /**
     * Returns a point which has the same coordinates as this vector (rounded 
     * to integers).
     *
     * @return a point which has the same coordinates as this vector (rounded
     *         to integers)
     */
    public Point toPoint() {
        return new Point((int) x, (int) y);
    }
    
    /**
     * Returns String representation of this vector in format [x, y].
     *
     * @return String representation of this vector in format [x, y]
     */
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
