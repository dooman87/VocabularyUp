package vocabularyup.ui;

import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import vocabularyup.model.xml.Article;

/**
 * Text pane that show current article.
 * @author dooman
 */
public class ArticleView extends JTextPane {

    public ArticleView() {
        setEditorKit(new HTMLEditorKit());
        setEditable(false);
    }

    public void setArticle(Article article) {
        if (article != null) {
            StringBuilder html = new StringBuilder();
            html.append("<html>");
            html.append("<h1>");
            html.append(article.getSource());
            html.append("</h1>");

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
            html.append("</ul>");
            html.append("</html>");

            setText(html.toString());
        } else {
            setText("");
        }
    }
}
