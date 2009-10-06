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

import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import vocabularyup.model.xml.Article;

/**
 * Text pane that show current article.
 * @author dooman
 */
public class ArticleView extends JTextPane {
    private static final Logger log = Logger.getLogger(ArticleView.class.getName());

    public ArticleView() {
        setEditorKit(new HTMLEditorKit());
        setEditable(false);
    }

    public void setArticle(Article article) {
        log.fine("Set article to view [" + (article == null ? "null" : article.getSource()) + "]");
        if (article != null) {
            StringBuilder html = new StringBuilder();
            html.append("<html>");
            html.append("<head></head>");
            html.append("<body>");
            html.append("<h1><u>");
            html.append(article.getSource());
            html.append("</u></h1>");

            html.append("<ul>");
            for (String translate : article.getTranslates()) {
                html.append("<li><b>");
                html.append(translate);
                html.append("</b></li>");
            }
            html.append("</ul>");

            html.append("<ul>");
            for (String example : article.getExamples()) {
                html.append("<li><b>");
                html.append(example);
                html.append("</b></li>");
            }
            html.append("</ul>");
            String color = Integer.valueOf(article.getRating()) < 0 ? "#FF0000" : "#F0FFFF";
            html.append("<h2 style=\"text-align:right;background-color:").append(color).append(";\">");
            html.append("Rating: ").append(article.getRating());
            html.append("</h2>");
            html.append("</body>");
            html.append("</html>");

            setText(html.toString());
        } else {
            StringBuilder html = new StringBuilder();
            html.append("<html>");
            html.append("<head></head>");
            html.append("<body>");
            html.append("No selected articles");
            html.append("</body>");
            html.append("</html>");
            setText(html.toString());
        }

        log.finest("Set text to:\n" + getText());
    }
}
