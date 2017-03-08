/*
 * InfoNodeComponent.java
 *
 * Created on 1 August 2007, 18:27
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import net.sourceforge.code2uml.util.MouseDragListener;
import net.sourceforge.code2uml.util.TextSection;

/**
 * Represents a class/interface/enum on an UML class diagram. Displays
 * only information about its name and type (whether it is a class,
 * an enum or an interface). After clicking right mouse button on
 * it, it displays detailed information using helper BasicNodeComponent.
 *
 * @author Mateusz Wenus
 */
public class InfoNodeComponent extends NodeComponent {
    
    private FontMetrics fm;
    private BasicNodeComponent info;
    private TextSection nameSection;
    private int maxStrWidth = 0;
    
    /**
     * Creates a new instance of InfoNodeComponent.
     *
     * @param fm FontMetrics of a font that will be used to render text
     * @param backColor background color of this node
     * @param draggable true if this node should allow to be moved by mouse
     */
    public InfoNodeComponent(FontMetrics fm, Color backColor, boolean draggable) {
        super(backColor, draggable);
        this.fm = fm;
        
        info = new BasicNodeComponent(fm, backColor, false);
        nameSection = new TextSection(fm.getFont(), fm.getHeight(), fm.getAscent(), NodeComponent.padding);
        setSize(0, fm.getHeight());
        
        info.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == e.BUTTON3 && info.isEnabled()) {
                    Container parent = info.getParent();
                    parent.remove(info);
                    parent.repaint();
                }
            }
        });
        
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == e.BUTTON3 && isEnabled()) {
                    final Container parent = getParent();
                    info.setLocation(Math.max(0, Math.min(parent.getWidth() - info.getWidth(), getX() + (getWidth() - info.getWidth()) / 2)),
                            Math.max(0, Math.min(parent.getHeight() - info.getHeight(), getY() + (getHeight() - info.getHeight()) / 2)));
                    parent.add(info, 0);
                    parent.repaint();
                    
                    // the expanded node may be bigger than its parent
                    Dimension dim = new Dimension(Math.max(info.getWidth(), parent.getWidth()),
                                                  Math.max(info.getHeight(), parent.getHeight()));
                    if(!parent.getSize().equals(dim)) {
                        parent.setSize(dim);
                    }
                    
                    // stop dragging this node when it is expanded
                    for(MouseMotionListener m : getMouseMotionListeners()) {
                        if(m instanceof MouseDragListener)
                            removeMouseMotionListener(m);
                    }
                }
            }
        });
    }
    
    /**
     * Updates size of this node after adding specified String to it.
     *
     * @param str String that has been added.
     */
    protected void updateSize(String str) {
        int w = fm.stringWidth(str);
        if(w > maxStrWidth)
            maxStrWidth = w;
        setSize(maxStrWidth + 2 * NodeComponent.padding, getHeight() + fm.getHeight());
    }
    
    /**
     * Adds general information to name section of this node and its helper
     * BasicNodeComponent.
     *
     * @param str String to add
     */
    public void addToName(String str) {
        updateSize(str);
        nameSection.addString(str);
        info.addToName(str);
    }
    
    /**
     * Add specified String to enum section of helper BasicNodeComponent.
     *
     * @param str String to add
     */
    public void addToEnum(String str) {
        info.addToEnum(str);
    }
    
    /**
     * Add specified String to fields section of helper BasicNodeComponent.
     *
     * @param str String to add
     */
    public void addToField(String str) {
        info.addToField(str);
    }
    
    /**
     * Add specified String to methods section of helper BasicNodeComponent.
     *
     * @param str String to add
     */
    public void addToMethod(String str) {
        info.addToMethod(str);
    }
    
    /**
     * Paints this node.
     *
     * @param g graphics.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        // There is top and bottom margin with height equal to half of line
        // height. See constructor and method updateSize() to see how it
        // works.
        nameSection.drawCenteredAt(g, 0, fm.getHeight() / 2, getWidth(), fm);
        g.dispose();
    }
}
