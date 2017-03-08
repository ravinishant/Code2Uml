/*
 * BasicNodeComponent.java
 *
 * Created on 1 August 2007, 17:25
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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import net.sourceforge.code2uml.util.TextSection;

/**
 * Represents a class/interface/enum on an UML class diagram. Displays
 * information about it in a rectangle that consists of four sections,
 * which are (from top to bottom): name, enum values, fields, methods.
 * Information is added to those sections by appropriate methods
 * (addToXxx(String)).
 *
 * @author Mateusz Wenus
 */
public class BasicNodeComponent extends NodeComponent {
    
    private static final int sectionCount = 4;
    private TextSection[] section = new TextSection[sectionCount];
    private FontMetrics fm;
    private int maxStrWidth = 0;
    
    /**
     * Creates a new instance of BasicNodeComponent.
     *
     * @param fm FontMetrics of font that will be used to render text
     * @param backColor background color of this node
     * @param draggable true if this node should allow to b moved by mouse
     */
    public BasicNodeComponent(FontMetrics fm, Color backColor, boolean draggable) {
        super(backColor, draggable);
        this.fm = fm;
        for(int i = 0; i < sectionCount; i++)
            section[i] = new TextSection(fm.getFont(), fm.getHeight(), fm.getAscent(), NodeComponent.padding);
        setSize(0, fm.getHeight());
    }
    
    /**
     * Updates size of node after adding given String.
     *
     * @param str String that has been added
     */
    protected void updateSize(String str) {
        int w = fm.stringWidth(str);
        if(w > maxStrWidth)
            maxStrWidth = w;
        setSize(maxStrWidth + 2 * NodeComponent.padding, getHeight() + fm.getHeight());
    }
    
    /**
     * Add specified String to name section.
     *
     * @param str String to add
     */
    public void addToName(String str) {
        updateSize(str);
        section[0].addString(str);
    }
    
    /**
     * Add specified String to enum section.
     *
     * @param str String to add
     */
    public void addToEnum(String str) {
        updateSize(str);
        section[1].addString(str);
    }
    
    /**
     * Add specified String to fields section.
     *
     * @param str String to add
     */
    public void addToField(String str) {
        updateSize(str);
        section[2].addString(str);
    }
    
    /**
     * Add specified String to methods section.
     *
     * @param str String to add
     */
    public void addToMethod(String str) {
        updateSize(str);
        section[3].addString(str);
    }
    
    /**
     * Paints this component.
     *
     * @param g graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        // The first section has a top and bottom margin with height equal to 
        // half of line height. See constructor and method updateSize() to see 
        // how it works.
        int h = fm.getHeight() / 2;
        for(int i = 0; i < sectionCount; i++) {
            
            // draw lines between sections only if it's necessary
            if(i != 0 && section[i].getHeight() > 0)
                g.drawLine(0, h, getWidth(), h);
            
            // the first section is centered
            if(i == 0)
                section[i].drawCenteredAt(g, 0, h, getWidth(), fm);
            else
                section[i].drawAt(g, 0, h);
            
            h += section[i].getHeight();
            
            // bottom margin of first section
            if(i == 0)
                h += fm.getHeight() / 2;
        }
        g.dispose();
    }
}
