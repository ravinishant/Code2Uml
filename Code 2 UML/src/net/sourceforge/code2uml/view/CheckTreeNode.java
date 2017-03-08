/*
 * CheckTreeNode.java
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

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Represents a node of JTree that stores its selection state. Selection does
 * not mean being currently selected node in JTree, it is intended to serve
 * more like a JCheckBox.
 *
 * @author Mateusz Wenus
 */
public class CheckTreeNode extends DefaultMutableTreeNode {
    
    private boolean selected;
    
    /** 
     * Creates a tree node with no parent and no children.
     */
    public CheckTreeNode() {
    }

    /**
     * Creates a tree node with no parent, no children, initialized with
     * the specified user object, and specified selection.
     * @param userObject obejct that contains node's data
     * @param selected true if this node is selected
     */
    public CheckTreeNode(Object userObject, boolean selected) {
        super(userObject);
        this.selected = selected;
    }
    
    /**
     * Returns true if this node has been selected.
     * 
     * @return true if this node has been selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets whether this node has been selected.
     *
     * @param selected true if this node has been selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
