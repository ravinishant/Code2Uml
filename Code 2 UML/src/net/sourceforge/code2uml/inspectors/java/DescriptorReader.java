/*
 * DescriptorReader.java
 *
 * Created on August 16, 2007, 8:47 AM
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

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads fields' and methods' descriptors (defined in class file specification)
 * and returns their String representations.
 *
 * All methods of this class take a CharBuffer as an argument and they assume
 * that this CharBuffer is valid (is not null, contains enough characters to
 * read and contains information in a format which method expects to). If that
 * condition is not met, behaviour of a method is unspecified (it may return
 * wrong value, throw an exception, leave CharBuffer in indefinite state).
 * <br/><br/>
 * After successful execution of any method, CharBuffer's current position is 
 * set to character following data which that method was expected to read. This
 * means that methods of this class may be easily "chained", for example: <br/>
 * <code> while(condition) readParameterDescriptor(buffer); </code>
 *
 * @author Mateusz Wenus
 */
public class DescriptorReader {
    
    /** 
     * Creates a new instance of DescriptorReader. 
     */
    public DescriptorReader() {
    }
    
    /**
     * Reads a FieldDescriptor from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * A FieldDescriptor describes a field of a class, an enum or an interface. <br/>
     * In a .class file, a FieldDescriptor is represented by a <i>FieldType</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read FieldDescriptor
     */
    public String readFieldDescriptor(CharBuffer buffer) {
        return readFieldType(buffer);
    }
    
    /**
     * Reads a ComponentType from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In a .class file, a ComponentType is represented by a <i>FieldType</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ComponentType
     */
    protected String readComponentType(CharBuffer buffer) {
        return readFieldType(buffer);
    }
    
    /**
     * Reads a BaseType from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * BaseTypes are: byte, char, double, float, int, long, String and boolean.
     * <br/> In a .class file, a BaseType is represented as one of characters:
     * B, C, D, F, I, J, S, Z.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read BaseType
     */
    protected String readBaseType(CharBuffer buffer) {
        char c = buffer.get();
        switch(c) {
            case 'B':
                return "byte";
            case 'C':
                return "char";
            case 'D':
                return "double";
            case 'F':
                return "float";
            case 'I':
                return "int";
            case 'J':
                return "long";
            case 'S':
                return "String";
            case 'Z':
                return "boolean";
            default:
                return "?";
        }
    }
    
    /**
     * Reads an ArrayType from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * An ArrayType is an array of something. <br/>
     * In a .class file, an ArrayType is represented as: [<i>ComponentType</i>
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ArrayType
     */
    protected String readArrayType(CharBuffer buffer) {
        // the first character must be '['
        if(buffer.get() != '[')
            return "?";
        return readComponentType(buffer) + "[]";
    }
    
    /**
     * Reads an ObjectType from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * An ObjectType is an instance of a class. <br/>
     * In a .class file, an ObjectType is represented as: L<b>Classname</b>; <br/>
     * where Classname is a fully qualified class or interface name encoded in
     * internal form (having '/' instead of '.').
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ObjectType
     */
    protected String readObjectType(CharBuffer buffer) {
        // the first character must be 'L'
        if(buffer.get() != 'L')
            return "?";
        
        // start from 0 because charAt() takes index relative to current position
        // and now charAt(0) is char right after 'L''
        int i = 0;
        while(buffer.charAt(i) != ';')
            i++;
        char[] tab = new char[i];
        buffer.get(tab);
        buffer.get(); // read ';'
        
        String str = new String(tab);
        return str.replace('/', '.');
    }
    
    /**
     * Reads a FieldType from <code>buffer</code> and sets buffer's current 
     * position to char following it. <br/>
     * A FieldType is a base type, a class or an array. <br/>
     * In a .class file, a FieldType is represented as one of following:
     * <i>BaseType</i>, <i>ArrayType</i>, <i>ObjectType</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read FieldType
     */
    protected String readFieldType(CharBuffer buffer) {
        char c = buffer.charAt(0);
        switch(c) {
            case 'L':
                return readObjectType(buffer);
            case '[':
                return readArrayType(buffer);
            default:
                return readBaseType(buffer);
        }
    }
    
    /**
     * Reads a VoidDescriptor from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * A VoidDescriptor describes <code>void</code> return type. <br/>
     * In a .class file, a VoidDescriptor is represented as character 'V'.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read VoidDescriptor ("void")
     */
    protected String readVoidDescriptor(CharBuffer buffer) {
        buffer.get(); // read a 'V'
        return "void";
    }
    
    /**
     * Reads ReturnDescriptor from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * A ReturnDescriptor describes a return type of a method. <br/>
     * In a .class file, a ReturnDescriptor is represented as either <i>FieldType</i>
     * or <i>VoidDescriptor</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ReturnDescriptor
     */
    protected String readReturnDescriptor(CharBuffer buffer) {
        char c = buffer.charAt(0);
        switch(c) {
            case 'V':
                return readVoidDescriptor(buffer);
            default:
                return readFieldType(buffer);
        }
    }
    
    /**
     * Reads a ParameterDescriptor from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * A ParameterDescriptor describes an argument of a method. <br/>
     * In a .class file, a ParameterDescriptor is represented by a <i>FieldType</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ParameterDescriptor
     */
    protected String readParameterDescriptor(CharBuffer buffer) {
        return readFieldType(buffer);
    }
    
    /**
     * Reads a MethodDescriptor from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * A MethodDescriptor describes method's arguments and its return type. <br/>
     * In a .class file, a MethodDescriptor is represented as:
     * (<i>ParameterDescriptor</i>*)<i>ReturnDescriptor</i>.
     *
     * @param buffer CharBuffer to read from
     * @return a list; its first element is a String representation of method's return
     *         type, following arguments are String representations of method's
     *         arguments
     */
    public List<String> readMethodDescriptor(CharBuffer buffer) {
        List<String> result = new ArrayList<String>();
        
        // the first char must be a '('
        if(buffer.get() != '(')
            return result;
        
        // start from 0 because charAt() takes index relative to current position
        // and now charAt(0) is char right after '('
        int i = 0;
        while(buffer.charAt(i) != ')')
            i++;
        char[] tab = new char[i];
        buffer.get(tab);
        
        // read a ')'
        buffer.get();
        
        // now current index of this buffer is the first char of ReturnDescriptor, so:
        result.add(readReturnDescriptor(buffer));
        
        CharBuffer parameters = CharBuffer.wrap(tab);
        while(parameters.hasRemaining())
            result.add(readParameterDescriptor(parameters));
        
        return result;
    }
}
