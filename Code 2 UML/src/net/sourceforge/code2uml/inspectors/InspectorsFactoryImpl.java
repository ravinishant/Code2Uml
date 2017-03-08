/*
 * InspectorFactoryImpl.java
 *
 * Created on 27 July 2007, 21:56
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

package net.sourceforge.code2uml.inspectors;

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.code2uml.inspectors.java.ClassFileInspector;
import net.sourceforge.code2uml.inspectors.java.JarFileInspector;

/**
 * This class is responsible for selecting the right FileInspector for given
 * file extension. 
 *
 * @author Mateusz Wenus
 */
public class InspectorsFactoryImpl implements InspectorsFactory {
    
    Map<String, FileInspector> inspectors = new HashMap<String, FileInspector>();
    FileInspector nullInspector = new NullFileInspector();
    
    /**
     * Creates a new instance of InspectorsFactoryImpl.
     */
    public InspectorsFactoryImpl() {
        inspectors.put("class", new ClassFileInspector());
        inspectors.put("jar", new JarFileInspector());
    }
    
    /**
     * Get a FileInspector for given file. If file extension is not recognized a 
     * special NullFileInspector is returned which ignores (returns null) when 
     * asked to inspect a file. This method never returns null.
     *
     * @param fileExtension extension of a file
     * @return FileInspector for given type of file or NullFileInspector if this file 
     *         extension is not recognized
     */
    public FileInspector getInspector(String fileExtension) {
        FileInspector inspector = inspectors.get(fileExtension);
        return inspector != null? inspector : nullInspector;
    }
    
}
