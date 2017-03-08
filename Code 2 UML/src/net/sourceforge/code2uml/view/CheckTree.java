/*
 * CheckTree.java
 *
 * Created on August 13, 2007, 7:54 PM
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * Represents JTree that consists of JCheckBox nodes. Selecting and deselecting
 * a node propagates its state to all of its children.
 *
 * @author Mateusz Wenus
 */
public class CheckTree extends JTree {
    
    /** Creates a new instance of CheckTree. */
    public CheckTree() {
        setModel(null);
        setCellRenderer(new CheckTreeRenderer());
        setRootVisible(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                TreePath path = getPathForLocation(e.getX(), e.getY());
                if(path != null) {
                    CheckTreeNode node = (CheckTreeNode) path.getLastPathComponent();
                    setTreeSelected(node, !node.isSelected());
                    repaint();
                }
            }
        });
    }
    
    /**
     * Sets selection state of given node to specified value and propagates its
     * state to its children.
     *
     * @param root node that will have its selection state set and propagated
     * @param selected selection state
     */
    private void setTreeSelected(CheckTreeNode root, boolean selected) {
        root.setSelected(selected);
        if(!root.isLeaf()) {
            for(int i = 0; i < root.getChildCount(); i++) {
                setTreeSelected((CheckTreeNode) root.getChildAt(i), selected);
            }
        }
    }
}
