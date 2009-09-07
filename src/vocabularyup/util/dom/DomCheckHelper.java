/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.util.dom;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Pokidov.Dmitry
 */
public class DomCheckHelper {
    /**
     * Get list of elements that have name {@code tagName} from {@code element} and check length of result.
     * @param element source element where tags should be find.
     * @param tagName name of needed tag
     * @param length length of result node list.
     * @param equals if value is {@code true}, than check {@code length} equals, else not equals
     * @throws DomCheckingException if result length is not correct.
     * @return result list of elements.
     */
    public static List<Element> getElementsByTagName(Element element, String tagName, int length, boolean equals) 
        throws DomCheckingException {
        NodeList nl = element.getElementsByTagName(tagName);
        int resultLength = nl.getLength();
        boolean success = equals ? resultLength == length : resultLength != length;

        if (!success) {
            throw new DomCheckingException(element.getLocalName(), tagName, length, resultLength, equals);
        }

        List<Element> result = new ArrayList<Element>();
        for (int i = 0; i < resultLength; i++) {
            result.add((Element)nl.item(i));
        }
        return result;
    }
}
