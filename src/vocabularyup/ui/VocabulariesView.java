/*
 *  Copyright 2009 Pokidov Dmitry.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package vocabularyup.ui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import vocabularyup.VocabularyApp;
import vocabularyup.VocabularyAppAdapter;
import vocabularyup.VocabularyAppEvent;

/**
 *
 * @author 111
 */
public class VocabulariesView extends JTable {
    public static class VocabulariesModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return VocabularyApp.getInstance().getVocabularies().size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return VocabularyApp.getInstance().getVocabularies().get(rowIndex).getName();
        }
    }

    private VocabulariesModel model = new VocabulariesModel();

    public VocabulariesView() {
        setModel(model);
        getTableHeader().setVisible(false);
        VocabularyApp.getInstance().addListener(new VocabularyAppAdapter(){
            @Override
            public void addedVocabulary(VocabularyAppEvent event) {
                model.fireTableDataChanged();
            }
        });
    }
}
