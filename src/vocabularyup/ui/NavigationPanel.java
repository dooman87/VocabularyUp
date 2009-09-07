package vocabularyup.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import vocabularyup.VocabularyApp;
import vocabularyup.exception.VocabularyNotFoundException;
import vocabularyup.ui.ArticlesView.ArticleView;

/**
 * Panel for navigation. Contains two lists: vocabularies and articles.
 * @author dooman
 */
public class NavigationPanel extends JPanel {
    private static final Logger log = Logger.getLogger(NavigationPanel.class.getName());

    //ui
    private VocabulariesView vocabulariesView = new VocabulariesView();
    private ArticlesView articlesView = new ArticlesView();
    

    public NavigationPanel() {
        initUI();
    }

    private void initUI() {
       setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
       JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
       split.setLeftComponent(vocabulariesView);
       vocabulariesView.getSelectionModel().addListSelectionListener(
       new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String currentVocabularyName = (String) vocabulariesView.getValueAt(e.getLastIndex(), 0);
                try {
                    VocabularyApp.getInstance().setCurrentVocabulary(currentVocabularyName);
                } catch (VocabularyNotFoundException ex) {
                    log.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
       }
       );
       split.setRightComponent(articlesView);
       articlesView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                ArticleView articleView = (ArticleView) articlesView.getValueAt(e.getLastIndex(), 0);
                VocabularyApp.getInstance().setSelectedArticle(articleView.getArticle());
            }
        });
       split.setResizeWeight(0.4);
       add(split);
    }
}
