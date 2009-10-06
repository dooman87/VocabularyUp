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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel represents question in test.
 *
 * @author Pokidov.Dmitry
 */
public class TestWordPanel extends JPanel {
    private String word;
    private JTextField answer;

    public TestWordPanel(String word) {
        super();
        this.word = word;
        this.answer = new JTextField();
        initUI();
    }

    protected void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        JLabel wordLabel = new JLabel(word);
        wordLabel.setFont(wordLabel.getFont().deriveFont(20.0f));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        labelPanel.add(Box.createHorizontalGlue());
        labelPanel.add(wordLabel);
        labelPanel.add(Box.createHorizontalGlue());
        add(labelPanel);

        answer.setFont(answer.getFont().deriveFont(20.0f));
        answer.setHorizontalAlignment(JTextField.CENTER);
        add(answer);
    }

    public String getAnswer() {
        return answer.getText();
    }

    @Override
    public void requestFocus() {
        answer.requestFocus();
    }
}
