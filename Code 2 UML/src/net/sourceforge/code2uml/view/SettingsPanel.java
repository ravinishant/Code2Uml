/*
 * SettingsPanel.java
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

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerFactory;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * Represens the third tabbed pane in user interface. Must be added to a
 * JTabbedPane to work properly. Allows user choosing varoius diagram
 * generation options. After pressing next it gets UnitInfos chosen by
 * the user in the prevoius step and passes them to the controller to construct
 * a diagram from them.
 *
 * @author Mateusz Wenus
 */
public class SettingsPanel extends javax.swing.JPanel implements Observer {
    
    private Controller controller = ControllerFactory.getInstance();
    private static final String settingsFileName = ".code2uml.settings";
    private Collection<UnitInfo> units;
    
    /**
     * Creates new form SettingsPanel.
     */
    public SettingsPanel() {
        initComponents();
        controller.addObserver(this);
        setBGThreadWorking(false);
        loadSettings();
    }
    
    /**
     * Enables button on this panel when controller finishes processing
     * UnitInfos and creating UML diagram from them; shows this panel when
     * controller finishes getting UnitInfos from files.
     *
     * @param o observale whose state has changed
     * @param arg notification argument
     */
    public void update(Observable o, Object arg) {
        if(o == controller && arg instanceof Object[]) {
            Object[] tab = (Object[]) arg;
            if((Integer)tab[0] == Controller.GRAPH_RESULT) {
                setBGThreadWorking(false);
            }
            if((Integer)tab[0] == Controller.UNITS_RESULT) {
                setAsSelected();
                units = (Collection<UnitInfo>) tab[1];
            }
        }
    }
    
    /**
     * Loads a ConstructionHints object from file
     * <code>SettingsPanel.settingsFileName</code>. That object may then be used
     * to init components on this panel so that they will have the same values
     * as during last program execution.
     */
    private void loadSettings() {
        File file = new File(settingsFileName);
        if(file.exists()) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(file));
                Object obj = in.readObject();
                if(obj instanceof ConstructionHints) {
                    initFromHints((ConstructionHints) obj);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                if(in != null)
                    try {
                        in.close();
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    }
            }
        }
    }
    
    /**
     * Sets this panel as currently selected pane in its parent JTabbedPane.
     */
    private void setAsSelected() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JTabbedPane parent = (JTabbedPane) getParent();
                parent.setSelectedComponent(self());
            }
        });
    }
    
    /**
     * Helper function for inner class.
     */
    private SettingsPanel self() {
        return this;
    }
    
    /**
     * Saves a ConstructionHints object to file
     * <code>SettingsPanel.settingsFileName</code>.
     */
    private void saveSettings(ConstructionHints hints) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(settingsFileName));
            out.writeObject(hints);
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            if(out != null)
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }
    
    /**
     * Inits components on this panel based on a ConstructionHints instance.
     */
    private void initFromHints(ConstructionHints hints) {
        privateCheckBox.setSelected(hints.isPrivateVisible());
        protectedCheckBox.setSelected(hints.isProtectedVisible());
        packageCheckBox.setSelected(hints.isPackageVisible());
        publicCheckBox.setSelected(hints.isPublicVisible());
        staticCheckBox.setSelected(hints.isStaticVisible());
        finalCheckBox.setSelected(hints.isFinalVisible());
        fieldsCheckBox.setSelected(hints.isFieldsVisible());
        methodsCheckBox.setSelected(hints.isMethodsVisible());
        argumentsCheckBox.setSelected(hints.isArgumentsVisible());
        enumsCheckBox.setSelected(hints.isEnumsVisible());
        publicUnitsCheckBox.setSelected(!hints.isNonpublicUnitsVisible());
        generalizationChackBox.setSelected(hints.isGeneralizationDrawn());
        realizationCheckBox.setSelected(hints.isRealizationDrawn());
        hasACheckBox.setSelected(hints.isHasADrawn());
        hasATypeComboBox.setSelectedItem(hints.getHasAType().toString().toLowerCase() + "s");
        previewLabel.setFont(hints.getFont());
        previewLabel.setBackground(hints.getBackColor());
        if(hints.getNodeName().equals("infoNodeComponent"))
            expandRadioButton.setSelected(true);
        else
            fullRadioButton.setSelected(true);
    }
    
    /**
     * Helper method which creates ConstructionHints based on choices made on
     * this panel.
     *
     * @return ContructionHints about how diagram should be created
     */
    private ConstructionHints getHints() {
        ConstructionHints hints = new ConstructionHints();
        hints.setArgumentsVisible(argumentsCheckBox.isSelected());
        hints.setBackColor(previewLabel.getBackground());
        hints.setEnumsVisible(enumsCheckBox.isSelected());
        hints.setFieldsVisible(fieldsCheckBox.isSelected());
        hints.setFinalVisible(finalCheckBox.isSelected());
        hints.setNonpublicUnitsVisible(!publicUnitsCheckBox.isSelected());
        hints.setFont(previewLabel.getFont());
        hints.setMethodsVisible(methodsCheckBox.isSelected());
        hints.setPackageVisible(packageCheckBox.isSelected());
        hints.setPrivateVisible(privateCheckBox.isSelected());
        hints.setProtectedVisible(protectedCheckBox.isSelected());
        hints.setPublicVisible(publicCheckBox.isSelected());
        hints.setStaticVisible(staticCheckBox.isSelected());
        hints.setRealizationDrawn(realizationCheckBox.isSelected());
        hints.setGeneralizationDrawn(generalizationChackBox.isSelected());
        
        if(hasACheckBox.isSelected()) {
            hints.setHasADrawn(true);
            if(hasATypeComboBox.getSelectedItem().equals("aggregations"))
                hints.setHasAType(EdgeType.AGGREGATION);
            else
                hints.setHasAType(EdgeType.COMPOSITION);
        }
        
        if(expandRadioButton.isSelected())
            hints.setNodeName("infoNodeComponent");
        else if(fullRadioButton.isSelected())
            hints.setNodeName("basicNodeComponent");
        
        return hints;
    }
    
    /**
     * Enables and disable graphical components on this panel when the
     * background thread is or is not working.
     *
     * @param b true if and only if the background thread is working
     */
    private void setBGThreadWorking(final boolean b) {
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                jProgressBar.setVisible(b);
                previousButton.setEnabled(!b);
                fontButton.setEnabled(!b);
                colorButton.setEnabled(!b);
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
        drawModeButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        progressBarPanel = new javax.swing.JPanel();
        jProgressBar = new javax.swing.JProgressBar();
        expandRadioButton = new javax.swing.JRadioButton();
        fullRadioButton = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        previewLabel = new javax.swing.JLabel();
        colorButton = new javax.swing.JButton();
        fontButton = new javax.swing.JButton();
        generalizationChackBox = new javax.swing.JCheckBox();
        realizationCheckBox = new javax.swing.JCheckBox();
        hasACheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        hasATypeComboBox = new javax.swing.JComboBox();
        argumentsCheckBox = new javax.swing.JCheckBox();
        methodsCheckBox = new javax.swing.JCheckBox();
        enumsCheckBox = new javax.swing.JCheckBox();
        fieldsCheckBox = new javax.swing.JCheckBox();
        privateCheckBox = new javax.swing.JCheckBox();
        protectedCheckBox = new javax.swing.JCheckBox();
        packageCheckBox = new javax.swing.JCheckBox();
        publicCheckBox = new javax.swing.JCheckBox();
        finalCheckBox = new javax.swing.JCheckBox();
        staticCheckBox = new javax.swing.JCheckBox();
        publicUnitsCheckBox = new javax.swing.JCheckBox();

        jLabel1.setText("Choose what and how should be shown on the diagram.");

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        previousButton.setText("Previous");
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout progressBarPanelLayout = new javax.swing.GroupLayout(progressBarPanel);
        progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        progressBarPanelLayout.setVerticalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(previousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nextButton, previousButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBarPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nextButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(previousButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {nextButton, previousButton});

        drawModeButtonGroup.add(expandRadioButton);
        expandRadioButton.setSelected(true);
        expandRadioButton.setText("expandable - only names, expands to full");
        expandRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        expandRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));

        drawModeButtonGroup.add(fullRadioButton);
        fullRadioButton.setText("full - always all information");
        fullRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fullRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel2.setText("Elements draw mode:");

        previewLabel.setBackground(new java.awt.Color(255, 255, 255));
        previewLabel.setText("ABCDEF abcdef");
        previewLabel.setOpaque(true);

        colorButton.setText("Back Color");
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });

        fontButton.setText("Font");
        fontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontButtonActionPerformed(evt);
            }
        });

        generalizationChackBox.setSelected(true);
        generalizationChackBox.setText("show generalization relationships");
        generalizationChackBox.setToolTipText("Show class inheritance.");
        generalizationChackBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        generalizationChackBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        realizationCheckBox.setSelected(true);
        realizationCheckBox.setText("show realization relationships");
        realizationCheckBox.setToolTipText("Show interface implementation.");
        realizationCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        realizationCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        hasACheckBox.setSelected(true);
        hasACheckBox.setText("show \"has a\" relationships");
        hasACheckBox.setToolTipText("Show \"has a\" relationships.");
        hasACheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        hasACheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        hasACheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hasACheckBoxItemStateChanged(evt);
            }
        });

        jLabel3.setText("...as:");

        hasATypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "aggregations", "compositions" }));

        argumentsCheckBox.setSelected(true);
        argumentsCheckBox.setText("show methods' arguments");
        argumentsCheckBox.setToolTipText("Show types of arguments of methods.");
        argumentsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        argumentsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        methodsCheckBox.setSelected(true);
        methodsCheckBox.setText("show methods");
        methodsCheckBox.setToolTipText("Show methods of classes/interfaces/enums.");
        methodsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        methodsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        enumsCheckBox.setSelected(true);
        enumsCheckBox.setText("show enum values");
        enumsCheckBox.setToolTipText("Show enum values in enum types.");
        enumsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        enumsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        fieldsCheckBox.setSelected(true);
        fieldsCheckBox.setText("show fields");
        fieldsCheckBox.setToolTipText("Show fields of classes/interfaces/enums.");
        fieldsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fieldsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        privateCheckBox.setSelected(true);
        privateCheckBox.setText("show private members");
        privateCheckBox.setToolTipText("Show private fields/methods of classes/interfaces/enums.");
        privateCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        privateCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        protectedCheckBox.setSelected(true);
        protectedCheckBox.setText("show protected members");
        protectedCheckBox.setToolTipText("Show protected fields/methods of classes/interfaces/enums.");
        protectedCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        protectedCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        packageCheckBox.setSelected(true);
        packageCheckBox.setText("show package members");
        packageCheckBox.setToolTipText("Show package fields/methods of classes/interfaces/enums.");
        packageCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        packageCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        publicCheckBox.setSelected(true);
        publicCheckBox.setText("show public members");
        publicCheckBox.setToolTipText("Show public fields/methods of classes/interfaces/enums.");
        publicCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        publicCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        finalCheckBox.setSelected(true);
        finalCheckBox.setText("show final fields");
        finalCheckBox.setToolTipText("Show fields which values cannot change after initialization.");
        finalCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        finalCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        staticCheckBox.setSelected(true);
        staticCheckBox.setText("show static members");
        staticCheckBox.setToolTipText("Show static fields and methods.");
        staticCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        staticCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        publicUnitsCheckBox.setText("show public units only");
        publicUnitsCheckBox.setToolTipText("Show only those classes/interfaces/enums which are visible outside packages they are defined in.");
        publicUnitsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        publicUnitsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(finalCheckBox)
                        .addContainerGap(302, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(generalizationChackBox)
                        .addContainerGap(194, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(realizationCheckBox)
                        .addContainerGap(217, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(previewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(colorButton)
                            .addComponent(fontButton))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(expandRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(fullRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(hasACheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addComponent(argumentsCheckBox)
                            .addComponent(methodsCheckBox)
                            .addComponent(enumsCheckBox)
                            .addComponent(fieldsCheckBox)
                            .addComponent(staticCheckBox))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(publicUnitsCheckBox)
                            .addComponent(privateCheckBox)
                            .addComponent(protectedCheckBox)
                            .addComponent(packageCheckBox)
                            .addComponent(publicCheckBox)
                            .addComponent(hasATypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {colorButton, fontButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(staticCheckBox)
                    .addComponent(publicUnitsCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalCheckBox)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldsCheckBox)
                    .addComponent(privateCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enumsCheckBox)
                    .addComponent(protectedCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(methodsCheckBox)
                    .addComponent(packageCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(argumentsCheckBox)
                    .addComponent(publicCheckBox))
                .addGap(26, 26, 26)
                .addComponent(generalizationChackBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(realizationCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hasACheckBox)
                    .addComponent(hasATypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fontButton))
                    .addComponent(previewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expandRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void hasACheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hasACheckBoxItemStateChanged
        boolean selected = hasACheckBox.isSelected();
        jLabel3.setVisible(selected);
        hasATypeComboBox.setVisible(selected);
    }//GEN-LAST:event_hasACheckBoxItemStateChanged
    
    private void fontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontButtonActionPerformed
        Container parent = getParent();
        while(!(parent instanceof Frame)) {
            parent = parent.getParent();
        }
        FontDialog dialog = new FontDialog((Frame) parent, true);
        dialog.setVisible(true);
        if(dialog.isFontChosen()) {
            previewLabel.setFont(dialog.getSelectedFont());
        }
    }//GEN-LAST:event_fontButtonActionPerformed
    
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        setBGThreadWorking(true);
        ConstructionHints hints = getHints();
        saveSettings(hints);
        JTabbedPane parent = (JTabbedPane) getParent();
        UnitsPanel unitsPanel = (UnitsPanel) parent.getComponentAt(parent.getSelectedIndex() - 1);
        controller.processUnits(units,jProgressBar, hints, getGraphics());
    }//GEN-LAST:event_nextButtonActionPerformed
    
    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        JTabbedPane parent = (JTabbedPane) getParent();
        parent.setSelectedIndex(parent.getSelectedIndex() - 1);
    }//GEN-LAST:event_previousButtonActionPerformed
    
    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorButtonActionPerformed
        JColorChooser chooser = new JColorChooser();
        Color color = chooser.showDialog(this, "select back color", jLabel2.getBackground());
        if(color != null)
            previewLabel.setBackground(color);
    }//GEN-LAST:event_colorButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox argumentsCheckBox;
    private javax.swing.JButton colorButton;
    private javax.swing.ButtonGroup drawModeButtonGroup;
    private javax.swing.JCheckBox enumsCheckBox;
    private javax.swing.JRadioButton expandRadioButton;
    private javax.swing.JCheckBox fieldsCheckBox;
    private javax.swing.JCheckBox finalCheckBox;
    private javax.swing.JButton fontButton;
    private javax.swing.JRadioButton fullRadioButton;
    private javax.swing.JCheckBox generalizationChackBox;
    private javax.swing.JCheckBox hasACheckBox;
    private javax.swing.JComboBox hasATypeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JCheckBox methodsCheckBox;
    private javax.swing.JButton nextButton;
    private javax.swing.JCheckBox packageCheckBox;
    private javax.swing.JLabel previewLabel;
    private javax.swing.JButton previousButton;
    private javax.swing.JCheckBox privateCheckBox;
    private javax.swing.JPanel progressBarPanel;
    private javax.swing.JCheckBox protectedCheckBox;
    private javax.swing.JCheckBox publicCheckBox;
    private javax.swing.JCheckBox publicUnitsCheckBox;
    private javax.swing.JCheckBox realizationCheckBox;
    private javax.swing.JCheckBox staticCheckBox;
    // End of variables declaration//GEN-END:variables
    
}
