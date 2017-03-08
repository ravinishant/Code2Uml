/*
 * GraphWorker.java
 *
 * Created on August 4, 2007, 10:31 PM
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

package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * Represents a background thread which constructs a graph of classes/interfaces/emums
 * and relationships between them that can be drawn as UML class diagram. It uses
 * GraphConstructor for creating that graph and registers as its Observer so it is
 * notified after a node is created and after it is spaced. And whenever any of these
 * happens, it fires PropertyChangeEvent, setting property with name "progress" and
 * old value null and new value being a ProgressData instance. <br/><br/>
 *
 * Note: SwingWorker is designed to be executed only once. This means that also
 * GraphWorker should be executed only once, further executions will not result in
 * invoking doInBackground().
 *
 * @author Mateusz Wenus
 */
public class GraphWorker extends SwingWorker<Graph, Object> implements Observer {
    
    private GraphConstructor constructor = new GraphConstructorImpl();
    //private int notifyCount = 0;
    private Collection<UnitInfo> units;
    private Graphics g;
    private ConstructionHints hints;
    
    /** 
     * Creates a new instance of GraphWorker.
     *
     * @param units definitions of classes/interfaces/enums from which graph
     *        should be created
     * @param g graphics object of Container on which graph will be drawn
     * @param hints hints about how graph should be created
     */
    public GraphWorker(Collection<UnitInfo> units, Graphics g, ConstructionHints hints) {
        this.units = units;
        this.g = g;
        this.hints = hints;
    }

    /**
     * Creates a graph f classes/interfaces/emums and relationships between them
     * using information it has been given at constructon time.
     *
     * @throws java.lang.Exception as required by SwingWorker
     * @return constructed graph
     */
    protected Graph doInBackground() throws Exception {
        constructor.addObserver(this);
        return constructor.construct(units, g, hints);
    }

    /**
     * Called when underlying GraphConstructor creates a node or chooses 
     * coordinates for it. Should not be called manually.
     *
     * @param observable Observable which has changed (GraphConstructor)
     * @param object argument passed by Observable (ignored)
     */
    public void update(Observable observable, Object object) {
        if(observable == constructor) {
            //notifyCount++;
            //firePropertyChange("count", notifyCount, notifyCount - 1);
            firePropertyChange("progress", null, object);
        }
            
    }
    
}
