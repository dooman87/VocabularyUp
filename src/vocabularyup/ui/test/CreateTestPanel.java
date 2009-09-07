package vocabularyup.ui.test;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import vocabularyup.VocabularyApp;
import vocabularyup.model.xml.Vocabulary;
import vocabularyup.util.ui.GridBagHelper;

/**
 *
 * @author dooman
 */
public class CreateTestPanel extends JPanel {
    private JComboBox vocabulary;
    private JSpinner wordCount;
    private JSpinner timeForWord;

    public CreateTestPanel() {
        vocabulary = new JComboBox(VocabularyApp.getInstance().getVocabularies().toArray());
        SpinnerModel wordCountSpinnerModel = new SpinnerNumberModel(10, 1, 99, 1);
        wordCount = new JSpinner(wordCountSpinnerModel);
        SpinnerModel timeSpinnerModel = new SpinnerNumberModel(15, 10, 90, 1);
        timeForWord = new JSpinner(timeSpinnerModel);
        initUI();
    }

    /**
     * Return selected vocabulary.
     * @return selected vocabulary.
     */
    public Vocabulary getVocabulary() {
        return (Vocabulary) vocabulary.getSelectedItem();
    }

    /**
     * Return number of words in test.
     * @return number of words in range [1, 99]
     */
    public int getWordCount() {
        return ((Number) wordCount.getValue()).intValue();
    }

    protected void initUI() {
        GridBagHelper grid = new GridBagHelper(this);
        grid.addLabelComponent(new JLabel("Vocabulary"));
        grid.addFieldComponent(vocabulary);

        grid.addLabelComponent(new JLabel("Number of words"));
        grid.addFieldComponent(wordCount);

        grid.addLabelComponent(new JLabel("Time for word(in seconds)"));
        grid.addFieldComponent(timeForWord);
    }

}
