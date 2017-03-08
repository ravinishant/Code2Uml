/*
 * UnitInfo.java
 *
 * Created on 25 July 2007, 12:36
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

/**
 * Defines methods used to get information about a class/interface/enum.
 *
 * @author Mateusz Wenus
 */
public interface UnitInfo {
    
    /**
     * Returns unqualified name of represented class/interface/enum.
     *
     * @return unqualified name of represented class/interface/enum
     */
    public String getSimpleName();
    
    /**
     * Returns qualified name of represented class/interface/enum.
     *
     * @return qualified name of represented class/interface/enum
     */
    public String getName();
    
    /**
     * Returns methods defined in represented class/interface/enum.
     *
     * @return methods defined in represented class/interface/enum
     */
    public Collection<MethodInfo> getMethods();
    
    /**
     * Returns fields defined in represented class/interface/enum.
     *
     * @return fields defined in represented class/interface/enum
     */
    public Collection<FieldInfo> getFields();
    
    /**
     * Returns enum values of represented enum.
     *
     * @return enum values of represented enum or an empty collection if 
     *         this is not an enum
     */
    public Collection<String> getEnumValues();
    
    /**
     * Returns qualified names of all superclasses and interfaces which 
     * represented class/interface/enum extends (or implements).
     *
     * @return qualified names of all superclasses and interfaces which 
     *         represented class/interface/enum extends (or implements), 
     *         an empty collection if there are none
     */
    public Collection<String> getSupertypes();
    
    /**
     * Returns true if represented class/interface/enum is visible outside of
     * the package in which it is defined.
     *
     * @return true if represented class/interface/enum is visible outside of
     *         the package in which it is defined
     */
    public boolean isPublic();
    
    /**
     * Returns true if this represents a class.
     *
     * @return true if this represents a class
     */
    public boolean isClass();
    
    /**
     * Returns true if this represents an interface.
     *
     * @return true if this represents an interface
     */
    public boolean isInterface();
    
    /**
     * Returns true if this represents an enum.
     *
     * @return true if this represents an enum
     */
    public boolean isEnum();
    
    /**
     * Returns true if this represents an abstract class.
     *
     * @return true if this represents an abstract class
     */
    public boolean isAbstract();
    
    /**
     * Returns true if this represents a partial definition of a class (for C#)
     *
     * @return true if this represents a partial definition of a class
     */
    public boolean isPartial();
    
    /**
     * Merges two partial class definitions into one. This object's fields, methods
     * etc. sets become union of its values and corresponding arg's values; arg 
     * remains unchanged. Both this object and arg must be classes and must be partial.
     *
     * @param  arg partial class to be merged
     * @throws UnsupportedOperationException if this is not a partial definition of a class
     * @throws IllegalArgumentException if <code>arg</code> is not a partial 
     *         definition of a class
     */
    public void merge(UnitInfo arg) throws IllegalArgumentException, UnsupportedOperationException;
}
