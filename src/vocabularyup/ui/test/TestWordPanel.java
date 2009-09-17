/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
