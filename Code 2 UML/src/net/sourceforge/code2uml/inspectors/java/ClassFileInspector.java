/*
 * ClassFileInspector.java
 *
 * Created on 29 July 2007, 12:50
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

package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.sourceforge.code2uml.inspectors.FileInspector;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * FileInspector responsible for dealing with java .class files. See .class file
 * specification before analyzing ClassFileInspector's source.
 *
 * @author Mateusz Wenus
 */
public class ClassFileInspector implements FileInspector {
    
    /**
     * Contains pairs filepath - qualified name of class/interface/enum defined
     * in that file. This cache is common for all ClassFileInspectors and is
     * thread safe.
     */
    private static ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();
    
    /**
     * Creates a new instance of ClassFileInspector
     */
    public ClassFileInspector() {
    }
    
    /**
     * Returns definitions of all classes/interfaces/enums from specified file.
     * <br/><code>inspect(filePath, null)</code> is equivalent to 
     * <code>inspect(filePath)</code>
     *
     * @param filePath path to the file to read from
     * @return collection of objects representing classes/interfaces/enums
     *         defined in file <code>filePath</code> or null
     */
    public Collection<UnitInfo> inspect(String filePath) {
        ArrayList<UnitInfo> al = null;
        UnitInfo unit = processClassFile(filePath, UnitInfo.class);
        if(unit != null) {
            al = new ArrayList<UnitInfo>(1);
            al.add(unit);
        }
        return al;
    }
    
    /**
     * Returns from specified file definitions of those classes/interfaces/enums
     * which have qualified names belonging to <code>namesFilter</code>. If
     * <code>namesFilter</code> is null, all definitions are returned. <br/>
     * <code>inspect(filePath, null)</code> is equivalent to
     * <code>inspect(filePath)</code>
     *
     * @param filePath path to the file to read from
     * @param namesFilter collection of qualified names of classes/interfaces/enums
     *        which are allowed to be returned; if this parameter is null all
     *        definitions will be returned
     * @return collection of objects representing classes/interfaces/enums
     *         defined in file <code>filePath</code> which names are in <code>
     *         namesFiler</code> or null
     */
    public Collection<UnitInfo> inspect(String filePath, Collection<String> namesFilter) {
        
        /*
         * if you know what class is defined is filePath and that class is
         * not in namesFilter, then there's no need to read this file
         */
        if(cache.containsKey(filePath) && !namesFilter.contains(cache.get(filePath)))
            return null;
        
        /*
         * Note that cache is automatically updated.
         */
        Collection<UnitInfo> units = inspect(filePath);
        
        /*
         * units is either null (so return null) or contains one element (so
         * check if that element is in namesFilter)
         */
        if(units == null)
            return null;
        if(namesFilter.contains(units.iterator().next().getName()))
            return units;
        else
            return null;
    }
    
    /**
     * Returns qualified names of classes/interfaces/enums defined in specified
     * file.
     *
     * @param filePath file to read from
     * @return qualified names of classes/interfaces/enums defined in specified
     *         file
     */
    public Collection<String> glance(String filePath) {
        String name = cache.get(filePath);
        Collection<String> result = null;
        if(name == null) {
            name = processClassFile(filePath, String.class);
        }
        if(name != null) {
            result = new ArrayList<String>(1);
            result.add(name);
        }
        return result;
    }
    
    /**
     * Utility method which updates files-names cache if this is necessary.
     *
     * @param filePath path to a .class file
     * @param qualifiedName qualified name of a class/interface/enum defined
     *        in file <code>filePath</code>
     */
    private void updateCache(String filePath, String qualifiedName) {
        cache.putIfAbsent(filePath, qualifiedName);
    }
    
    /**
     * Processes a .class file and returns result of that processing. Actual
     * behaviour depends on <code>resultType</code> paramenter: <br/>
     * - if it is String.class this method returns qualified name of a 
     *   class/interface/enum defined in file <code>filePath</code><br/>
     * - if it is UnitInfo.class this method returns a UnitInfo which represents
     *   class/interface/enum defined in file <code>filePath</code><br/>
     * - if it has other value, the result is unspecified 
     *
     * @param filePath .class file to process
     * @param resultType either String.class or UnitInfo.class
     */
    private <T> T processClassFile(String filePath, Class<T> resultType) {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(filePath));
            ClassFileReader reader = new ClassFileReader();
            
            if(resultType.equals(String.class)) {
                String name = reader.readUnitName(in);
                updateCache(filePath, name);
                return (T) name;
            } else if (resultType.equals(UnitInfo.class)) {
                UnitInfo unit = reader.read(in);
                updateCache(filePath, unit.getName());
                return (T) unit;
            }
            return null;
        } catch(IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(in != null)
                try {
                    in.close();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
        }
    }
}
