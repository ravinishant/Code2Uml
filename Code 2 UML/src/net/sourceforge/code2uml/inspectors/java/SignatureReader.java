/*
 * SignatureReader.java
 *
 * Created on August 17, 2007, 12:54 PM
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Reads classes', fields' and methods' signatures (as defined in class file 
 * specification) and returns their String representations. <br/></br>
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
 * <code> while(condition) readTypeArgument(buffer); </code>
 *
 * @author Mateusz Wenus
 */
public class SignatureReader {
    
    private DescriptorReader descriptorReader = new DescriptorReader();
    private static final Set<Character> identifierTerminatingChar = new HashSet<Character>();
    static {
        identifierTerminatingChar.add(';');
        identifierTerminatingChar.add('<');
        identifierTerminatingChar.add('.');
        identifierTerminatingChar.add('/');
        identifierTerminatingChar.add(':');
    }
    
    /**
     * Creates a new instance of SignatureReader.
     */
    public SignatureReader() {
    }
    
    /*
     * This class defines a lot of methods that call each other which may look 
     * confusing. In fact, it defines a method for reading each of structures
     * defined in "The Java Virtual Machine Specification", Chapter 4 "The
     * class File Format", paragraph 4.4 Signatures. See that documentation.
     */
    
    /**
     * Reads a FieldTypeSignature from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, a FieldTypeSignature is represented as one of:
     * <i>ClassTypeSignature</i>, <i>ArrayTypeSignature</i>,
     * <i>TypeVariableSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read $
     */
    public String readFieldTypeSignature(CharBuffer buffer) {
        switch(buffer.charAt(0)) {
            case 'L':
                return readClassTypeSignature(buffer);
            case '[':
                return readArrayTypeSignature(buffer);
            case 'T':
                return readTypeVariableSignature(buffer);
            default:
                return "?";
        }
    }
    
    /**
     * Reads a TypeSignature from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In a .class file, a TypeSignature is represented as either
     * <i>FieldTypeSignature</i> or <i>BaseType</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read TypeSignature
     */
    protected String readTypeSignature(CharBuffer buffer) {
        char c = buffer.charAt(0);
        if(c == 'L' || c =='[' || c == 'T')
            return readFieldTypeSignature(buffer);
        else
            return descriptorReader.readBaseType(buffer);
    }
    
    /**
     * Reads an ArrayTypeSignature from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, an ArrayTypeSignature is represented as: <br/>
     * [<i>TypeSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ArrayTypeSignature
     */
    protected String readArrayTypeSignature(CharBuffer buffer) {
        if(buffer.get() != '[')
            return "?";
        
        return readTypeSignature(buffer) + "[]";
    }
    
    /**
     * Reads a WildcardIndicator from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, a WildcardIndicator is represented as either '+' or '-'.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read WildcardIndicator
     */
    protected String readWildcardIndicator(CharBuffer buffer) {
        char c = buffer.get();
        if(c == '+')
            return "? extends ";
        else if(c == '-')
            return "? super ";
        else
            return "?";
    }
    
    /**
     * Reads a TypeArgument from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In a .class file, a TypeArgument is represented as either <br/>
     * <i>WildcardIndicator</i>(optional) <i>FieldTypeSignature</i> or <br/>
     * character '*'. <br/><br/>
     *
     * Note: this method may return "?"; this does not indicate an error but
     * a situation when generic type has not been specified (for example in
     * declaration List<?> list).
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read TypeArgument
     */
    protected String readTypeArgument(CharBuffer buffer) {
        String result = "";
        char c = buffer.charAt(0);
        
        if(c == '*') {
            buffer.get(); // don't forget about setting buffer's current position!
            return "?";
        }
        if(c == '+' || c == '-') {
            result = readWildcardIndicator(buffer);
        }
        
        return result + readFieldTypeSignature(buffer);
    }
    
    /**
     * Reads TypeArguments from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In a .class file, TypeArguments is represented as: <br/>
     * &lt<i>TypeArgument</i>+&gt
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read TypeArguments
     */
    protected String readTypeArguments(CharBuffer buffer) {
        if(buffer.get() != '<')
            return "?";
        
        StringBuilder builder = new StringBuilder();
        builder.append('<');
        builder.append(readTypeArgument(buffer)); // there is always at least one
        
        while(buffer.charAt(0) != '>') {
            builder.append(", ");
            builder.append(readTypeArgument(buffer));
        }
        
        buffer.get(); // read '>' to leave correct buffer's current position
        builder.append('>');
        
        return builder.toString();
    }
    
    /**
     * Reads an Identifier from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In signature an Identifier denotes "an identifier for a type, field, local
     * variable, parameter, method name or type variable, as generated by Java
     * language". (The class File Format, 4.4.4 Signatures) <br/>
     * In a .class file and identifier is used in: <i>FormalTypeParameter</i>,
     * <i>PackageSpecifier</i>, <i>SimpleClassTypeSignature</i>,
     * <i>TypeVariableSignature</i>. It is a sequence of characters, ending with
     * one of ';', ':', '.', '/', '<'. Note that this method does <b>not</b>
     * read the terminating character.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read Identifier
     */
    protected String readIdentifier(CharBuffer buffer) {
        
        int i = 0;
        while(!identifierTerminatingChar.contains(buffer.charAt(i)))
            i++;
        
        char[] tab = new char[i];
        buffer.get(tab);
        
        return new String(tab);
    }
    
    /**
     * Reads a TypeVariableSignature from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, a TypeVariableSignature is represented as: <br/>
     * <b>T Identifier ;</b>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read TypeVariableSignature
     */
    protected String readTypeVariableSignature(CharBuffer buffer) {
        if(buffer.get() != 'T')
            return "?";
        
        String result = readIdentifier(buffer);
        
        buffer.get(); // read a ';' to leave correct buffer's current position
        return result;
    }
    
    /**
     * Reads a SimpleClassTypeSignature from <code>buffer</code> and sets
     * buffer's current position to char following it. <br/>
     * In a .class file, a SimpleClassTypeSignature is represented as: <br/>
     * <b>Identifier</b> <i>TypeArguments</i>(optional).
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read SimpleClassTypeSignature
     */
    protected String readSimpleClassTypeSignature(CharBuffer buffer) {
        String result = readIdentifier(buffer);
        if(buffer.charAt(0) == '<')
            result += readTypeArguments(buffer);
        return result;
    }
    
    /**
     * Reads a ClassTypeSignatureSuffix from <code>buffer</code> and sets
     * buffer's current position to char following it. <br/>
     * In a .class file, a ClassTypeSignatureSuffix is represented as: <br/>
     * <b>.</b><i>SimpleClassTypeSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ClassTypeSignatureSuffix
     */
    protected String readClassTypeSignatureSuffix(CharBuffer buffer) {
        if(buffer.get() != '.')
            return "?";
        
        // dots in signatures must be converted to '$' characters
        // (The class File Format, 4.4.4 Signatures)
        return "$" + readSimpleClassTypeSignature(buffer);
    }
    
    /*
     * Note: The class FileFormat, 4.4.4 Signatures defines a PackageSpecifier.
     * However, it is represented as: Identifier / PackageSpecifier* and
     * it is used in ClassTypeSignature which is defined as: <br/>
     * L PackageSpecifier* SimpleClassTypeSignature ClassTypeSignatureSuffix*;
     *
     * Since SimpleClassTypeSignature starts with Identifier just like
     * PackageSpecifier it would be very difficult to tell where PackageIdentifier
     * ends. However, PackageSpecifier is used in ClassTypeSignature only,
     * so there won't be a separate method for it and another method
     * (readClassTypeSignature) will be responsible for reading PackageSpecifiers.
     
     protected String readPackageSpecifier(CharBuffer buffer) {
         throw new UnsupportedOperationExcepion();
     }
     */
    
    /**
     * Reads a ClassTypeSignature from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, a ClassTypeSignature is represented as: <br/>
     * L PackageSpecifier* SimpleClassTypeSignature ClassTypeSignatureSuffix*;
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ClassTypeSignature
     */
    protected String readClassTypeSignature(CharBuffer buffer) {
        if(buffer.get() != 'L')
            return "?";
        
        StringBuilder builder = new StringBuilder();
        /*
         * ClassTypeSignature is defined as:
         * L <i>PackageSpecifier</i>* <i>SimpleClassTypeSignature ClassTypeSignatureSuffix</i>*;
         *
         * So, there's an 'L' at the beginning and then some number of Identifiers
         * terminated by '/' characters. When we read an Identifier which is
         * terminated by another character, one of following happened:
         *
         * - the char is a '<' - the last read Identifier belongs to
         *   SimpleClassTypeSignature and now the buffer starts with TypeArguments
         *   of that SimpleClassTypeSignature
         * - the char is a '.' - the last read Identifier belongs to
         *   SimpleClassTypeSignature which has no TypeArguments and now the
         *   buffer starts with ClassTypeSignatureSuffix
         * - the char is a ';' - the last read Identifier belongs to
         *   SimpleClassTypeSignature which has no TypeArguments and isn't
         *   followed by any ClassTypeSignatureSuffixes; the ';' indicates end
         *   of ClassTypeSignature
         *
         * Note that there's always at least one Identifier (belonging to
         * SimpleClassTypeSignature).
         */
        
        builder.append(readIdentifier(buffer));
        while(buffer.charAt(0) == '/') {
            buffer.get();
            builder.append('.');
            builder.append(readIdentifier(buffer));
        }
        switch(buffer.charAt(0)) {
            case '<':
                builder.append(readTypeArguments(buffer));
                while(buffer.charAt(0) == '.')
                    builder.append(readClassTypeSignatureSuffix(buffer));
                buffer.get(); // read a ';' to leave correct buffer's current position!
                return builder.toString();
            case '.':
                do {
                    builder.append(readClassTypeSignatureSuffix(buffer));
                } while(buffer.charAt(0) == '.');
                buffer.get(); // read a ';' to leave correct buffer's current position!
                return builder.toString();
            case ';':
                buffer.get(); // dont't forget to leave correct buffer's current position!
                return builder.toString();
            default:
                return "?";
        }
    }
    
    /**
     * Reads a SuperinterfaceSignature from <code>buffer</code> and sets
     * buffer's current position to char following it. <br/>
     * In a .class file, a SuperinterfaceSignature is represented as:
     * <i>ClassTypeSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read SuperinterfaceSignature
     */
    protected String readSuperinterfaceSignature(CharBuffer buffer) {
        return readClassTypeSignature(buffer);
    }
    
    /**
     * Reads a SuperclassSignature from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, a SuperclassSignature is represented as:
     * <i>ClassTypeSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read SuperclassSignature
     */
    protected String readSuperclassSignature(CharBuffer buffer) {
        return readClassTypeSignature(buffer);
    }
    
    /**
     * Reads an InterfaceBound from <code>buffer</code> and sets buffer's
     * current position to char following it. <br/>
     * In a .class file, an InterfaceBound is represented as: <br/>
     * <b>:</b><i>FieldTypeSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read InterfaceBound
     */
    protected String readInterfaceBound(CharBuffer buffer) {
        if(buffer.get() != ':')
            return "?";
        return readFieldTypeSignature(buffer);
    }
    
    /**
     * Reads a ClassBound from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In a .class file, a ClassBound is represented as: <br/>
     * <b>:</b><i>FieldTypeSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ClassBound
     */
    protected String readClassBound(CharBuffer buffer) {
        if(buffer.get() != ':')
            return "?";
        
        return readFieldTypeSignature(buffer);
    }
    
    /**
     * Reads a FormalTypeParameter from <code>buffer</code> and sets buffer's 
     * current position to char following it. <br/>
     * In a .class file, a FormalTypeParameter is represented as: <br/>
     * <b>Identifier</b> <i>ClassBound InterfaceBound</i>*.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read FormalTypeParameter
     */
    protected String readFormalTypeParameter(CharBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        builder.append(readIdentifier(buffer));
        builder.append(' ');
        builder.append(readClassBound(buffer));
        while(buffer.charAt(0) == ':') {
            builder.append(' ');
            builder.append(readInterfaceBound(buffer));
        }
        return builder.toString();
    }
    
    /**
     * Reads FormalTypeParameters from <code>buffer</code> and sets buffer's 
     * current position to char following it. <br/>
     * In a .class file, FormalTypeParameters struct is represented as: <br/>
     * &lt<i>FormalTypeParameter</i>+&gt
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read FormalTypeParameters
     */
    protected String readFormalTypeParameters(CharBuffer buffer) {
        if(buffer.get() != '<')
            return "?";
        
        StringBuilder builder = new StringBuilder('<');
        builder.append(readFormalTypeParameter(buffer));
        while(buffer.charAt(0) != '>') {
            builder.append(", ");
            builder.append(readFormalTypeParameter(buffer));
        }
        buffer.get();
        builder.append('>');
        return builder.toString();
    }
    
    /**
     * Reads a ClassSignature from <code>buffer</code> and sets buffer's current
     * position to char following it. <br/>
     * In a .class file, a ClassSignature is represented as: <br/>
     * <i>FormalTypeParameters</i>(optional) <i>SuperclassSignature 
     * SuperinterfaceSignature</i>*.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ClassSignature
     */
    public String readClassSignature(CharBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        if(buffer.charAt(0) == '<') {
            builder.append(readFormalTypeParameters(buffer));
            builder.append(' ');
        }
        
        builder.append(readSuperclassSignature(buffer));
        while(buffer.charAt(0) == 'L') {
            builder.append(' ');
            builder.append(readSuperinterfaceSignature(buffer));
        }
        return builder.toString();
    }
    
    /**
     * Reads a ReturnType from <code>buffer</code> and sets buffer's current 
     * position to char following it. <br/>
     * In a .class file, a ReturnType is represented as either
     * <i>TypeSignature</i> or <i>VoidDescriptor</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ReturnType
     */
    protected String readReturnType(CharBuffer buffer) {
        if(buffer.charAt(0) == 'V')
            return descriptorReader.readVoidDescriptor(buffer);
        else
            return readTypeSignature(buffer);
    }
    
    /**
     * Reads a ThrowsSignature from <code>buffer</code> and sets buffer's 
     * current position to char following it. <br/>
     * In a .class file, a ThrowsSignature is represented as either
     * <b>^</b><i>ClassTypeSignature</i> or <b>^</b><i>TypeVariableSignature</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read ThrowsSignature
     */
    protected String readThrowsSignature(CharBuffer buffer) {
        if(buffer.get() != '^')
            return "?";
        
        if(buffer.charAt(0) == 'L')
            return readClassTypeSignature(buffer);
        else
            return readTypeVariableSignature(buffer);
    }
    
    /**
     * Reads a MethodTypeSignature from <code>buffer</code> and sets buffer's 
     * current position to char following it. <br/>
     * In a .class file, a MethodTypeSignature is represented as: <br/>
     * <i>FormalTypeParameters</i>(optional) <b>(</b><i>TypeSignature*</i>
     * <b>)</b> <i>ReturnType</i>.
     *
     * @param buffer CharBuffer to read from
     * @return String representation of read MethodTypeSignature
     */
    public List<String> readMethodTypeSignature(CharBuffer buffer) {
        LinkedList<String> result = new LinkedList<String>();
        if(buffer.charAt(0) == '<')
            readFormalTypeParameters(buffer);
        
        if(buffer.get() != '(')
            return result;
        while(buffer.charAt(0) != ')')
            result.addLast(readTypeSignature(buffer));
        buffer.get();
        
        result.addFirst(readReturnType(buffer));
        
        while(buffer.hasRemaining() && buffer.charAt(0) == '^')
            readThrowsSignature(buffer);
        
        return result;
    }
}
