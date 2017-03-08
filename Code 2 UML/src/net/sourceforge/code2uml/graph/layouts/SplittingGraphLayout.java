/*
 * SplittingGraphLayout.java
 *
 * Created on September 11, 2007, 6:04 PM
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
import java.util.Observable;
import net.sourceforge.code2uml.graph.Graph;

/**
 * Provides an abstract graph layout algorithm which splits the graph into 
 * weakly connected components, lays out each of them independently and then
 * merges them to create layout of the whole graph. Subclasses must override
 * <code>layout(Collection<ConnectedComponent> components)</code> method in
 * which each of given ConnectedComponents should be laid out. Splitting and 
 * merging is performed by this class and subclasses don't need to care about 
 * it. <br/><br/>
 *
 * Note: This class extends java.util.Observable so that its subclasses don't
 * need to implement <code>addObserver</code> method defined in <code>
 * graphLayout</code> interface and may easily notifiy their Observers about
 * progress of their work.
 * 
 * @author Mateusz Wenus
 */
public abstract class SplittingGraphLayout extends Observable implements GraphLayout {
    
    private static final int padding = 30;
    private ConnectedComponentFactory factory = new ConnectedComponentFactory(padding);
    private ConnectedComponentMerger merger = new SimpleConnectedComponentMerger();
    
    /** 
     * Creates a new instance of SplittingGraphLayout. 
     */
    public SplittingGraphLayout() {
    }

    /**
     * Chooses coordinates for nodes of graph. Should notify its Observers 
     * about progress of its work using ProgressData objects.
     * 
     * @param graph graph to layout
     */
    public void layout(Graph graph) {
        Collection<ConnectedComponent> components = factory.createFromGraph(graph);
        layout(components);
        merger.merge(components);
    }
    
    /**
     * Lays out ConnectedComponents from <code>components</code>. Each of 
     * ConnectedComponents may be laid out independently - this means that 
     * NodeComponents belonging to different components may overlap or even
     * have the same coordinates - it will be fixed automatically by 
     * SplittingGraphLayout.
     *
     * @param components ConnectedComponent which should be laid out
     */
    protected abstract void layout(Collection<ConnectedComponent> components);
}
