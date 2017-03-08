/*
 * ClassByteBufferReader.java
 *
 * Created on August 8, 2007, 1:19 PM
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

import java.io.DataInput;
import java.io.IOException;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.unitdata.UnitInfoImpl;

/**
 * This class is responsible for reading .class file's contents.
 *
 * @author Mateusz Wenus
 */
class ClassFileReader {
    
    private static final int ACC_PUBLIC = 0x0001;
    private static final int ACC_FINAL = 0x0010;
    private static final int ACC_SUPER = 0x0020;
    private static final int ACC_INTERFACE = 0x0200;
    private static final int ACC_ABSTRACT = 0x0400;
    private static final int ACC_SYNTHETIC = 0x1000;
    private static final int ACC_ANNOTATION = 0x2000;
    private static final int ACC_ENUM = 0x4000;
    
    private FieldInfoReader fieldReader = new FieldInfoReader();
    private MethodInfoReader methodReader = new MethodInfoReader();
    
    /**
     * Creates a new instance of ClassFileReader.
     */
    public ClassFileReader() {
    }
    
    /**
     * Returns class/interface/enum defined in given .class file.
     *
     * @param in DataInput to read contents of .class file from
     * @return class/interface/enum defined in that .class file
     * @throws IOException if an I/O error occurs
     */
    public UnitInfo read(DataInput in) throws IOException {
        
        UnitInfoImpl unit = new UnitInfoImpl();
        
        // magic number identifying .class file
        if(in.readInt() != 0xCAFEBABE)
            return null;
        
        // major and minor version
        in.readInt();
        
        // constant_pool_count
        int data = in.readUnsignedShort();
        
        // read constant_pool
        Object[] pool = new ConstantPoolReader().read(in, data);
        
        // access_flags
        data = in.readUnsignedShort();
        if((data & ACC_INTERFACE) != 0)
            unit.setIsInterface(true);
        else if((data & ACC_ENUM) != 0)
            unit.setIsEnum(true);
        else {
            unit.setIsClass(true);
            if((data & ACC_ABSTRACT) != 0)
                unit.setIsAbstract(true);
        }
        if((data & ACC_PUBLIC) != 0) 
            unit.setIsPublic(true);
        
        // this class - index into constant_pool
        data = in.readUnsignedShort();
        String name = (String) pool[(Integer) pool[data]];
        unit.setName(name.replace('/', '.'));
        unit.setSimpleName(unit.getName().substring(unit.getName().lastIndexOf('.') + 1));
        
        // super class - index into constant_pool
        data = in.readUnsignedShort();
        if(data > 0) {
            name = (String) pool[(Integer) pool[data]];
            unit.addSupertype(name.replace('/', '.'));
        }
        
        // interfaces count
        data = in.readUnsignedShort();
        for(int i = 0; i < data; i++) {
            int idx = in.readUnsignedShort();
            name = (String) pool[(Integer) pool[idx]];
            unit.addSupertype(name.replace('/', '.'));
        }
        
        // fields count
        data = in.readUnsignedShort();
        for(int i = 0; i < data; i++) {
            FieldInfo field = fieldReader.read(in, pool);
            if(field != null) {
                if(field.getTypeName().equals("enum")) {
                    unit.addEnumValue(field.getName());
                } else {
                    unit.addField(field);
                }
            }
        }
        
        // methods count
        data = in.readUnsignedShort();
        for(int i = 0; i < data; i++) {
            MethodInfo method = methodReader.readMethod(in, pool);
            if(method != null) {
                unit.addMethod(method);
            }
        }
        
        /*
         * There is attributes data at the end of .class file but I don't read
         * it because I don't use it.
         */
        
        return unit;
    }

    /**
     * Returns qualified name of a class/interface/enum defined in a .class 
     * file.
     *
     * @param in DataInput to read contents of .class file from
     * @return qualified name of a class/interafce/enum defined in given file
     * @throws IOException if I/O error occurs
     */
    public String readUnitName(DataInput in) throws IOException {
        ConstantPoolReader reader = new ConstantPoolReader();
        
        if(in.readInt() != 0xCAFEBABE)
            return null;
        
        // major and minor version
        in.readInt();
        
        // constant_pool_count
        int data = in.readUnsignedShort();
        
        // read constant_pool
        Object[] pool = new ConstantPoolReader().read(in, data);
        
        // access_flags
        in.readUnsignedShort();
        
        // this class - index into constant_pool
        data = in.readUnsignedShort();
        
        /*
         * pool[data] contains an index into pool at which is qualified
         * name of the class in internal form (having '/' instead of '.')
         */
        return ((String) pool[(Integer) pool[data]]).replace('/', '.');
    }
}
