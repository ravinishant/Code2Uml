/*
 * GraphConstructor.java
 *
 * Created on 3 August 2007, 07:42
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
import java.util.Observer;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * Defines methods used to create a graph of classes/interfaces/enums and 
 * relationships between them from information about those classes/interfaces/enums. 
 * Subclasses must also implement addObserver() method and should notify their 
 * Observers about progress of graph construction.
 *
 * @author Mateusz Wenus
 */
public interface GraphConstructor {
    
    /**
     * Constructs a graph of classes/interfaces/enums and their relationships from
     * information about them. Notifies its Observers whenever: <br/>
     * it processes a UnitInfo and creates a graph node from it <br/>
     * it chooses coordinates for a node <br/>
     * Observers should assume that notifications number 1 to units.size() are 
     * about node creation, the rest about their spacing.
     *
     * @param units information about classes/interfaces/emums to include 
     * @param g graphics of container on which graph will be drawn
     * @param hints hints about how graph should be created
     * @return fully constructed graph; its nodes and edges can be added to
     *         a Container and will be correctly displayed
     */
    public Graph construct(Collection<UnitInfo> units, Graphics g, ConstructionHints hints);
    
    /**
     * Adds an Observer to this object.
     *
     * @param o Observer to add
     */
    public void addObserver(Observer o);
}
