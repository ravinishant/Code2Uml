/*
 * ControllerFactory.java
 *
 * Created on August 6, 2007, 11:15 PM
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

package net.sourceforge.code2uml.controller;

/**
 * Provides static methods to get Controller's instance.
 *
 * @author Mateusz Wenus
 */
public class ControllerFactory {
    
    private static Controller instance = new ControllerImpl();
    
    /** 
     * Creates a new instance of ControllerFactory. 
     */
    private ControllerFactory() {
    }
    
    /**
     * Returns Controller's instance.
     * 
     * @return Controller's instance
     */
    public static Controller getInstance() {
        return instance;
    }
}
