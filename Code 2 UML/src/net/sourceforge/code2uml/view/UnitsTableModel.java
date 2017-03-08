/*
 * UnitsTableModel.java
 *
 * Created on August 7, 2007, 8:33 AM
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

package net.sourceforge.code2uml.view;

import javax.swing.table.DefaultTableModel;
import net.sourceforge.code2uml.unitdata.UnitInfo;

/**
 * Provides model for table of UnitInfos. That table has two columns, first
 * of them contains names of units, seconds indicates whether unit will be
 * included in diagram. Only the last column is editable.
 *
 * @author Mateusz Wenus
 */
class UnitsTableModel extends DefaultTableModel {
    
    private static String[] columnHeaders = {"name", "include?"};
    
    /** 
     * Creates a new instance of UnitsTableModel. 
     */
    public UnitsTableModel() {
        super(columnHeaders, 0);
        
    }
    
    /**
     * Returns type of values in given column.
     *
     * @param columnIndex index of a column
     * @return type of values in given column
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == getColumnCount() - 1)
            return Boolean.class;
        else
            return String.class;
    }
    
    /**
     * Returns true if given cell is editable.
     *
     * @param row row number of that cell
     * @param column number of that cell
     * @return true if given cell is editable
     */
    @Override 
    public boolean isCellEditable(int row, int column) {
        return column == getColumnCount() - 1;
    }
    
    /**
     * Adds specified UnitInfo to the table.
     * 
     * @param unit UnitInfo to add
     */
    public void addRow(UnitInfo unit) {
        super.addRow(new Object[]{unit.getSimpleName(), !unit.getSimpleName().contains("$")});
    }
}
