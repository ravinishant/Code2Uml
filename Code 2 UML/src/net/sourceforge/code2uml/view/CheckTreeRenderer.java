/*
 * CheckTreeRenderer.java
 *
 * Created on August 13, 2007, 7:55 PM
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

package net.sourceforge.code2uml.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 * Represents an object that displays nodes of a CheckTree.
 *
 * @author Mateusz Wenus
 */
public class CheckTreeRenderer extends JPanel implements TreeCellRenderer {
    
    private JLabel label = new JLabel();
    private JCheckBox checkBox = new JCheckBox();
    private Icon leafIcon;
    private Icon nodeClosedIcon;
    private Icon nodeOpenIcon;
    
    /**
     * Creates a new instance of CheckTreeRenderer.
     */
    public CheckTreeRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(checkBox);
        add(label);
        
        checkBox.setOpaque(false);
        setOpaque(false);
        
        URL leafIconUrl = ClassLoader.getSystemResource("net/sourceforge/code2uml/icons/leaf.png");
        if(leafIconUrl != null)
            leafIcon = new ImageIcon(leafIconUrl);
        URL nodeClosedIconUrl = ClassLoader.getSystemResource("net/sourceforge/code2uml/icons/node-closed.png");
        if(nodeClosedIconUrl != null)
            nodeClosedIcon = new ImageIcon(nodeClosedIconUrl);
        URL nodeOpenIconUrl = ClassLoader.getSystemResource("net/sourceforge/code2uml/icons/node-open.png");
        if(nodeOpenIconUrl != null)
            nodeOpenIcon = new ImageIcon(nodeOpenIconUrl);
    }
    
    /**
     * Returns a component which renderer uses to draw the value (tree node).
     *
     * @param tree tree that node belongs to
     * @param value value of drawn node
     * @param selected true if node is selected (not in terms of JCheckBox)
     * @param expanded true if node is expanded
     * @param leaf true if node is leaf
     * @param row no idea
     * @param hasFocus true if node hasFocus
     * @return a component which renderer uses to draw the value (tree node)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        
        CheckTreeNode node = (CheckTreeNode) value;
        checkBox.setSelected(node.isSelected());
        label.setText(node.toString());
        
        if(leaf)
            label.setIcon(leafIcon);
        else {
            if(expanded)
                label.setIcon(nodeOpenIcon);
            else
                label.setIcon(nodeClosedIcon);
        }
        
        setSize(checkBox.getWidth() + label.getWidth(),
                Math.max(checkBox.getHeight(), label.getHeight()));
        return this;
    }

    /*public Dimension getPreferredSize() {
        return new Dimension(checkBox.getWidth() + label.getWidth(),
                Math.max(checkBox.getHeight(), label.getHeight()));
    }*/
    

}
