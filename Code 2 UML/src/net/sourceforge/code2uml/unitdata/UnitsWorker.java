/*
 * UnitsWorker.java
 *
 * Created on 28 July 2007, 14:48
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
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;

/**
 * Represents a background thread which gets definitions of classes/interfaces/enums
 * from given files. It uses UnitsRetriever for reading those files and registers as its
 * observer so that it is notified after each file is read. And whenever a file is read
 * it fires PropertyChangeEvent, setting property with name "progress", old value equal
 * null and new value being a ProgressData instance.
 * <br/><br/>
 *
 * Note: SwingWorker is designed to be executed only once. This means that also
 * UnitsWorker should be executed only once, further executions will not result in
 * invoking doInBackground().
 *
 * @author Mateusz Wenus
 */
public class UnitsWorker extends SwingWorker<Collection<UnitInfo>, Object> implements Observer {
    
    private Collection<String> filePaths;
    private Collection<String> namesFilter;
    private UnitsRetriever retriever;
    
    /**
     * Creates a new instance of UnitsWorker which will read files from <code>
     * filePaths</code>
     *
     * @param filePaths paths to files to read from
     */
    public UnitsWorker(Collection<String> filePaths) {
        this.filePaths = filePaths;
        retriever = new UnitsRetrieverImpl();
        retriever.addObserver(this);
    }
    
    /**
     * Creates a new instance of UnitsWorker which will read files from <code>
     * filePaths</code> and return only those classes/interfaces/enums which
     * have qualified names on <code>namesFilter</code>.
     *
     * @param filePaths paths to files to read from
     * @param namesFilter qualified names of classes/interfaces/enums that
     *        can be returned by this method
     */
    public UnitsWorker(Collection<String> filePaths, Collection<String> namesFilter) {
        this(filePaths);
        this.namesFilter = namesFilter;
    }
    
    /**
     * Called when underlying UnitsRetriever notifies about progress of its job.
     * Should not be called malually.
     *
     * @param o Observable which has changed
     * @param arg argument passed by Observable
     */
    public void update(Observable o, Object arg) {
        if(o.equals(retriever)) {
            firePropertyChange("progress", null, arg);
        }
    }
    
    /**
     * Gets definitions of classes/interfaces/enums from files that were given to
     * the worker at construction time. Collection of those definitions may be read
     * using get() method once this worker is done.
     *
     * @return UnitInfos representing classes/interfaces/enums defined in given files.
     */
    protected Collection<UnitInfo> doInBackground() {
        return retriever.retrieve(filePaths, namesFilter);
    }
    
}
