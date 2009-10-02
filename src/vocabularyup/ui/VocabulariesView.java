/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
