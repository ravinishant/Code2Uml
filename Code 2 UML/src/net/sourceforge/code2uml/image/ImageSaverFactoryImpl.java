/*
 * ImageSaverFactoryImpl.java
 *
 * Created on August 26, 2007, 2:29 PM
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

import java.util.HashMap;
import java.util.Map;

/**
 * Returns ImageSavers capable of saving images in chosen formats.
 *
 * @author Mateusz Wenus
 */
public class ImageSaverFactoryImpl implements ImageSaverFactory {
    
    private Map<String, ImageSaver> savers = new HashMap<String, ImageSaver>();
    
    /** 
     * Creates a new instance of ImageSaverFactoryImpl. 
     */
    public ImageSaverFactoryImpl() {
        savers.put("bmp", new BMPImageSaver());
    }

    /**
     * Returns an ImageSaver capable of saving contents of a Component in 
     * format <code>fileExtension</code>.
     *
     * @param fileExtension extension of desired file format
     * @return an ImageSaver capable of saving contents of a Component in 
     *         given format or null if there is no appropriate ImageSaver
     */
    public ImageSaver getImageWorker(String fileExtension) {
        return savers.get(fileExtension);
    }
    
}
