/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import vocabularyup.VocabularyApp;
import vocabularyup.exception.VocabularyAlreadyExistException;
import vocabularyup.exception.VocabularyModelException;
import vocabularyup.model.xml.Vocabulary;
import vocabularyup.test.VocabularyTest;
import vocabularyup.test.VocabularyTestResult;
import vocabularyup.ui.test.CreateTestPanel;
import vocabularyup.ui.test.TestController;
import vocabularyup.ui.test.TestDialog;
import vocabularyup.ui.test.TestWordPanel;

/**
 *
 * @author 111
 */
public class MainFrame extends JFrame {
    private static final Logger log = Logger.getLogger(MainFrame.class.getName());

    public static class CreateVocabularyAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            String vocabularyName = JOptionPane.showInputDialog("Enter name of new vocabulary:");

            if (vocabularyName != null) {
                try {
                    VocabularyApp.getInstance().createVocabulary(vocabularyName);
                } catch(VocabularyAlreadyExistException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    log.log(Level.INFO, "Vocabulary already exists");
                } catch (VocabularyModelException ex) {
                    JOptionPane.showMessageDialog(null, "Internal error while create: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    log.log(Level.SEVERE, "Vocabulary creating error", ex);
                }
            }
        }
    }

    public static class ExitAction extends AbstractAction {
        
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static class AddWordAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            AddArticleDialog dialog = new AddArticleDialog(null);
            dialog.setModal(true);
            dialog.setLocationRelativeTo(null);
            dialog.setLocationByPlatform(true);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    public static class TestAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            TestDialog dialog = new TestDialog(new TestController() {
                private VocabularyTest test;
                private List<VocabularyTestResult> result;
                private int timeForWord;
                private int wordCount;

                @Override
                public Component getNext(int step, Component current) {
                    if (step == 0) {
                        log.fine("Get settings for test.");
                        CreateTestPanel panel = (CreateTestPanel) current;
                        this.timeForWord = panel.getTimeForWord();
                        this.wordCount = panel.getWordCount();
                        test = new VocabularyTest(panel.getVocabulary(), wordCount);
                        test.start();
                    } else {
                        TestWordPanel wordPanel = (TestWordPanel) current;
                        test.setAnswer(wordPanel.getAnswer());
                    }
                    if (test.hasMoreWords()) {
                        return new TestWordPanel(test.getWord());
                    } else {
                        result = test.end();
                        return null;
                    }
                }

                @Override
                public int getTimeForWord() {
                    return timeForWord;
                }

                @Override
                public int getWordCount() {
                    return wordCount;
                }

                @Override
                public List<VocabularyTestResult> getResult() throws IllegalStateException {
                    return result;
                }


            });
            dialog.setLocationRelativeTo(null);
            dialog.setLocationByPlatform(true);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    //actions
    private final CreateVocabularyAction createAction = new CreateVocabularyAction();
    private final ExitAction exitAction = new ExitAction();
    private final AddWordAction addWordAction = new AddWordAction();
    private final TestAction testAction = new TestAction();

    public MainFrame() {
        setTitle("Extend Your Vocabulary!");
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        createMainView();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        prepareAction("Create vocabulary...", createAction, KeyEvent.VK_N, "Create vocabulary");
        prepareAction("Exit", exitAction, KeyEvent.VK_X, null);
        fileMenu.add(createAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);

        JMenu editMenu = new JMenu("Edit");
        prepareAction("Add word...", addWordAction, KeyEvent.VK_A, "Add new word");
        prepareAction("Test...", testAction, KeyEvent.VK_T, "Pass test");
        editMenu.add(addWordAction);
        editMenu.add(testAction);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Create split pane. Table in left panel(vocabularies list) 
     * and panel in right panel.
     */
    private void createMainView() {
       JSplitPane split = new JSplitPane();

       split.setLeftComponent(new NavigationPanel());
       split.setRightComponent(new ArticlePanel());
       split.setResizeWeight(0.2);
       add(split);
    }

    /**
     * Create menu item.
     * @param name name of menu item.
     * @param action executing action when menu item clicked.
     * @param key hot key for menu item(will be {@code ALT+key}), 0 - if you don't want to set hotkey.
     * @param description menu item's description, may be {@code null}.
     * @return созданный пункт меню.
     */
    private void prepareAction(String name, Action action, Integer key, String description) {
        action.putValue(Action.NAME, name);
        action.putValue(Action.ACTION_COMMAND_KEY, description);
        action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, InputEvent.ALT_MASK));
        action.putValue(Action.SHORT_DESCRIPTION, description); 
    }
}
