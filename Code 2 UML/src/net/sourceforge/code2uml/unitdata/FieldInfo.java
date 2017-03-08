/*
 * FieldInfo.java
 *
 * Created on 25 July 2007, 12:58
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
 * Defines methods used to get information about a field of a 
 * class/enum/interface.
 *
 * @author Mateusz Wenus
 */
public interface FieldInfo {
    
    /**
     * Returns access modifier of represented field.
     *
     * @return access modifier of represented field
     */
    public AccessType getAccessType();
    
    /**
     * Returns qualified name of type of represented field.
     *
     * @return qualified name of type of represented field
     */
    public String getTypeName();
    
    /**
     * Returns name of represented field.
     *
     * @return name of represented field
     */
    public String getName();
    
    /**
     * Returns true if this represents a static field.
     *
     * @return true if this represents a static field
     */
    public boolean isStatic();
    
    /**
     * Returns true if this represents a final field.
     *
     * @return true if this represents a final field
     */
    public boolean isFinal();
}
