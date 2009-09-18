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
            html.append("<h2>");
            html.append("Rating:").append(article.getRating());
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
