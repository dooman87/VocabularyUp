/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.util.dom;

/**
 * Exception trows when invalid number of specified elements found in DOM model.
 * @author Pokidov.Dmitry
 */
public class DomCheckingException extends Exception {
    /**
     * Construct new DOM checking exception.
     * @param parentTagName name of tag that contains invalid elements count.
     * @param tagName name of error tag.
     * @param expectedLength expected count of tag.
     * @param resultLength real count of tag.
     * @param equals if true than {@code expectedLength} and {@code resultLength} 
     *  should was equals, else {@code expectedLength} and {@code resultLengh} shouldn't was equals.
     */
    public DomCheckingException(String parentTagName, String tagName,
            int expectedLength, int resultLength, boolean equals) {
        super("Invalid elements " + tagName + " count in " +
                parentTagName + " (count must be " + (!equals ? "not " : "") + 
                "equals " + expectedLength + " but " + resultLength + " )");
    }
}
