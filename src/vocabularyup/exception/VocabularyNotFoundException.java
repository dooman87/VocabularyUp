/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.exception;

/**
 * Словарь не найден.
 * @author 111
 */
public class VocabularyNotFoundException extends Exception {

    public VocabularyNotFoundException(Throwable cause) {
        super(cause);
    }

    public VocabularyNotFoundException(String vocabularyName, Throwable cause) {
        super(getErrorMessage(vocabularyName), cause);
    }

    public VocabularyNotFoundException(String vocabularyName) {
        super(getErrorMessage(vocabularyName));
    }

    private static String getErrorMessage(String vocabularyName) {
        return "Vocabulary " + vocabularyName + " not found";
    }
}
