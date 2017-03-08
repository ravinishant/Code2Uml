/*
 * FieldInfoReader.java
 *
 * Created on September 2, 2007, 7:06 PM
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
import java.nio.CharBuffer;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.FieldInfoImpl;

/**
 * Reads field_info structures from a .class file.
 *
 * @author Mateusz Wenus
 */
class FieldInfoReader {
    
    private static final int ACC_PUBLIC = 0x0001;
    private static final int ACC_PRIVATE = 0x0002;
    private static final int ACC_PROTECTED = 0x0004;
    private static final int ACC_STATIC = 0x0008;
    private static final int ACC_FINAL = 0x0010;
    private static final int ACC_VOLATILE = 0x0040;
    private static final int ACC_TRANSIENT = 0x0080;
    private static final int ACC_SYNTHETIC = 0x1000;
    private static final int ACC_ENUM = 0x4000;
    
    private DescriptorReader descriptorReader = new DescriptorReader();
    private SignatureReader signatureReader = new SignatureReader();
    private AttributeInfoReader attributeReader = new AttributeInfoReader();
    
    /**
     * Creates a new instance of FieldInfoReader.
     */
    public FieldInfoReader() {
    }
    
    /**
     * Reads a field_info structure from <code>in</code> and return FieldInfo
     * containing read information. The <code>pool</code> parameter stores 
     * contents of .class file's constant pool in format returned by
     * ConstantPoolReader. <br/>
     * If the field_info which this method reads: <br/>
     * - represents an enum value -> this method returns a FieldInfo whose
     *   getTypeName() method returns "enum" and getName() method returns
     *   the name of that enum value <br/>
     * - represents synthetic (not present in the source code) field -> this
     *   method returns null
     * - otherwise it return a FieldInfo which contains read information about
     *   a field
     *
     * @param in DataInput to read from
     * @param pool constant pool
     * @throws IOException if an I/O error occurs
     * @return FieldInfo containing read information or null
     */
    public FieldInfo read(DataInput in, Object[] pool) throws IOException {
        int temp = in.readUnsignedShort();
        if((temp & ACC_SYNTHETIC) != 0) {
            ignoreField(in);
            return null;
        }
        
        FieldInfoImpl field = new FieldInfoImpl();
        
        if((temp & ACC_ENUM) != 0) {
            temp = in.readUnsignedShort();
            field.setTypeName("enum");
            field.setName((String) pool[temp]);
            in.readUnsignedShort();
            attributeReader.ignoreAttributes(in);
        } else {
            if((temp & ACC_PUBLIC) != 0)
                field.setAccessType(AccessType.PUBLIC);
            else if((temp & ACC_PRIVATE) != 0)
                field.setAccessType(AccessType.PRIVATE);
            else if((temp & ACC_PROTECTED) != 0)
                field.setAccessType(AccessType.PROTECTED);
            else
                field.setAccessType(AccessType.PACKAGE);
            field.setStatic((temp & ACC_STATIC) != 0);
            field.setIsFinal((temp & ACC_FINAL) != 0);
            
            // name index
            temp = in.readUnsignedShort();
            field.setName((String) pool[temp]);
            
            // descriptor index
            temp = in.readUnsignedShort();
            field.setTypeName(descriptorReader.readFieldDescriptor(CharBuffer.wrap((CharSequence) pool[temp])));
            
            int signatureIdx = attributeReader.readSignatureIndex(in, pool);
            if(signatureIdx != -1) {
                CharBuffer charBuffer = CharBuffer.wrap((CharSequence) pool[signatureIdx]);
                field.setTypeName(signatureReader.readFieldTypeSignature(charBuffer));
            }
        }
        return field;
    }
    
    /**
     * Helper function which reads field_info data, ignoring it. Important note:
     * this method assumes that access_flags of that field_info has already been
     * read.
     *
     * @param in DataInput to read from
     * @throws IOException if an I/O error occurs
     */
    private void ignoreField(DataInput in) throws IOException {
        in.readInt();
        attributeReader.ignoreAttributes(in);
    }
}
