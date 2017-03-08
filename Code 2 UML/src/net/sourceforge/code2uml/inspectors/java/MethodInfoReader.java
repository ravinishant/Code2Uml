/*
 * MethodInfoReader.java
 *
 * Created on September 2, 2007, 6:57 PM
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
import java.util.List;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.MethodInfoImpl;

/**
 * Reads method_info structures from a .class file.
 *
 * @author Mateusz Wenus
 */
class MethodInfoReader {
    
    private static final int ACC_PUBLIC = 0x0001;
    private static final int ACC_PRIVATE = 0x0002;
    private static final int ACC_PROTECTED = 0x0004;
    private static final int ACC_STATIC = 0x0008;
    private static final int ACC_FINAL = 0x0010;
    private static final int ACC_SYNCHRONIZED = 0x0020;
    private static final int ACC_BRIDGE = 0x0040;
    private static final int ACC_VARARGS = 0x0080;
    private static final int ACC_NATIVE = 0x0100;
    private static final int ACC_ABSTRACT = 0x0400;
    private static final int ACC_STRICT = 0x0800;
    private static final int ACC_SYNTHETIC = 0x1000;
    
    private DescriptorReader descriptorReader = new DescriptorReader();
    private SignatureReader signatureReader = new SignatureReader();
    private AttributeInfoReader attributeReader = new AttributeInfoReader();
    
    /**
     * Creates a new instance of MethodInfoReader.
     */
    public MethodInfoReader() {
    }
    
    /**
     * Reads a method_info structure from <code>in</code> and return MethodInfo
     * containing read information. The <code>pool</code> parameter stores 
     * contents of .class file's constant pool in format returned by
     * ConstantPoolReader. 
     * 
     * @param in DataInput to read from
     * @param pool constant pool 
     * @throws IOException if an I/O error occurs
     * @return MethodInfo containing read information or null if this 
     *         method_info defines a synthetic (not present in the source code)
     *         method
     */
    public MethodInfo readMethod(DataInput in, Object[] pool) throws IOException {
        int temp = in.readUnsignedShort();
        
        if((temp & ACC_SYNTHETIC) != 0) {
            ignoreMethod(in);
            return null;
        }
        
        MethodInfoImpl method = new MethodInfoImpl();
        if((temp & ACC_PUBLIC) != 0)
            method.setAccessType(AccessType.PUBLIC);
        else if((temp & ACC_PRIVATE) != 0)
            method.setAccessType(AccessType.PRIVATE);
        else if((temp & ACC_PROTECTED) != 0)
            method.setAccessType(AccessType.PROTECTED);
        else
            method.setAccessType(AccessType.PACKAGE);
        method.setStatic((temp & ACC_STATIC) != 0);
        method.setAbstract(((temp & ACC_ABSTRACT) != 0));
        
        // name_index
        temp = in.readUnsignedShort();
        method.setName((String) pool[temp]);
        
        //descriptor_index
        temp = in.readUnsignedShort();
        String methodDescriptor = (String) pool[temp];
        List<String> methodInfo = descriptorReader.readMethodDescriptor(CharBuffer.wrap(methodDescriptor));
        
        method.setReturnTypeName(methodInfo.remove(0));
        method.setArguments(methodInfo);
        
        int signatureIdx = attributeReader.readSignatureIndex(in, pool);
        if(signatureIdx != -1) {
            CharBuffer charBuffer = CharBuffer.wrap((CharSequence) pool[signatureIdx]);
                List<String> list = signatureReader.readMethodTypeSignature(charBuffer);
                method.setReturnTypeName(list.remove(0));
                method.setArguments(list);
        }
        
        /*
         * methods which contain '<' are <init>() and <clinit>()
         */
        return method.getName().contains("<")? null : method;
    }
    
    /**
     * Reads method_info's data, ignoring it. Important note: this method 
     * assumes that acces_flags of that method_info has already been read.
     *
     * @param in DataInput to read from
     * @throws IOException if an I/O error occurs
     */
    private void ignoreMethod(DataInput in) throws IOException {
        in.readInt();
        attributeReader.ignoreAttributes(in);
    }
}
