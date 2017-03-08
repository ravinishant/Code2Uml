/*
 * ConstantPoolReader.java
 *
 * Created on 29 July 2007, 12:51
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
import java.nio.ByteBuffer;

/**
 * Reads data from .class file's contant pool. See .class file format specification
 * before analyzing ConstantPoolReader's source.
 *
 * @author Mateusz Wenus
 */
public class ConstantPoolReader {
    
    private static final int CONSTANT_Class = 7;
    private static final int CONSTANT_Fieldref = 9;
    private static final int CONSTANT_Methodref = 10;
    private static final int CONSTANT_InterfaceMethodref = 11;
    private static final int CONSTANT_String = 8;
    private static final int CONSTANT_Integer = 3;
    private static final int CONSTANT_Float = 4;
    private static final int CONSTANT_Long = 5;
    private static final int CONSTANT_Double = 6;
    private static final int CONSTANT_NameAndType = 12;
    private static final int CONSTANT_Utf8 = 1;
    
    /**
     * Creates a new instance of ConstantPoolReader
     */
    public ConstantPoolReader() {
    }
    
    /**
     * Reads <code>constantPoolCount - 1</code> cp_info structs from given buffer.
     * For each of read cp_infos: <br/>
     * if it is a CONSTANT_Class_info struct its name_index (as an Integer) is
     * put into returned array at current position <br/>
     * if it is a CONSTANT_Utf8_info struct its String value is put into returned
     * array at current position <br/>
     * otherwise the cp_info is ignored and null is put into returned array. It
     * works this way because ClassFileInspector is interested only in names of
     * fields, methods, supertypes etc. and extra information included in other
     * cp_infos is not useless.
     *
     * @param in DataInput to read constant_pool's data from
     * @param constantPoolCount number of cp_infos to read + 1
     * @return array containing at each position Integer, String or null
     * @throws IOException if an I/O error occurs
     */
    public Object[] read(DataInput in, int constantPoolCount) throws IOException {
        
        // Although constantPoolCount - 1 objects will be read, they are indexed from 1
        // so array must have constantPoolCount size
        Object[] result = new Object[constantPoolCount];
        
        int tag;
        //short length;
        for(int i = 1; i < constantPoolCount; i++) {
            tag = in.readUnsignedByte();
            switch(tag) {
                case CONSTANT_Utf8:
                    //length = in.readUnsignedShort();
                    //byte[] tab = new byte[length];
                    //for(int j = 0; j < length; j++)
                    //    tab[j] = buffer.get();
                    result[i] = in.readUTF();
                    break;
                case CONSTANT_Class:
                    result[i] = in.readUnsignedShort();
                    break;
                default:
                    if(readTrash(in, tag))
                        i++;
                    break;
            }
        }
        
        return result;
    }
    
    /**
     * Helper function which reads uninteresting data from constant_pool. It 
     * reads the next entry from the constant_pool.
     *
     * @param in DataInput to read constant_pool's data from
     * @param tag tag of read cp_info
     * @return true if and only if CONSTANT_Long_info or CONSTANT_Double_info has
     *         been read which means that the next constant_pool usable entry is
     *         at current position + 2 and entry at current_position + 1 should be
     *         omitted
     * @throws IOException if an I/O error occurs
     */
    private boolean readTrash(DataInput in, int tag) throws IOException {
        switch(tag) {
            case CONSTANT_Fieldref:
            case CONSTANT_Methodref:
            case CONSTANT_InterfaceMethodref:
            case CONSTANT_Integer:
            case CONSTANT_Float:
            case CONSTANT_NameAndType:
                in.readInt();
                break;
            case CONSTANT_Class:
            case CONSTANT_String:
                in.readShort();
                break;
            case CONSTANT_Long:
            case CONSTANT_Double:
                in.readLong();
                return true;
            case CONSTANT_Utf8:
                in.readUTF();
                break;
            default:
                break;
        }
        return false;
    }
}
