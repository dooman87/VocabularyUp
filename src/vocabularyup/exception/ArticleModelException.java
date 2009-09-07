/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.exception;

/**
 * Error in article model.
 * @author Pokidov.Dmitry
 */
public class ArticleModelException extends Exception {

    public ArticleModelException(Throwable cause) {
        super(cause);
    }

    public ArticleModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleModelException(String message) {
        super(message);
    }
}
