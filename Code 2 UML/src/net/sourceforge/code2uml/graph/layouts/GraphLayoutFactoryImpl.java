/*
 * GraphLayoutFactoryImpl.java
 *
 * Created on August 4, 2007, 10:00 PM
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

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.code2uml.graph.layouts.force.ForceBasedGraphLayout;

/**
 * This class is responsible for returning requested GraphLayouts.
 *
 * @author Mateusz Wenus
 */
public class GraphLayoutFactoryImpl implements GraphLayoutFactory {
    
    private Map<String, GraphLayout> layouts = new HashMap<String, GraphLayout>();
    private GraphLayout defaultLayout = new SimpleGraphLayout();
    
    /** 
     * Creates a new instance of GraphLayoutFactoryImpl. 
     */
    public GraphLayoutFactoryImpl() {
        layouts.put("rectangular", new ForceBasedGraphLayout());
    }

    /**
     * Returns requested GraphLayout. 
     *
     * @param name name of GraphLayout
     * @return requested of GraphLayout or SimpleGraphLayout if it could not be found
     */
    public GraphLayout getLayout(String name) {
        GraphLayout layout = layouts.get(name);
        if(layout == null)
            return defaultLayout;
        else
            return layout;
    }
    
}
