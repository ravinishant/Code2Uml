/*
 * FilesPanel.java
 *
 * Created on August 6, 2007, 5:21 PM
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerFactory;

/**
 * Represents first of tabbed panes in user interface. Must be added to
 * JTabbedPane to work properly. Allows user to select files from
 * which UML diagram will be created. After pressing next, sends those
 * files to be processed by controller.
 *
 * @author Mateusz Wenus
 */
public class FilesPanel extends javax.swing.JPanel implements Observer {
    
    private Collection<String> selectedFiles = new ArrayList<String>();
    private Controller controller = ControllerFactory.getInstance();
    
    /**
     * Creates new form FilesPanel.
     */
    public FilesPanel() {
        initComponents();
        pathsList.setModel(new DefaultListModel());
        controller.addObserver(this);
        setBGThreadWorking(false);
    }
    
    /**
     * Returns a collection of filepaths selected by the user on this panel.
     * 
     * @return a collection of filepaths selected by the user on this panel
     */
    public Collection<String> getSelectedFiles() {
        return selectedFiles;
    }
    
    /**
     * Enables button on this panel when controller finishes processing files
     * and getting classes/interfaces/enums qualified names from them.
     *
     * @param o observale whose state has changed
     * @param arg notification argument
     */
    public void update(Observable o, Object arg) {
        if(o == controller && arg instanceof Object[]) {
            Object[] tab = (Object[]) arg;
            if((Integer)tab[0] == Controller.NAMES_RESULT) {
                setBGThreadWorking(false);
            }
        }
    }
    
    /** 
     * Enables and disables graphical components on this panel when the
     * background thread is or is not working.
     *
     * @param b true if and only if the background thread is working
     */
    private void setBGThreadWorking(final boolean b) {
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                jProgressBar.setVisible(b);
                addButton.setEnabled(!b);
                removeButton.setEnabled(!b);
                nextButton.setEnabled(!b);
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        pathsListPane = new javax.swing.JScrollPane();
        pathsList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        progressBarPanel = new javax.swing.JPanel();
        jProgressBar = new javax.swing.JProgressBar();

        pathsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        pathsListPane.setViewportView(pathsList);

        jLabel1.setBackground(java.awt.SystemColor.control);
        jLabel1.setText("Use 'Add' button to add .jar or .class files from which you want to create UML class diagram.");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jProgressBar.setStringPainted(true);

        javax.swing.GroupLayout progressBarPanelLayout = new javax.swing.GroupLayout(progressBarPanel);
        progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );
        progressBarPanelLayout.setVerticalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pathsListPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, nextButton, removeButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathsListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progressBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nextButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        setBGThreadWorking(true);
        controller.retrieveNames(selectedFiles, jProgressBar);
    }//GEN-LAST:event_nextButtonActionPerformed
    
    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        DefaultListModel model = (DefaultListModel) pathsList.getModel();
        for(Object selected : pathsList.getSelectedValues()) {
            selectedFiles.remove(selected);
            model.removeElement(selected);
        }
    }//GEN-LAST:event_removeButtonActionPerformed
    
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filer = new FileNameExtensionFilter(".class and .jars", "class", "jar");
        fileChooser.setFileFilter(filer);
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(this);
        
        if(result == JFileChooser.APPROVE_OPTION) {
            DefaultListModel model = (DefaultListModel) pathsList.getModel();
            File[] selected = fileChooser.getSelectedFiles();
            for(File file : selected) {
                String path = file.getAbsolutePath();
                selectedFiles.add(path);
                if(!model.contains(path))
                    model.addElement(path);
            }
        }
    }//GEN-LAST:event_addButtonActionPerformed
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JButton nextButton;
    private javax.swing.JList pathsList;
    private javax.swing.JScrollPane pathsListPane;
    private javax.swing.JPanel progressBarPanel;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
    
}
