/*
 * NodeConstructorImpl.java
 *
 * Created on 3 August 2007, 07:49
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.util.GenericNameConverter;

/**
 * Constructs nodes from information about classes/interfaces/enums which
 * those nodes will represent.
 *
 * @author Mateusz Wenus
 */
class NodeConstructorImpl implements NodeConstructor {
    
    private NodeComponentFactory factory = new NodeComponentFactoryImpl();
    private GenericNameConverter converter = new GenericNameConverter();
    private static final char SPACE = ' ';
    private static final char DOT = '.';
        
    /**
     * Creates a new instance of NodeConstructorImpl.
     */
    public NodeConstructorImpl() {
    }
    
    /**
     * Returns a node constructed from information about a class/interface/enum.
     * This node all necessary information apart from edges that start in it.
     *
     * @param unit class/interface/enum which this node will represent
     * @param g graphics object of Container to ehich this node will be added
     * @param hints hints about how node should be created
     * @return a node constructed from inforamtion in <code>unit</code>
     */
    public NodeComponent construct(UnitInfo unit, Graphics g, ConstructionHints hints) {
        
        Set<AccessType> accessSet = new HashSet<AccessType>();
        if(hints.isPrivateVisible())
            accessSet.add(AccessType.PRIVATE);
        if(hints.isProtectedVisible())
            accessSet.add(AccessType.PROTECTED);
        if(hints.isPackageVisible())
            accessSet.add(AccessType.PACKAGE);
        if(hints.isPublicVisible())
            accessSet.add(AccessType.PUBLIC);
        
        NodeComponent node = factory.create(g, hints);
        node.setUnitName(unit.getName());
        
        if(unit.isInterface())
            node.addToName("<<interface>>");
        else if(unit.isEnum())
            node.addToName("<<enum>>");
        
        node.addToName(unit.getSimpleName());
        
        if(hints.isFieldsVisible()) {
            SortedSet<String> fields = new TreeSet<String>();
            for(FieldInfo field : unit.getFields()) {
                if(accessSet.contains(field.getAccessType()) &&
                        (!field.isStatic() || hints.isStaticVisible()) &&
                        (!field.isFinal() || hints.isFinalVisible())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(field.getAccessType().toChar());
                    builder.append(SPACE);
                    if(field.isStatic())
                        builder.append("static ");
                    if(field.isFinal())
                        builder.append("final ");
                    builder.append(converter.unqualify(field.getTypeName()));
                    builder.append(SPACE);
                    builder.append(field.getName());
                    fields.add(builder.toString());
                }
            }
            for(String field : fields)
                node.addToField(field);
        }
        
        if(unit.isEnum() && hints.isEnumsVisible()) {
            SortedSet<String> enums = new TreeSet<String>();
            enums.addAll(unit.getEnumValues());
            for(String enumValue : enums)
                node.addToEnum(enumValue);
        }
        
        if(hints.isMethodsVisible()) {
            SortedSet<String> methods = new TreeSet<String>();
            for(MethodInfo method : unit.getMethods()) {
                if(accessSet.contains(method.getAccessType()) &&
                        (!method.isStatic() || hints.isStaticVisible())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(method.getAccessType().toChar());
                    builder.append(SPACE);
                    if(method.isStatic())
                        builder.append("static ");
                    builder.append(converter.unqualify(method.getReturnTypeName()));
                    builder.append(SPACE);
                    builder.append(method.getName());
                    builder.append("(");
                    if(hints.isArgumentsVisible() && method.getArguments() != null) {
                        Iterator<String> it = method.getArguments().iterator();
                        if(it.hasNext()) {
                            String str = it.next();
                            builder.append(converter.unqualify(str));
                        }
                        while(it.hasNext()) {
                            String str = it.next();
                            builder.append(", ");
                            builder.append(converter.unqualify(str));
                        }
                    }
                    builder.append(")");
                    methods.add(builder.toString());
                }
            }
            for(String method : methods)
                node.addToMethod(method);
        }
        
        return node;
    }
    
}
