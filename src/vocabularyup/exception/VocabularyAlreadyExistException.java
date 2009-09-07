/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.exception;

/**
 * Словарь с таким именем уже существует.
 * 
 * @author 111
 */
public class VocabularyAlreadyExistException extends Exception {

    public VocabularyAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public VocabularyAlreadyExistException(String vocabularyName, Throwable cause) {
        super(getErrorMessage(vocabularyName), cause);
    }

    public VocabularyAlreadyExistException(String vocabularyName) {
        super(getErrorMessage(vocabularyName));
    }

    private static String getErrorMessage(String vocabularyName) {
        return "Vocabulary \"" + vocabularyName + "\" already exists";
    }
}
