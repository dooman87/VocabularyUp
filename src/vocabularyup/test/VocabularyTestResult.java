package vocabularyup.test;

import vocabularyup.model.xml.Article;

/**
 * Result for word in test.
 * @author dooman
 */
public class VocabularyTestResult {
    private Article article;
    private String userAnswer;
    private boolean result;

    public VocabularyTestResult(Article article, String userAnswer) {
        this.article = article;
        this.userAnswer = userAnswer;
        this.result = false;

        for (String t : article.getTranslates()) {
            if (t.equals(userAnswer)) {
                result = true;
                break;
            }
        }
    }

    public Article getArticle() {
        return article;
    }

    public boolean isResult() {
        return result;
    }

    public String getUserAnswer() {
        return userAnswer;
    }    
}
