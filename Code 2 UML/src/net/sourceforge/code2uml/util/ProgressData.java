/*
 * ProgressData.java
 *
 * Created on August 29, 2007, 5:53 PM
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

package net.sourceforge.code2uml.util;

/**
 * Carries information about progress of work of a background thread. Stores: <br/>
 * - percentage of work that is already done <br/>
 * - brief description of task and its progress (optional)
 *
 * @author Mateusz Wenus
 */
public class ProgressData {
    
    private double progress;
    private String message;
    
    /**
     * Creates a new instance of ProgressData, describing a task which is
     * complete in <code>progress</code> percent and has <code>message</code>
     * description. If <code>progress</code> is not in range [0, 100] it is set
     * to its closest value n that range.
     *
     * @param progress value in range [0, 100] describing percentage of work done
     * @param message description of task and its progress
     */
    public ProgressData(double progress, String message) {
        if(progress < 0.0)
            progress = 0.0;
        else if(progress > 100.0)
            progress = 100.0;
        this.progress = progress;
        this.message = message;
    }
    
    /**
     * Creates a new instance of ProgressData, describing a task which is
     * complete in <code>progress</code> percent and has no description.
     * If <code>progress</code> is not in range [0, 100] it is set to its 
     * closest value n that range.
     *
     * @param progress value in range [0, 100] describing percentage of work done
     */
    public ProgressData(double progress) {
        if(progress < 0.0)
            progress = 0.0;
        else if(progress > 100.0)
            progress = 100.0;
        this.progress = progress;
    }

    /**
     * Returns a double in range [0, 100] describing percentage of work that
     * is already done.
     *
     * @return a double in range [0, 100] describing percentage of work that
     *         is already done
     */
    public double getProgress() {
        return progress;
    }

    /**
     * Returns a String description of currently performed task and its progress.
     * That String may then be set to a JProgressBar, JLabel or simply ignored.
     * This method may return null as it is optional to set value for message
     * string.
     *
     * @return String description of currently performed task and its progress
     */
    public String getMessage() {
        return message;
    }
}
