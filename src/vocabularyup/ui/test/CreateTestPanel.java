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

    /**
     * Return number of seconds for one word.
     * @return integer in range [10, 90]
     */
    public int getTimeForWord() {
        return ((Number)timeForWord.getValue()).intValue();
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
