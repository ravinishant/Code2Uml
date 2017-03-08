/*
 * ConstructionHints.java
 *
 * Created on 3 August 2007, 07:43
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

package net.sourceforge.code2uml.graph;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * This class stores various information about how a graph (mainly its nodes)
 * should be created.
 *
 * @author Mateusz Wenus
 */
public class ConstructionHints implements Serializable {
    
    private boolean fieldsVisible = true;
    private boolean methodsVisible  = true;
    private boolean enumsVisible = true;
    private boolean privateVisible = true;
    private boolean packageVisible = true;
    private boolean protectedVisible = true;
    private boolean publicVisible = true;
    private boolean argumentsVisible = true;
    private boolean staticVisible = true;
    private boolean finalVisible = true;
    private boolean realizationDrawn = true;
    private boolean generalizationDrawn = true;
    private boolean hasADrawn = false;
    private boolean nonpublicUnitsVisible = true;
    private EdgeType hasAType = EdgeType.AGGREGATION;
    private Font font = new Font("Dialog", Font.PLAIN, 12);
    private Color backColor = Color.WHITE;
    private String layoutName = "rectangular";
    private String nodeName = "basicNodeComponent";
    
    /**
     * Creates a new instance of ConstructionHints.
     */
    public ConstructionHints() {
    }
    
    /**
     * Returns true if fields should be shown on UML diagram.
     *
     * @return true if fields should be shown on UML diagram
     */
    public boolean isFieldsVisible() {
        return fieldsVisible;
    }
    
    /**
     * Sets to indicate whether fields should be shown on UML diagram.
     *
     * @param fieldsVisible true if fields should be shown on UML diagram
     */
    public void setFieldsVisible(boolean fieldsVisible) {
        this.fieldsVisible = fieldsVisible;
    }
    
    /**
     * Returns true if methods should be shown on UML diagram.
     *
     * @return true if methods should be shown on UML diagram
     */
    public boolean isMethodsVisible() {
        return methodsVisible;
    }
    
    /**
     * Sets to indicate whether methods should be shown on UML diagram.
     *
     * @param methodsVisible true if methods should be shown on UML diagram
     */
    public void setMethodsVisible(boolean methodsVisible) {
        this.methodsVisible = methodsVisible;
    }
    
    /**
     * Returns true if enum values should be shown on UML diagram.
     *
     * @return true if enum values should be shown on UML diagram
     */
    public boolean isEnumsVisible() {
        return enumsVisible;
    }
    
    /**
     * Sets to indicate whether enum values should be shown on UML diagram.
     *
     * @param enumsVisible true if enum values should be shown on UML diagram
     */
    public void setEnumsVisible(boolean enumsVisible) {
        this.enumsVisible = enumsVisible;
    }
    
    /**
     * Returns true if private members should be shown on UML diagram.
     *
     * @return true if private members should be shown on UML diagram
     */
    public boolean isPrivateVisible() {
        return privateVisible;
    }
    
    /**
     * Sets to indicate whether private members should be shown on UML diagram.
     *
     * @param privateVisible true if private members should be shown on UML diagram
     */
    public void setPrivateVisible(boolean privateVisible) {
        this.privateVisible = privateVisible;
    }
    
    /**
     * Returns true if package members should be shown on UML diagram.
     *
     * @return true if package members should be shown on UML diagram
     */
    public boolean isPackageVisible() {
        return packageVisible;
    }
    
    /**
     * Sets to indicate whether package members should be shown on UML diagram.
     *
     * @param packageVisible true if package members should be shown on UML diagram
     */
    public void setPackageVisible(boolean packageVisible) {
        this.packageVisible = packageVisible;
    }
    
    /**
     * Returns true if protected members should be shown on UML diagram.
     *
     * @return true if protected members should be shown on UML diagram
     */
    public boolean isProtectedVisible() {
        return protectedVisible;
    }
    
    /**
     * Sets to indicate whether protected members should be shown on UML diagram.
     *
     * @param protectedVisible true if protected members should be shown on UML diagram
     */
    public void setProtectedVisible(boolean protectedVisible) {
        this.protectedVisible = protectedVisible;
    }
    
    /**
     * Returns true if public members should be shown on UML diagram.
     *
     * @return true if public members should be shown on UML diagram
     */
    public boolean isPublicVisible() {
        return publicVisible;
    }
    
    /**
     * Sets to indicate whether public members should be shown on UML diagram.
     *
     * @param publicVisible true if public members should be shown on UML diagram
     */
    public void setPublicVisible(boolean publicVisible) {
        this.publicVisible = publicVisible;
    }
    
    /**
     * Returns true if methods' arguments should be shown on UML diagram.
     *
     * @return true if methods' arguments should be shown on UML diagram
     */
    public boolean isArgumentsVisible() {
        return argumentsVisible;
    }
    
    /**
     * Sets to indicate whether should be shown on UML diagram.
     *
     * @param argumentsVisible true if should be shown on UML diagram
     */
    public void setArgumentsVisible(boolean argumentsVisible) {
        this.argumentsVisible = argumentsVisible;
    }
    
    /**
     * Returns font used to write text on UML diagram.
     *
     * @return font used to write text on UML diagram
     */
    public Font getFont() {
        return font;
    }
    
    /**
     * Sets font used to write text on UML diagram.
     *
     * @param font font used to write text on UML diagram
     */
    public void setFont(Font font) {
        this.font = font;
    }
    
    /**
     * Returns background color of graph's nodes.
     *
     * @return background color of graph's nodes
     */
    public Color getBackColor() {
        return backColor;
    }
    
    /**
     * Sets background color of graph's nodes.
     *
     * @param backColor background color of graph's nodes
     */
    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
    
    /**
     * Returns true if static members should be shown on UML diagram.
     *
     * @return true if static members should be shown on UML diagram
     */
    public boolean isStaticVisible() {
        return staticVisible;
    }
    
    /**
     * Sets to indicate whether static members should be shown on UML diagram.
     *
     * @param staticVisible true if static members should be shown on UML diagram
     */
    public void setStaticVisible(boolean staticVisible) {
        this.staticVisible = staticVisible;
    }
    
    /**
     * Returns true if final members should be shown on UML diagram.
     *
     * @return true if final members should be shown on UML diagram
     */
    public boolean isFinalVisible() {
        return finalVisible;
    }
    
    /**
     * Sets to indicate whether final members should be shown on UML diagram.
     *
     * @param finalVisible true if final members should be shown on UML diagram
     */
    public void setFinalVisible(boolean finalVisible) {
        this.finalVisible = finalVisible;
    }
    
    /**
     * Returns true if classes/interfaces/enums which are not visible outside of
     * packages they are defined in should be shown on UML diagram.
     *
     * @return true if nonpublic units should be shown on UML diagram
     */
    public boolean isNonpublicUnitsVisible() {
        return nonpublicUnitsVisible;
    }
    
    /**
     * Sets to indicate whether nonpublic units should be shown on UML diagram.
     *
     * @param nonpublicUnitsVisible true if nonpublic units should be shown on 
     *        UML diagram
     */
    public void setNonpublicUnitsVisible(boolean nonpublicUnitsVisible) {
        this.nonpublicUnitsVisible = nonpublicUnitsVisible;
    }
    
    /**
     * Returns name of GraphLayout that should be used to layout graph's nodes.
     *
     * @return name of GraphLayout that should be used to layout graph's nodes
     */
    public String getLayoutName() {
        return layoutName;
    }
    
    /**
     * Sets the name of GraphLayout that should be used to layout graph's nodes
     *
     * @param layoutName name of GraphLayout that should be used to layout graph's nodes
     */
    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }
    
    /**
     * Returns name of subtype of NodeComponent that should be used to represent 
     * graph's nodes.
     *
     * @return name of subtype of NodeComponent that should be used to represent 
     *         graph's nodes
     */
    public String getNodeName() {
        return nodeName;
    }
    
    /**
     * Sets name of subtype of NodeComponent that should be used to represent 
     * graph's nodes. 
     *
     * @param nodeName name of subtype of NodeComponent that should be used to represent 
     *        graph's nodes
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /** 
     * Returns true if realization relationships should be drawn on UML diagram.
     *
     * @return true if and only if realization relationships should be drawn on 
     *         UML diagram
     */
    public boolean isRealizationDrawn() {
        return realizationDrawn;
    }

    /**
     * Sets whether realization relationships should be drawn on UML diagram.
     *
     * @param realizationDrawn true if and only if realization relationships
     *        should be drawn on UML diagram
     */
    public void setRealizationDrawn(boolean realizationDrawn) {
        this.realizationDrawn = realizationDrawn;
    }

    /** 
     * Returns true if generalization relationships should be drawn on UML diagram.
     *
     * @return true if and only if generalization relationships should be drawn on 
     *         UML diagram
     */
    public boolean isGeneralizationDrawn() {
        return generalizationDrawn;
    }

    /**
     * Sets whether generalization relationships should be drawn on UML diagram.
     *
     * @param generalizationDrawn true if and only if generalization relationships
     *        should be drawn on UML diagram
     */
    public void setGeneralizationDrawn(boolean generalizationDrawn) {
        this.generalizationDrawn = generalizationDrawn;
    }

    /** 
     * Returns true if "has a" relationships should be drawn on UML diagram.
     *
     * @return true if and only if "has a" relationships should be drawn on 
     *         UML diagram
     */
    public boolean isHasADrawn() {
        return hasADrawn;
    }

    /**
     * Sets whether "has a" relationships shoulf be drawn on UML diagram.
     *
     * @param hasADrawn true if and only if "has a" relationships
     *        should be drawn on UML diagram
     */
    public void setHasADrawn(boolean hasADrawn) {
        this.hasADrawn = hasADrawn;
    }

    /**
     * Returns a type which all "has a" relationships on UML diagram should have.
     *
     * @return a type which all "has a" relationships on UML diagram should have
     */
    public EdgeType getHasAType() {
        return hasAType;
    }

    /**
     * Sets a type which all "has a" relationships on UML diagram will have. 
     * The only values that are allowed are EdgeType.AGGRGATION and 
     * EdgeType.COMPOSITION.
     *
     * @param hasAType either EdgeType.AGGRGATION or EdgeType.COMPOSITION
     */
    public void setHasAType(EdgeType hasAType) {
        if(hasAType != EdgeType.AGGREGATION && hasAType != EdgeType.COMPOSITION)
            throw new IllegalArgumentException();
        this.hasAType = hasAType;
    }
    
}
