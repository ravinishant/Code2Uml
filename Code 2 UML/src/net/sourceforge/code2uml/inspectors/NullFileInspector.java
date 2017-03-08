/*
 * NullFileInspector.java
 *
 * Created on 27 July 2007, 17:08
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

import java.util.Collection;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * FileInspector intended for inspecting unrecognized files. Always returns null.
 *
 * @author Mateusz Wenus
 */
public class NullFileInspector implements FileInspector {
    
    /**
     * Creates a new instance of NullFileInspector.
     */
    public NullFileInspector() {
    }

    /**
     * Returns null.
     *
     * @param filePath file to inspect
     * @return null
     */
    public Collection<UnitInfo> inspect(String filePath) {
        return null;
    }

    /**
     * Returns null.
     * 
     * @param filePath file to read from
     * @return null
     */
    public Collection<String> glance(String filePath) {
        return null;
    }

    /**
     * Returns null.
     * 
     * @param filePath path to the file to read from
     * @param namesFilter collection of qualified names of classes/interfaces/enums
     *        which are allowed to be returned; if this parameter is null all 
     *        definitions will be returned
     * @return null
     */
    public Collection<UnitInfo> inspect(String filePath, Collection<String> namesFilter) {
        return null;
    }
    
}
