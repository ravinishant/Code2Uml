/*
 * MathUtils.java
 *
 * Created on September 5, 2007, 8:56 AM
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

import java.awt.Point;

/**
 * Defines conveniance methods for some mathematical operations.
 *
 * @author Mateusz Wenus
 */
public class MathUtils {
    
    /**
     * Returns the distance between points represented by vectors v1 and v2.
     *
     * @param v1 coordinates of first point
     * @param v2 coordinates of second point
     * @return the distance between points represented by vectors v1 and v2
     */
    public static double dist(Vector2D v1, Vector2D v2) {
        return Math.sqrt((v1.getX() - v2.getX()) * (v1.getX() - v2.getX()) +
                         (v1.getY() - v2.getY()) * (v1.getY() - v2.getY()));
    }
    
    /**
     * Returns a unit vector with direction of a vector from point v1 to v2.
     *
     * @param v1 coordinates of start point
     * @param v2 coordinates of end point
     * @return a unit vector with direction of a vector from point v1 to v2
     */
    public static Vector2D createUnitVector(Vector2D v1, Vector2D v2) {
        double length = Math.max(0.0001, MathUtils.dist(v1, v2));
        Vector2D result = new Vector2D(v2.getX() - v1.getX(), v2.getY() - v1.getY());
        result.setX(result.getX() / length);
        result.setY(result.getY() / length);
        return result;
    }
}
