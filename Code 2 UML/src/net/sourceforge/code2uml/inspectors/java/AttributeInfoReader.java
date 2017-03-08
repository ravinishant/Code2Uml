/*
 * AttributeInfoReader.java
 *
 * Created on September 2, 2007, 7:39 PM
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

/**
 * Reads attribute_info structure from a .class file.
 *
 * @author Mateusz Wenus
 */
public class AttributeInfoReader {
    
    /** 
     * Creates a new instance of AttributeInfoReader. 
     */
    public AttributeInfoReader() {
    }
    
    /**
     * Reads through attribute_info structures, ignoring them. The only purpose
     * of this method is to make the next call of in.readX() return data right
     * after the last attribute_info.
     *
     * @param in DataInput to read from
     * @throws IOException if an I/O error occurs
     */
    public void ignoreAttributes(DataInput in) throws IOException {
        int attributesCount = in.readUnsignedShort();
        for(int i = 0; i < attributesCount; i++) {
            in.readUnsignedShort();
            int max = in.readInt();
            for(int j = 0; j < max; j++)
                in.readByte();
        }
    }
    
    /**
     * Reads attributes_info and returns an index into the constant pool at 
     * which a signature is stored. The <code>pool</code> parameter stores 
     * contents of .class file's constant pool in format returned by
     * ConstantPoolReader.
     *
     * @param in DataInput to read from
     * @param pool constant_pool
     * @throws IOException if an I/O error occurs
     * @return the index into the constant pool at which a signature is stored.
     */
    public int readSignatureIndex(DataInput in, Object[] pool) throws IOException {
        int signatureIndex = -1;
        int attributesCount = in.readUnsignedShort();
        for(int i = 0; i < attributesCount; i++) {
            int idx = in.readUnsignedShort();
            boolean signature = pool[idx] != null &&
                                pool[idx] instanceof String &&
                                pool[idx].equals("Signature");
            if(signature) {
                in.readInt();
                signatureIndex = in.readUnsignedShort();
            } else {
                int max = in.readInt();
                for(int j = 0; j < max; j++)
                    in.readByte();
            }
        }
        return signatureIndex;
    }
}
