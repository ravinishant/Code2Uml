/*
 * ImageWorkerFactory.java
 *
 * Created on August 26, 2007, 12:39 PM
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

import javax.swing.SwingWorker;

/**
 * Defines methods used to get an ImageSaver to saving an image in chosen format. 
 *
 * @author Mateusz Wenus
 */
public interface ImageSaverFactory {
    
    /**
     * Returns an ImageSaver capable of saving contents of a Component in 
     * format <code>fileExtension</code>.
     *
     * @param fileExtension extension of desired file format
     * @return an ImageSaver capable of saving contents of a Component in 
     *         given format or null if there is no appropriate ImageSaver
     */
    public ImageSaver getImageWorker(String fileExtension);
}
