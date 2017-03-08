/*
 * MethodInfo.java
 *
 * Created on 25 July 2007, 13:07
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

import java.util.List;

/**
 * Defines methods used to get information about a method of a class/interface/enum.
 *
 * @author Mateusz Wenus
 */
public interface MethodInfo {
    
    /**
     * Returns access modifier of represented method.
     *
     * @return access modifier of represented method
     */
    public AccessType getAccessType();
    
    /**
     * Returns true if this represents a static method.
     *
     * @return true if this represents a static method
     */
    public boolean isStatic();
    
    /**
     * Returns true if this represents an abstract method.
     *
     * @return true if this represents an abstract method
     */
    public boolean isAbstract();
    
    /**
     * Returns qualified name of return type of represented method.
     *
     * @return qualified name of return type of represented method
     */
    public String getReturnTypeName();
    
    /**
     * Returns name of represented method.
     *
     * @return name of represented method
     */
    public String getName();
    
    /**
     * Returns list of qualified names of arguments' types of represented method.
     *
     * @return list of qualified names of arguments' types of represented method
     *         or null if it has no arguments
     */
    public List<String> getArguments();
    
}
