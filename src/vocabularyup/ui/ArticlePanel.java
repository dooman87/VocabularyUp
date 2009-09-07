package vocabularyup.ui;

import java.awt.GridBagConstraints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import vocabularyup.VocabularyApp;
import vocabularyup.VocabularyAppAdapter;
import vocabularyup.VocabularyAppEvent;
import vocabularyup.util.ui.GridBagHelper;

/**
 * Panel for search articles in vocabularies and show it.
 *
 * @author dooman
 */
public class ArticlePanel extends JPanel {
    private static final Logger log = Logger.getLogger(ArticlePanel.class.getName());

    //ui
    private JTextField searchEdit = new JTextField();
    private ArticleView resultView = new ArticleView();
    //ui

    public ArticlePanel() {
        initUI();
        init();
    }

    private void initUI() {
        GridBagHelper grid = new GridBagHelper(this);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(new JLabel("Search:"));
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(searchEdit);
        topPanel.add(Box.createHorizontalGlue());
        GridBagConstraints topPanelConstaint = 
                new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 
                                    GridBagConstraints.REMAINDER, 1, 
                                    1.0, 0.0, 
                                    GridBagConstraints.CENTER, 
                                    GridBagConstraints.HORIZONTAL, 
                                    GridBagHelper.DEFAULT_INSETS, 0, 0);
        grid.addComponent(topPanel, topPanelConstaint);

        grid.addComponent(resultView, GridBagHelper.MULTIROW_FIELD_CONSTRAINT);
    }

    private void init() {
        searchEdit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    log.info("Finding article with filter");
                    VocabularyApp.getInstance().search(searchEdit.getText());
                }
            }
        });
        VocabularyApp.getInstance().addListener(new VocabularyAppAdapter(){

            @Override
            public void selectedArticleChange(VocabularyAppEvent event) {
                resultView.setArticle(event.getArticle());
            }

        });
    }
}
