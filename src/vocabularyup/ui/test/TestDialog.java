package vocabularyup.ui.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * The main dialog for test module.
 * @author dooman
 */
public class TestDialog extends JDialog {

    private class TimerTask extends SwingWorker implements ActionListener {
        private volatile boolean buttonClicked = false;

        @Override
        protected Object doInBackground() throws Exception {
            for (int i = 20; i > 0 && !buttonClicked; i--) { //countdown
                timeLabel.setText("00:" + (i < 10 ? "0" : "") + i);
                Thread.sleep(1000);
            }

            if (!buttonClicked) {
                setNextWord();
            }
            return null;
        }

        public void actionPerformed(ActionEvent e) {
            buttonClicked = true;
        }
    }

    private class NextAction extends AbstractAction {
        
        public NextAction() {
            putValue(Action.NAME, "Next >>>");
        }

        public void actionPerformed(ActionEvent e) {
            setNextWord();
        }
    }

    private TestController controller;
    private JPanel mainPanel;
    private int currentStep;
    private JButton nextButton;
    private JLabel timeLabel;

    public TestDialog(TestController controller) {
        this.controller = controller;
        this.currentStep = -1;
        this.mainPanel = new JPanel();
        this.timeLabel = new JLabel();
        nextButton = new JButton(new NextAction());
        initUI();

        setModal(true);
    }

    protected void initUI() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.add(timeLabel, BorderLayout.EAST);
        mainPanel.add(timePanel);

        mainPanel.add(new CreateTestPanel());

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalStrut(5));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(nextButton);
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }

    synchronized private void setNextWord() {
        Component currentComponent = TestDialog.this.mainPanel.getComponent(1);
        mainPanel.remove(currentComponent);
        Component wordComponent = controller.getNext(++currentStep, currentComponent);
        if (wordComponent != null) {
            mainPanel.add(wordComponent, 1);
            mainPanel.validate();
            TimerTask task = new TimerTask();
            nextButton.addActionListener(task);
            task.execute();
        } else {
            setVisible(false);
            dispose();
        }
    }
}
