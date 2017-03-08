/*
 * Controller.java
 *
 * Created on August 6, 2007, 11:04 PM
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

package net.sourceforge.code2uml.controller;

import java.awt.Component;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.GraphWorker;
import net.sourceforge.code2uml.image.ImageWorker;
import net.sourceforge.code2uml.unitdata.NamesWorker;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.unitdata.UnitsWorker;
import net.sourceforge.code2uml.util.ProgressData;

/**
 * Provides methods used by GUI classes to get information about
 * classes/interfaces/enums from files and to construct an UML diagram from
 * that information. Extends Observable to allow easy adding of observers,
 * notifies them when ordered work is done.
 *
 * @author Mateusz Wenus
 */
class ControllerImpl extends Observable implements Controller {
    
    /**
     * Gets definitions of classes/interfaces/enums from given files. Controller
     * notifies its observers when it finishes getting that information. 
     * Notification argument is an array of two Objects, first of them is 
     * Controller.UNITS_RESULT, the second is a collections of UnitInfos
     * from given files.
     * 
     * @param filePaths files to read from
     * @param progress progress bar to show progress on; this argument is optional and
     *        may be null
     */
    public void processFiles(Collection<String> filePaths, JProgressBar progress) {
        processFiles(filePaths, null, progress);
    }
    
    /**
     * Gets definitions of classes/interfaces/enums from given files. Performs
     * that operation in a background thread so the caller doesn't need to do 
     * so. The controller notifies its observers when it finishes getting that 
     * information; the notification argument is an array of two Objects, the 
     * first of them is Controller.UNITS_RESULT, the second is a collections of 
     * UnitInfos from given files which have qualified names in <code>
     * namesFilter</code>.
     *
     * @param filePaths files to read from
     * @param namesFilter qualified names of classes/interfaces/enums that can
     *        be returned; if this argument is null all classes/interfaces/enums
     *        will be returned
     * @param progress progress bar to show progress on; this argument is 
     *        optional and may be null
     */
    public void processFiles(Collection<String> filePaths, 
                  Collection<String> namesFilter, final JProgressBar progress) {
        
        final UnitsWorker worker = new UnitsWorker(filePaths, namesFilter);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if(name.equals("progress") && progress != null) {
                    ProgressData data = (ProgressData) evt.getNewValue();
                    setProgress(progress, (int) data.getProgress(), data.getMessage());
                } else if(name.equals("state") && evt.getNewValue().equals(StateValue.DONE)) {
                    try {
                        Collection<UnitInfo> units = worker.get();
                        setChanged();
                        notifyObservers(new Object[]{Controller.UNITS_RESULT, units});
                        clearChanged();
                    } catch (ExecutionException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Creates a graph of classes/interfaces/enums and their relationships from
     * information about them. The Controller notifies its observers when it is
     * done, setting notification argument to an array of two Obejcts, first of
     * them is Controller.GRAPH_RESULT, the second is the constructed graph.
     *
     * @param units definitions of classes/interfaces/enums to create graph from
     * @param progress progress bar to show progress on; this argument is optional and
     *        may be null
     * @param hints hints about what and how should be shown on the diagram
     * @param g graphics object (needed to measure Strings)
     */
    public void processUnits(Collection<UnitInfo> units, 
            final JProgressBar progress, ConstructionHints hints, Graphics g) {
        
        final GraphWorker worker = new GraphWorker(units, g, hints);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if(name.equals("progress") && progress != null) {
                    ProgressData data = (ProgressData) evt.getNewValue();
                    setProgress(progress, (int) data.getProgress(), data.getMessage());
                } else if(name.equals("state") && evt.getNewValue().equals(StateValue.DONE)) {
                    try {
                        Graph g = worker.get();
                        setChanged();
                        notifyObservers(new Object[]{Controller.GRAPH_RESULT, g});
                        clearChanged();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Saves contents of Component comp to file filePath. The Controller notifies
     * its observers when it is done, setting norification argument to 
     * Controller.SAVE_RESULT.
     *
     * @param comp Component which will be saved
     * @param filePath path to file to save Component to
     * @param progress progress bar to show progress on; this argument is optional
     *        and may be null
     */
    public void saveImage(Component comp, String filePath, final JProgressBar progress) {
        SwingWorker worker = new ImageWorker(comp, filePath);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if(name.equals("progress")) {
                    ProgressData data = (ProgressData) evt.getNewValue();
                    setProgress(progress, (int) data.getProgress(), "saving...");
                } else if(name.equals("state") && evt.getNewValue() == StateValue.DONE) {
                    setChanged();
                    notifyObservers(Controller.SAVE_RESULT);
                    clearChanged();
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Gets qualified names of classes/interfaces/enums defined in given files.
     * Performs that operation in a background thread so the caller doesn't 
     * need to do so. The Controller notifies its observer when it is done, 
     * setting notification argument to an array of two Objects: first of them
     * is Controller.NAME_RESULT, the second is a collection containing found
     * qualified names.
     *
     * @param filePaths files to read
     * @param progress progress bar to show progress on; this argument is 
     *        optional and may be null
     */
    public void retrieveNames(Collection<String> filePaths, final JProgressBar progress) {
        final SwingWorker worker = new NamesWorker(filePaths);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if(name.equals("progress")) {
                    ProgressData progressData = (ProgressData) evt.getNewValue();
                    setProgress(progress, (int) progressData.getProgress(), progressData.getMessage());
                } else if(name.equals("state") && evt.getNewValue().equals(StateValue.DONE)) {
                    try {
                        Object result = worker.get();
                        setChanged();
                        notifyObservers(new Object[]{Controller.NAMES_RESULT, result});
                        clearChanged();
                    } catch (ExecutionException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    
                }
            }
        });
        worker.execute();
    }

    /**
     * Helper function which displays progress of an operation ordered by
     * a Controller on a JProgressBar.
     *
     * @param progress the JPogressBar to show progress on
     * @param value value which will be set by calling progress setValue()
     *        method
     * @param str text to be displayed on the JProgressBar; this argument is
     *        optional and may be null
     */
    private void setProgress(final JProgressBar progress, final int value, final String str) {
        if(progress == null)
            return;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                progress.setValue(value);
                if(str != null)
                    progress.setString(str);
            }
        });
    }
}
