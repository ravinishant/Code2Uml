/*
 * ImageSaver.java
 *
 * Created on August 26, 2007, 2:25 PM
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
import java.util.Observer;

/**
 * Defines methods used to save contents of a java.awt.Component to a file.
 * More precisely, it saves an image which is created by calling Component's 
 * paint() method. Implementing class must allow adding observers to it and 
 * should notify them about progress of saving the image. The notification 
 * argument should be an instance of ProgressData.
 *
 * @author Mateusz Wenus
 */
public interface ImageSaver {
    
    /**
     * Saves contents of a Component <code>comp</code> to file <code>filePath
     * </code>. Notifies its observers about progress of saving.
     *
     * @param comp Component which will be saved
     * @param filePath file to save the Component to
     * @throws IOException if an I/O error occurs
     */
    public void saveImage(Component comp, String filePath) throws IOException;
    
    /**
     * Adds an observer to this object. Implementing classes notify their 
     * observers about progress of saving image and they set notification
     * argument to an instance of ProgressData.
     *
     * @param o observer to add
     */
    public void addObserver(Observer o);
}
