/*
 * TextSection.java
 *
 * Created on 1 August 2007, 16:52
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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class stores Strings and allowes displaying them in lines (one
 * String per line). It allows to choose font and text padding (only
 * horizontal). In order to layout text properly, it needs inforamtion 
 * about height of text and its ascent (getHeight() and getAscent() 
 * defined in FontMetrics).
 *
 * @author Mateusz Wenus
 */
public class TextSection {
    
    private Font font;
    private int lineHeight;
    private int ascent;
    private int padding;
    private Collection<String> strings = new LinkedList<String>();
            
    /**
     * 
     * Creates a new instance of TextSection.
     * @param font font which will be used to render text
     * @param lineHeight height of a line of text with specified font
     * @param ascent ascent of specified font
     * @param padding horizontal text padding
     */
    public TextSection(Font font, int lineHeight, int ascent, int padding) {
        this.font = font;
        this.lineHeight = lineHeight;
        this.ascent = ascent;
        this.padding = padding;
    }

    /**
     * Returns height of line of text.
     *
     * @return height of line of text
     */
    public int getLineHeight() {
        return lineHeight;
    }

    /**
     * Returns total height of all lines of text.
     *
     * @return total height of all lines of text
     */
    public int getHeight() {
        return lineHeight * strings.size();
    }
    
    /**
     * Adds specified String to list of Strings displayed by this object.
     * The String will be displayed under all Strings that have already been 
     * added.
     *
     * @param str String to add
     */
    public void addString(String str) {
        strings.add(str);
    }
    
    /**
     * Draws all Strings, one per line. Top-left corner of first line will be
     * at (x, y).
     *
     * @param g graphics 
     * @param x x coordiante of top-left corner
     * @param y y coordiante of top-left corner
     */
    public void drawAt(Graphics g, int x, int y) {
        g.setFont(font);
        g.setColor(Color.BLACK);
        int count = 0;
        Iterator<String> it = strings.iterator();
        while(it.hasNext()) {
            g.drawString(it.next(), x + padding, y + count * lineHeight + ascent);
            count++;
        }
    }
    
     /**
     * Draws all Strings, one per line. Top-left corner of first line will be
     * at (x, y). The Strings are centered.
     *
     * @param g graphics 
     * @param x x coordiante of top-left corner
     * @param y y coordiante of top-left corner
     * @param width width of line
     * @param fm FontMetrics e
     */
    public void drawCenteredAt(Graphics g, int x, int y, int width, FontMetrics fm) {
        g.setFont(font);
        g.setColor(Color.BLACK);
        int count = 0;
        Iterator<String> it = strings.iterator();
        while(it.hasNext()) {
            String str = it.next();
            g.drawString(str, (width - fm.stringWidth(str)) / 2, y + count * lineHeight + ascent);
            count++;
        }
    }
}
