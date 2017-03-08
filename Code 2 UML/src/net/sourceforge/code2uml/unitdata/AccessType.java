/*
 * AccessType.java
 *
 * Created on 25 July 2007, 12:59
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

import java.lang.reflect.Modifier;

/**
 * Describes type of access to a field, method or a class (public, package, protected, private).
 *
 * @author Mateusz Wenus
 */
public enum AccessType {
    
    /**
     * public access
     */
    PUBLIC,
    
    /**
     * package access
     */
    PACKAGE,
    
    /**
     * protected access
     */
    PROTECTED,
    
    /**
     * private access
     */
    PRIVATE;
    
    /**
     * Returns char representaion of this access type.
     *
     * @return char representaion of this access type
     */
    public char toChar() {
        switch(this) {
            case PRIVATE:
                return '-';
            case PROTECTED:
                return '#';
            case PACKAGE:
                return '~';
            case PUBLIC:
                return '+';
        }
        return '?';
    }
    
    /**
     * Returns string representation of this access type.
     *
     * @return string representation of this access type
     */
    @Override
    public String toString() {
        switch(this) {
            case PRIVATE:
                return "private";
            case PROTECTED:
                return "protected";
            case PACKAGE:
                return "";
            case PUBLIC:
                return "public";
        }
        return "?";
    }
}
