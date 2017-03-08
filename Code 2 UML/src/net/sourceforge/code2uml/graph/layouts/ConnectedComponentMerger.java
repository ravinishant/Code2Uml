/*
 * ConnectedComponentMerger.java
 *
 * Created on September 11, 2007, 11:11 AM
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

package net.sourceforge.code2uml.graph.layouts;

import java.util.Collection;

/**
 * Defines methods used to merge ConnectedComponents which have been laid out
 * independently into one layout.
 * 
 * @author Mateusz Wenus
 */
interface ConnectedComponentMerger {
    
    /**
     * Lays out ConnectedComponens from <code>components</code> so that they 
     * don't overlap. Does not change distances between NodeComponents 
     * belonging to the same ConnectedComponent.
     *
     * @param components ConnectedComponents to merge
     */
    public void merge(Collection<ConnectedComponent> components);
    
}
