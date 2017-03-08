/*
 * ImageWorker.java
 *
 * Created on August 26, 2007, 5:10 PM
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

package net.sourceforge.code2uml.image;

import java.awt.Component;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;

/**
 * Represents a background thread which saves contents of given 
 * java.awt.Component as an image file. Fires propertyChangeEvents to inform 
 * about progress of saving; it sets propertyName of such event to "progress",
 * its old value to null and its new value to an instance of ProgressData.
 * <br/><br/>
 *
 * Note: SwingWorker is designed to be executed only once. This means that also
 * ImageWorker should be executed only once, further executions will not result in
 * invoking doInBackground().
 *
 * @author Mateusz Wenus
 */
public class ImageWorker extends SwingWorker implements Observer {
    
    private ImageSaverFactory factory = new ImageSaverFactoryImpl();
    private Component comp;
    private String filePath;
    
    /** 
     * Creates a new instance of ImageWorker.
     *
     * @param comp a java.awt.Component whose contents this ImageWorker will 
     *        save
     * @param filePath path to a file to save <code>comp</code> to
     */
    public ImageWorker(Component comp, String filePath) {
        this.comp = comp;
        this.filePath = filePath;
    }

    /**
     * Saves contents of a Component to an image file (the Component and file
     * path were given at construction time). 
     *
     * @throws Exception as required by SwingWorker
     * @return always null
     */
    protected Object doInBackground() throws Exception {
        ImageSaver saver = factory.getImageWorker(filePath.substring(filePath.lastIndexOf('.') + 1));
        saver.addObserver(this);
        try {
            saver.saveImage(comp, filePath);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Called when underlying ImageServer notifies about progress of its work.
     * Should not be called manually.
     *
     * @param o Observable whose state has changed
     * @param arg notification argument
     */
    public void update(Observable o, Object arg) {
        if(o instanceof ImageSaver) {
            firePropertyChange("progress", null, arg);
        }
    }
    
}
