/*
 * MethodInfoImpl.java
 *
 * Created on 27 July 2007, 16:49
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

import java.util.Iterator;
import java.util.List;

/**
 * Represent a method of a class/interface/enum.
 *
 * @author Mateusz Wenus
 */
public class MethodInfoImpl implements MethodInfo {
    
    private AccessType accessType;
    private boolean staticMethod;
    private boolean abstractMethod;
    private String returnTypeName;
    private String name;
    private List<String> arguments;
    
    /**
     * Creates a new instance of MethodInfoImpl.
     */
    public MethodInfoImpl() {
    }
    
    /**
     * Returns access modifier of represented method. 
     *
     * @return access modifier of represented method
     */
    public AccessType getAccessType() {
        return accessType;
    }
    
    /**
     * Sets access modifier of represented method.
     *
     * @param accessType access modifier of represented method
     */
    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
    
    /**
     * Returns true if this represent a static method. 
     *
     * @return true if this represent a static method
     */
    public boolean isStatic() {
        return staticMethod;
    }
    
    /**
     * Sets whether this represents a static method. 
     *
     * @param staticMethod boolean value to set
     */
    public void setStatic(boolean staticMethod) {
        this.staticMethod = staticMethod;
    }
    
    /**
     * Returns true if this represent an abstract method. 
     *
     * @return true if this represent an abstract method
     */
    public boolean isAbstract() {
        return abstractMethod;
    }
    
    /**
     * Sets whether this represents an abstract method.
     *
     * @param abstractMethod boolean value to set
     */
    public void setAbstract(boolean abstractMethod) {
        this.abstractMethod = abstractMethod;
    }
    
    /**
     * Returns qualified name of return type of represented method.
     *
     * @return qualified name of return type of represented method
     */
    public String getReturnTypeName() {
        return returnTypeName;
    }
    
    /**
     * Sets name of return type of represented method. 
     *
     * @param returnTypeName qualified name of return type of represented method
     */
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }
    
    /**
     * Returns name of represented method. 
     *
     * @return name of represented method
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets name of represented method.
     *
     * @param name name of represented method
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns list of qualified names of represented method's arguments. 
     *
     * @return list of qualified names of represented method's arguments.
     */
    public List<String> getArguments() {
        return arguments;
    }
    
    /**
     * Sets list of represented method's arguments. 
     *
     * @param arguments list of qualified names of represented method's arguments
     */
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
    
    /**
     * Returns string representation of represented method in form: <br/>
     * <access type> <modifiers> <return type> <name>(<arguments' types>)
     *
     * @return string representation of represented method
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(accessType.toString());
        if(staticMethod)
            builder.append(" static ");
        else
            builder.append(" ");
        builder.append(returnTypeName);
        builder.append(" ");
        builder.append(name);
        builder.append("(");
        if(arguments != null) {
            Iterator<String> it = arguments.iterator();
            if(it.hasNext()) {
                builder.append(it.next());
            }
            while(it.hasNext()) {
                builder.append(", ");
                builder.append(it.next());
            }
        }
        builder.append(")");
        return builder.toString().trim();
    }
}
