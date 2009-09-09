package vocabularyup.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
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
                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel model = (ListSelectionModel) e.getSource();
                if (!model.isSelectionEmpty()) {
                    String currentVocabularyName = (String) vocabulariesView.getValueAt(model.getMinSelectionIndex(), 0);
                    try {
                        log.fine("Change vocabulary selection [" + model.getMinSelectionIndex() + "]");
                        VocabularyApp.getInstance().setCurrentVocabulary(currentVocabularyName);
                    } catch (VocabularyNotFoundException ex) {
                        log.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }
            }
       }
       );
       split.setRightComponent(articlesView);
       articlesView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel model = (ListSelectionModel) e.getSource();
                if (!model.isSelectionEmpty()) {
                    log.fine("Set current article [" + model.getMinSelectionIndex() + "]");
                    ArticleView articleView = (ArticleView) articlesView.getValueAt(model.getMinSelectionIndex(), 0);
                    VocabularyApp.getInstance().setSelectedArticle(articleView.getArticle());
                }
            }
        });
       split.setResizeWeight(0.4);
       add(split);
    }
}
