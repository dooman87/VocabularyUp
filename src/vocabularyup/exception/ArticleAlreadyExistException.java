package vocabularyup.exception;

/**
 * Throws, when user want add article that already exists.
 * @author Pokidov.Dmitry
 */
public class ArticleAlreadyExistException extends Exception {
    public ArticleAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public ArticleAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleAlreadyExistException(String message) {
        super(message);
    }
}
