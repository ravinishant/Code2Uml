/*
 * NodeConstructor.java
 *
 * Created on 3 August 2007, 07:47
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
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * Defines methods used to construct a node from information about a 
 * class/interface/enum that this node will represent.
 *
 * @author Mateusz Wenus
 */
public interface NodeConstructor {
    
    /**
     * Returns a node constructed from information about a class/interface/enum.
     * This node all necessary information apart from edges that start in it.
     *
     * @param unit class/interface/enum which this node will represent
     * @param g graphics object of Container to ehich this node will be added
     * @param hints hints about how node should be created
     * @return a node constructed from inforamtion in <code>unit</code>
     */
    public NodeComponent construct(UnitInfo unit, Graphics g, ConstructionHints hints);
}
