package vocabularyup.ui.test;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * The main dialog for test module.
 * @author dooman
 */
public class TestDialog extends JDialog {

    private class TimerTask extends SwingWorker implements ActionListener {
        @Override
        protected Object doInBackground() throws Exception {
            for (int i = 20; i > 0; i--) { //countdown
                //TODO: set time
            }
            setNextWord();
            return null;
        }

        public void actionPerformed(ActionEvent e) {
            //TODO: stop processing
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

    public TestDialog(TestController controller) {
        this.controller = controller;
        this.currentStep = 0;
        this.mainPanel = new JPanel();
        nextButton = new JButton(new NextAction());
        initUI();
    }

    protected void initUI() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(new CreateTestPanel());

        mainPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(nextButton);
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }

    synchronized private void setNextWord() {
        Component currentComponent = TestDialog.this.mainPanel.getComponent(0);
        TestDialog.this.mainPanel.remove(currentComponent);
        TestDialog.this.mainPanel.add(controller.getNext(++currentStep, currentComponent), 0);
        TimerTask task = new TimerTask();
        nextButton.addActionListener(task);
        task.execute();
    }
}
