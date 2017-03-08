/*
 * FieldInfoImpl.java
 *
 * Created on 27 July 2007, 16:45
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

/**
 * Represent a field of a class/enum/interface.
 *
 * @author Mateusz Wenus
 */
public class FieldInfoImpl implements FieldInfo {
    
    private AccessType accessType;
    private String typeName;
    private String name;
    private boolean staticField;
    private boolean finalField;
    
    /**
     * Creates a new instance of FieldInfoImpl.
     */
    public FieldInfoImpl() {
    }
    
    /**
     * Returns access modifier of represented field.
     *
     * @return access modifier of represented field
     */
    public AccessType getAccessType() {
        return accessType;
    }
    
    /**
     * Sets access modifier of represented field.
     *
     * @param accessType access modifier of represented field
     */
    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
    
    /**
     * Returns qualified name of type of represented field.
     *
     * @return qualified name of type of represented field
     */
    public String getTypeName() {
        return typeName;
    }
    
    /**
     * Sets name of type of represnted field.
     *
     * @param typeName name of type of represented field.
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    /**
     * Returns name of represented field.
     *
     * @return name of represented field
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets name of represented field.
     *
     * @param name name of represented field.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns true if this represents a static field.
     *
     * @return true if this represents a static field
     */
    public boolean isStatic() {
        return staticField;
    }
    
    /**
     * Sets whether this represents a static field.
     *
     * @param staticField boolean value to set
     */
    public void setStatic(boolean staticField) {
        this.staticField = staticField;
    }
    
    /**
     * Returns true if this represents a final field.
     *
     * @return true if this represents a final field
     */
    public boolean isFinal() {
        return finalField;
    }
    
    /**
     * Sets whether this represents a final field.
     *
     * @param isFinal boolean value to set
     */
    public void setIsFinal(boolean isFinal) {
        this.finalField = isFinal;
    }
    
    /**
     * Returns string representation if this field in form:<br/>
     * <access type> <modifiers> <type name> <name>
     *
     * @return string representation if this field
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(accessType.toString());
        if(staticField)
            builder.append(" static ");
        else
            builder.append(" ");
        builder.append(typeName);
        builder.append(" ");
        builder.append(name);
        return builder.toString().trim();
    }
}
