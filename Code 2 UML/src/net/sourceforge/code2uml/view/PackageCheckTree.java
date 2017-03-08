/*
 * PackageCheckTree.java
 *
 * Created on September 1, 2007, 3:05 PM
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Represents a JTree that consists of JCheckBox nodes and is intended to
 * store hierarchical package/class structure. Defines convenient method which
 * generates a model of this tree from classes/interfaces/enums qualified names.
 *
 * @author Mateusz Wenus
 */
public class PackageCheckTree extends JTree {
    
    /** 
     * Creates a new instance of PackageCheckTree. 
     */
    public PackageCheckTree() {
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
     * Generates a model of this tree from qualified names of classes, enums
     * and interfaces from <code>names</code>. 
     *
     * @param names qualified names of classes/interfaces/enums to generate
     *        tree model from
     * @return a map where keys are qualified names from <code>names</code> and
     *         values are their corresponding tree nodes
     */
    public Map<String, CheckTreeNode> createModel(Collection<String> names) {
        List<String> sortedNames = new ArrayList<String>(names);
        Collections.sort(sortedNames);
        Map<String, CheckTreeNode> result = new HashMap<String, CheckTreeNode>();
        CheckTreeNode root = new CheckTreeNode("root", false);
        DefaultTreeModel model = new DefaultTreeModel(root);
        
        for(String name : sortedNames) {
            StringTokenizer tokenizer = new StringTokenizer(name, ".");
            CheckTreeNode current = root;
            while(tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                boolean exists = false;
                for(int i = 0; i < current.getChildCount(); i++) {
                    CheckTreeNode child = (CheckTreeNode) current.getChildAt(i);
                    if(child.getUserObject().toString().equals(str)) {
                        current = child;
                        exists = true;
                        break;
                    }
                }
                if(!exists) {
                    CheckTreeNode newNode = new CheckTreeNode(str, !str.contains("$"));
                    current.add(newNode);
                    current = newNode;
                }
            }
            result.put(name, current);
        }
        setModel(model);
        return result;
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
                CheckTreeNode node = (CheckTreeNode) root.getChildAt(i);
                
                /*
                 * Selection is propagated only to those nodes which don't have 
                 * '$' character in their name; deselection is always propagated
                 */
                if(!selected || !node.getUserObject().toString().contains("$"))
                    setTreeSelected(node, selected);
            }
        }
    }
    
}
