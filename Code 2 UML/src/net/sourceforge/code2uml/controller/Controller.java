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
import java.util.Collection;
import java.util.Observer;
import javax.swing.JProgressBar;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * Defines methods used by GUI classes to get information about 
 * classes/interfaces/enums from files and to construct an UML diagram from
 * that information. 
 *
 * @author Mateusz Wenus
 */
public interface Controller {
    
    /**
     * Set to indicate that notification argument is a collection of UnitInfos 
     * defined in given files.
     */
    public static final int UNITS_RESULT = 1;
    
    /**
     * Set to indicate that notification result is a graph of 
     * classes/interfaces/enums and relationships between them.
     */
    public static final int GRAPH_RESULT = 2;
    
    /**
     * Set to indicate that image has been saved.
     */
    public static final int SAVE_RESULT = 3;
    
    /**
     * Set to indicate that notification argument is a collection of qualified
     * names of classes/interfaces/enums defined in given files.
     */
    public static final int NAMES_RESULT = 4;
    
    /**
     * Gets definitions of classes/interfaces/enums from given files. Performs
     * that operation in a background thread so the caller doesn't need to do 
     * so. The controller notifies its observers when it finishes getting that 
     * information; the notification argument is an array of two Objects, the 
     * first of them is Controller.UNITS_RESULT, the second is a collections of 
     * UnitInfos from given files.
     *
     * @param filePaths files to read from
     * @param progress progress bar to show progress on; this argument is 
     *        optional and may be null
     */
    public void processFiles(Collection<String> filePaths, 
                             JProgressBar progress);
    
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
                             Collection<String> namesFilter,
                             JProgressBar progress);
    
    /**
     * Creates a graph of classes/interfaces/enums and their relationships from
     * information about them. Performs that operation in a background thread so
     * the caller doesn't need to do  so.The Controller notifies its observers 
     * when it is done, setting notification argument to an array of two 
     * Obejcts, first of them is Controller.GRAPH_RESULT, the second is the 
     * constructed graph.
     *
     * @param units definitions of classes/interfaces/enums to create graph from
     * @param progress progress bar to show progress on; this argument is 
     *        optional and may be null
     * @param hints hints about what and how should be shown on the diagram
     * @param g graphics object (needed to measure Strings)
     */
    public void processUnits(Collection<UnitInfo> units, JProgressBar progress, 
            ConstructionHints hints, Graphics g);
    
    /**
     * Saves contents of Component comp to file filePath. Performs that 
     * operation in a background thread so the caller doesn't need to do so. The 
     * Controller notifies its observers when it is done, setting norification 
     * argument to Controller.SAVE_RESULT.
     *
     * @param comp Component which will be saved
     * @param filePath path to file to save Component to
     * @param progress progress bar to show progress on; this argument is optional
     *        and may be null
     */
    public void saveImage(Component comp, String filePath, JProgressBar progress);
    
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
    public void retrieveNames(Collection<String> filePaths, 
                              JProgressBar progress);
    
    /**
     * Adds an observer to this controller.
     *
     * @param o observer to add
     */
    public void addObserver(Observer o);
}
