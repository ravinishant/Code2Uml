/*
 * GenericNameConverter.java
 *
 * Created on August 19, 2007, 1:24 PM
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

package net.sourceforge.code2uml.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is responsible for converting qualified type names containing
 * information about generic types to other formats.
 *
 * @author Mateusz Wenus
 */
public class GenericNameConverter {
    
    /**
     * Creates a new instance of GenericNameConverter.
     */
    public GenericNameConverter() {
    }
    
    /**
     * Returns qualified names of types contained in <code>name</code>.
     * Examples: <br/>
     * java.lang.String -> [java.lang.String] <br/>
     * java.util.Collection<java.lang.String> -> [java.util.Collection, java.lang.String] <br/>
     * java.util.Set<? extends java.lang.String> -> [java.util.Set, java.lang.String]
     *
     * @param typeName qualified name of a type
     * @return qualified names of types contained in <code>name</code>.
     */
    public Collection<String> getTypeNames(String typeName) {
        Collection<String> result = new ArrayList<String>();
        int startIdx, endIdx;
        startIdx = endIdx = 0;
        
        /*
         * This loop splits generic type name into words (separated by '<', '>',
         * ' ' or ',') and returns words which are type names.
         */
        while(endIdx < typeName.length()) {
            switch(typeName.charAt(endIdx)) {
                case '<':
                case '>':
                case ' ':
                case ',':
                    // copy up to endIdx not endIdx + 1 since we ignore the last
                    // found character
                    String str = typeName.substring(startIdx, endIdx);
                    if(!str.equals("super") && !str.equals("extends") && !str.equals("?"))
                        result.add(str);
                    startIdx = endIdx + 1;
                    break;
                default:
                    break;
            }
            endIdx++;
        }
        String str = typeName.substring(startIdx, endIdx);
        if(!str.equals("super") && !str.equals("extends") && !str.equals("?"))
            result.add(str);
        return result;
    }
    
    /**
     * Returns a String that describes type <code>typeName</code> but uses
     * unqualified names of types. Examples:
     * java.lang.String -> String <br/>
     * java.util.Collection<java.lang.String> -> Collection<String> <br/>
     * java.util.Set<? extends java.lang.String> -> Set<? extends java.lang.String>
     *
     * @param typeName qualified name of a type
     * @return a String that describes type <code>typeName</code> but uses
     *         unqualified names of types
     */
    public String unqualify(String typeName) {
        StringBuilder builder = new StringBuilder();
        int startIdx, endIdx;
        startIdx = endIdx = 0;
        while(endIdx < typeName.length()) {
            switch(typeName.charAt(endIdx)) {
                case '.':
                    // if you find a '.', drop all that was read so far because
                    // it is part of package name
                    startIdx = endIdx + 1;
                    break;
                case '<':
                case '>':
                case ',':
                case ' ':
                    // any of these characters denotes end of data that
                    // should be included so append that data to result
                    // (including the terminating char)
                    builder.append(typeName.substring(startIdx, endIdx + 1));
                    startIdx = endIdx + 1;
                    break;
                default:
                    break;
            }
            endIdx++;
        }
        builder.append(typeName.substring(startIdx));
        return builder.toString();
    }
}
