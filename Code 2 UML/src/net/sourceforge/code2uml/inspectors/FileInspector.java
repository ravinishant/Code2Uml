/*
 * FileInspector.java
 *
 * Created on 27 July 2007, 07:38
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
 * Defines methods used to get definitions of classes/interfaces/enums from
 * a file. Implementing class may (but this is not required) extend 
 * java.util.Observable class, if it does it should notify its observers
 * about progress of reading given file, setting notification argument to an
 * Integer equal to: <br/>
 * - during execution of inspect() -> number of classes/interfaces/enums found
 *   so far <br/>
 * - during execution of glance() -> number of qualified names of 
 *   classes/interfaces/enums found so far <br/>
 * This may be useful for inspectors dealing with large files (for example .jars).
 *
 * @author Mateusz Wenus
 */
public interface FileInspector {
    
    /**
     * Returns definitions of all classes/interfaces/enums from specified file.
     * <br/><code>inspect(filePath, null)</code> is equivalent to 
     * <code>inspect(filePath)</code>
     *
     * @param filePath path to the file to read from
     * @return collection of objects representing classes/interfaces/enums
     *         defined in file <code>filePath</code> or null
     */
    public Collection<UnitInfo> inspect(String filePath);
    
    /**
     * Returns from specified file definitions of those classes/interfaces/enums
     * which have qualified names belonging to <code>namesFilter</code>. If 
     * <code>namesFilter</code> is null, all definitions are returned. <br/>
     * <code>inspect(filePath, null)</code> is equivalent to 
     * <code>inspect(filePath)</code>
     *
     * @param filePath path to the file to read from
     * @param namesFilter collection of qualified names of classes/interfaces/enums
     *        which are allowed to be returned; if this parameter is null all 
     *        definitions will be returned
     * @return collection of objects representing classes/interfaces/enums
     *         defined in file <code>filePath</code> which names are in <code>
     *         namesFiler</code> or null
     */
    public Collection<UnitInfo> inspect(String filePath, Collection<String> namesFilter);
    
    /**
     * Returns qualified names of classes/interfaces/enums defined in specified 
     * file.
     *
     * @param filePath file to read from
     * @return qualified names of classes/interfaces/enums defined in specified 
     *         file
     */
    public Collection<String> glance(String filePath);
}
