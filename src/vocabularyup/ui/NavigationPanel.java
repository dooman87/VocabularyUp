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

package vocabularyup.ui;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

       JScrollPane leftComonent = new JScrollPane(vocabulariesView);
       Dimension leftComponentSize = leftComonent.getPreferredSize();
       leftComponentSize.width = 200;
       leftComonent.setPreferredSize(leftComponentSize);
       split.setLeftComponent(leftComonent);

       vocabulariesView.getSelectionModel().addListSelectionListener(
       new ListSelectionListener() {
            @Override
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

       JScrollPane rightComponent = new JScrollPane(articlesView);
       Dimension rightComponentSize = rightComponent.getPreferredSize();
       rightComponentSize.width = 200;
       rightComponent.setPreferredSize(rightComponentSize);
       //split.setRightComponent(new JScrollPane(articlesView));
       split.setRightComponent(rightComponent);
       articlesView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
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

       //split.setMaximumSize(new Dimension(200, split.getMaximumSize().height));
       //setMaximumSize(new Dimension(200, getMaximumSize().height));
    }
}
