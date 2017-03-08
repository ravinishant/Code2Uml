/*
 * UnitInfoImpl.java
 *
 * Created on 27 July 2007, 16:54
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

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a class, an interface or an enum.
 *
 * @author Mateusz Wenus
 */
public class UnitInfoImpl implements UnitInfo {
    
    private String simpleName;
    private String name;
    private Collection<MethodInfo> methods = new HashSet<MethodInfo>();
    private Collection<FieldInfo> fields = new HashSet<FieldInfo>();
    private Collection<String> enumValues = new HashSet<String>();
    private Collection<String> superTypes = new HashSet<String>();
    private boolean isPublic;
    private boolean isClass;
    private boolean isInterface;
    private boolean isEnum;
    private boolean isAbstract;
    private boolean isPartial;
    
    /**
     * Creates a new instance of UnitInfoImpl.
     */
    public UnitInfoImpl() {
    }
    
    /**
     * Returns unqualified name of represented class/interface/enum.
     *
     * @return unqualified name of represented class/interface/enum
     */
    public String getSimpleName() {
        return simpleName;
    }
    
    /**
     * Sets unqualified name of represented class/interface/enum.
     *
     * @param simpleName unqualified name of represented class/interface/enum
     */
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    
    /**
     * Returns qualified name of represented class/interface/enum.
     *
     * @return qualified name of represented class/interface/enum
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets qualified name of represented class/interface/enum.
     *
     * @param name qualified name of represented class/interface/enum
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns methods defined in represented class/interface/enum.
     *
     * @return methods defined in represented class/interface/enum
     */
    public Collection<MethodInfo> getMethods() {
        return methods;
    }
    
    /**
     * Adds a new method to represented class/interface/enum 
     *
     * @param method method to add
     */
    public void addMethod(MethodInfo method) {
        methods.add(method);
    }
    
    /**
     * Returns fields defined in represented class/interface/enum.
     *
     * @return fields defined in represented class/interface/enum
     */
    public Collection<FieldInfo> getFields() {
        return fields;
    }
    
    /**
     * Adds a new field to represented class/interface/enum 
     *
     * @param field field to add
     */
    public void addField(FieldInfo field) {
        fields.add(field);
    }
    
    /**
     * Returns enum values of represented enum.
     *
     * @return enum values of represented enum or an empty collection if 
     *         this is not an enum
     */
    public Collection<String> getEnumValues() {
        return enumValues;
    }
    
    /**
     * Adds a new enum value to represented enum. 
     *
     * @param enumValue enum value to add
     */
    public void addEnumValue(String enumValue) {
        enumValues.add(enumValue);
    }
    
    /**
     * Returns qualified names of all superclasses and interfaces which 
     * represented class/interface/enum extends (or implements).
     *
     * @return qualified names of all superclasses and interfaces which 
     *         represented class/interface/enum extends (or implements), 
     *         an empty collection if there are none
     */
    public Collection<String> getSupertypes() {
        return superTypes;
    }
    
    /**
     * Adds a new supertype to supertypes of represented class/interface/enum.
     *
     * @param supertypeName qualified name of supertype to add
     */
    public void addSupertype(String supertypeName) {
        superTypes.add(supertypeName);
    }
    
    /**
     * Returns true if represented class/interface/enum is visible outside of
     * the package in which it is defined.
     *
     * @return true if represented class/interface/enum is visible outside of
     *         the package in which it is define
     */
    public boolean isPublic() {
        return isPublic;
    }
    
    /**
     * Sets whether represented class/interface/enum is visible outside of the
     * package in which it is defined.
     *
     * @param isPublic boolean value to set
     */
    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    /**
     * Returns true if this represents a class.
     *
     * @return true if this represents a class
     */
    public boolean isClass() {
        return isClass;
    }
    
    /**
     * Sets whether this represent a class. 
     *
     * @param isClass boolean value to set
     */
    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
    }
    
    /**
     * Returns true if this represents an interface.
     *
     * @return true if this represents an interface
     */
    public boolean isInterface() {
        return isInterface;
    }
    
    /**
     * Sets whether this represents an interface. 
     *
     * @param isInterface boolean value to set
     */
    public void setIsInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }
    
    /**
     * Returns true if this represents an enum.
     *
     * @return true if this represents an enum
     */
    public boolean isEnum() {
        return isEnum;
    }
    
    /**
     * Sets whether this represent an enum.
     *
     * @param isEnum boolean value to set
     */
    public void setIsEnum(boolean isEnum) {
        this.isEnum = isEnum;
    }
    
    /**
     * Returns true if this represents an abstract class.
     *
     * @return true if this represents an abstract class
     */
    public boolean isAbstract() {
        return isAbstract;
    }
    
    /**
     * Sets whether this represent an abstract class.
     *
     * @param isAbstract boolean value to set
     */
    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
    
    /**
     * Returns true if this represents a partial definition of a class (for C#)
     *
     * @return true if this represents a partial definition of a class
     */
    public boolean isPartial() {
        return isPartial;
    }
    
    /**
     * Sets whether this represent a partial definition of a class.
     *
     * @param isPartial boolean value to set
     */
    public void setIsPartial(boolean isPartial) {
        this.isPartial = isPartial;
    }
    
    /**
     * Merges two partial class definitions into one. This object's fields, methods
     * etc. sets become union of its values and corresponding arg's values; arg 
     * remains unchanged. Both this object and arg must be classes and must be partial.
     *
     * @param  arg partial class to be merged
     * @throws UnsupportedOperationException if this is not a partial definition of a class
     * @throws IllegalArgumentException if arg is not a partial definition of a class
     */
    public void merge(UnitInfo arg) throws IllegalArgumentException, UnsupportedOperationException {
        if(!this.isPartial())
            throw new UnsupportedOperationException();
        if(!arg.isPartial() || !this.name.equals(arg.getName()))
            throw new IllegalArgumentException();
        
        methods.addAll(arg.getMethods());
        fields.addAll(arg.getFields());
        enumValues.addAll(arg.getEnumValues());
    }
}
