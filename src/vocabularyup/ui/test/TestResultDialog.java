/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.ui.test;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import vocabularyup.test.VocabularyTestResult;

/**
 * Dialog for show test's result.
 * TODO: columns size in table.
 * @author Pokidov.Dmitry
 */
public class TestResultDialog extends JDialog {

    private class TestResultModel extends AbstractTableModel {
        private List<VocabularyTestResult> result;

        public TestResultModel(List<VocabularyTestResult> result) {
            this.result = result;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0: return "Word";
                case 1: return "Answer";
                case 2: return "Rigth answer";
                case 3: return "Result";
            }

            return null;
        }

        @Override
        public int getRowCount() {
            return result.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            VocabularyTestResult res = result.get(rowIndex);
            switch (columnIndex) {
                case 0: return res.getArticle().getSource();
                case 1: return res.getUserAnswer();
                case 2:
                    StringBuilder builder = new StringBuilder();
                    List<String> translates = res.getArticle().getTranslates();
                    for (String t : translates) {
                        builder.append(t);
                        builder.append(" ");
                    }
                    return builder.toString();
                case 3: return res.isResult() ? "Y" : "N";
            }

            return null;
        }

    }

    private class FinishAction extends AbstractAction {

        public FinishAction() {
            putValue(Action.NAME, "Ok");
            putValue(Action.ACCELERATOR_KEY, KeyEvent.VK_ENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: write result to vocabulary!
            TestResultDialog.this.setVisible(false);
            TestResultDialog.this.dispose();
        }
    }

    private JTable resultTable;

    public TestResultDialog(List<VocabularyTestResult> result) {
        resultTable = new JTable(new TestResultModel(result));
        initUI();
        setModal(true);
        setTitle("Your Result");
    }

    protected void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        mainPanel.add(resultTable.getTableHeader());
        mainPanel.add(new JScrollPane(resultTable));

        mainPanel.add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        JButton okButton = new JButton(new FinishAction());
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }
}
