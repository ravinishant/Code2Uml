/*
 * GraphConstructorImpl.java
 *
 * Created on August 3, 2007, 11:00 PM
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
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import net.sourceforge.code2uml.graph.layouts.GraphLayout;
import net.sourceforge.code2uml.graph.layouts.GraphLayoutFactory;
import net.sourceforge.code2uml.graph.layouts.GraphLayoutFactoryImpl;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.util.GenericNameConverter;
import net.sourceforge.code2uml.util.ProgressData;

/**
 * This class is responsible for creating a graph of classes/interfaces/enums
 * and relationships between them from information about those
 * classes/interfaces/enums.
 *
 * @author Mateusz Wenus
 */
public class GraphConstructorImpl extends Observable implements GraphConstructor {
    
    private GraphLayoutFactory factory = new GraphLayoutFactoryImpl();
    
    /**
     * Creates a new instance of GraphConstructorImpl.
     */
    public GraphConstructorImpl() {
    }
    
    /**
     * Constructs a graph of classes/interfaces/enums and their relationships from
     * information about them. Notifies its Observers whenever: <br/>
     * it processes a UnitInfo and creates a graph node from it <br/>
     * it chooses coordinates for a node <br/>
     * Observers should assume that notifications number 1 to units.size() are
     * about node creation, the rest about their layout.
     *
     * @param units information about classes/interfaces/emums to include
     * @param g graphics of container on which graph will be drawn
     * @param hints hints about how graph should be created
     * @return fully constructed graph; its nodes and edges can be added to
     *         a Container and will be correctly displayed
     */
    public Graph construct(final Collection<UnitInfo> units, Graphics g, ConstructionHints hints) {
        Graph graph = new GraphImpl();
        NodeConstructor constructor = new NodeConstructorImpl();
        GenericNameConverter converter = new GenericNameConverter();
        Map<String, NodeComponent> nodes = new HashMap<String, NodeComponent>();
        Map<String, UnitInfo> unitsMap = new HashMap<String, UnitInfo>();
        
        boolean showNonpublic = hints.isNonpublicUnitsVisible();
        int unitCount = 0;
        for(UnitInfo unit : units) {
            if(showNonpublic || unit.isPublic()) {
                unitsMap.put(unit.getName(), unit);
                nodes.put(unit.getName(), constructor.construct(unit, g, hints));
                unitCount++;
            }
            setChanged();
            notifyObservers(new ProgressData(50.0 * unitCount / units.size()));
            clearChanged();
        }
        
        for(UnitInfo unit : units) {
            if(showNonpublic || unit.isPublic()) {
                for(String name : unit.getSupertypes()) {
                    if(nodes.containsKey(name)) {
                        UnitInfo target = unitsMap.get(name);
                        if(target.isClass() && hints.isGeneralizationDrawn() ||
                                target.isInterface() && hints.isRealizationDrawn()) {
                            EdgeComponent edge = new EdgeComponent();
                            edge.setFrom(nodes.get(unit.getName()));
                            edge.setTo(nodes.get(name));
                            if(target.isInterface())
                                edge.setType(EdgeType.REALIZATION);
                            else
                                edge.setType(EdgeType.GENERALIZATION);
                            nodes.get(unit.getName()).addEdge(edge);
                        }
                    }
                }
                
                if(hints.isHasADrawn()) {
                    for(FieldInfo field : unit.getFields()) {
                        if(field.isStatic())
                            continue;
                        
                        for(String type : converter.getTypeNames(field.getTypeName())) {
                            if(nodes.containsKey(type)) {
                                EdgeComponent edge = new EdgeComponent();
                                edge.setFrom(nodes.get(unit.getName()));
                                edge.setTo(nodes.get(type));
                                edge.setType(hints.getHasAType());
                                nodes.get(unit.getName()).addEdge(edge);
                            }
                        }
                    }
                }
            }
        }
        
        graph.getNodes().addAll(nodes.values());
        
        GraphLayout layout = factory.getLayout(hints.getLayoutName());
        layout.addObserver(new Observer() {
            public void update(Observable observable, Object arg) {
                setChanged();
                notifyObservers(new ProgressData(((ProgressData)arg).getProgress() / 2.0 + 50.0));
                clearChanged();
            }
        });
        layout.layout(graph);
        
        return graph;
    }
    
}
