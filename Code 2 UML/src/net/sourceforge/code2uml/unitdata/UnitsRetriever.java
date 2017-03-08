/*
 * UnitsRetriever.java
 *
 * Created on 28 July 2007, 14:32
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

package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import java.util.Observer;

/**
 * Defines methods used to get information about classes/interfaces/enums defined
 * in given files. A class implementing this interface must allow adding observers 
 * to it and must notify about progress of processing files (it can be achieved 
 * easily by extending java.util.Observable).
 *
 * @author Mateusz Wenus
 */
public interface UnitsRetriever {
    
    /**
     * Gets objects representing classes/interfaces/enums defined in given files.
     * Must not return two or more UnitInfos u1 and u2 such that 
     * <code>u1.getName().equals(u2.getName())</code>. Notifies its observers
     * about progress of processing files from <code>filePaths</code>. When it
     * notifies its observers its sets notification argument to ProgressData 
     * instance.
     *
     * @param filePaths paths of files to read
     * @return objects representing classes/interfaces/enums defined in given 
     *         files
     */
    public Collection<UnitInfo> retrieve(Collection<String> filePaths);
    
    /**
     * Returns from given files objects representing classes/interfaces/enums 
     * which qualified names are in <code>namesFilter</code>.
     * Must not return two or more UnitInfos u1 and u2 such that 
     * <code>u1.getName().equals(u2.getName())</code>. Notifies its observers
     * about progress of processing files from <code>filePaths</code>. When it
     * notifies its observers its sets notification argument to ProgressData 
     * instance.
     *
     * @param filePaths paths to files to read
     * @param namesFilter qualified names of classes/interfaces/enums that are
     *        allowed to be returned; if it is null then all 
     *        classes/interfaces/enums are returned
     * @return objects representing classes/interfaces/enums defined in given 
     *         files
     */
    public Collection<UnitInfo> retrieve(Collection<String> filePaths, 
                                         Collection<String> namesFilter);
    
    /**
     * Returns qualified names of classes/interfaces/enums defined in given 
     * files. Notifies its observers about progress of processing files using
     * ProgressData instance as notification argument.
     *
     * @param filePaths paths to files to read
     * @return qualified names of classes/interfaces/enums defined in given 
     *         files
     */
    public Collection<String> retrieveNames(Collection<String> filePaths);
    
    /**
     * Adds a observer to this object.
     * 
     * @param o observer to add
     */
    public void addObserver(Observer o);
    
}
