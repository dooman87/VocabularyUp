/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.exception;

/**
 * Некорректная работа с ДОМ моделью документа.
 * @author 111
 */
public class VocabularyModelException extends Exception {
    private static final String MESSAGE = "Vocabulary model error";

    public VocabularyModelException() {
        this(MESSAGE);
    }

    public VocabularyModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public VocabularyModelException(String message) {
        super(message);
    }

    public VocabularyModelException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
