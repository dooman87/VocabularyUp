package vocabularyup.util.dom;

import org.w3c.dom.Element;

/**
 * Helper to work with DOM.
 * @author Pokidov.Dmitry
 */
public class DomHelper {
    /**
     * Remove all childs element from {@code el}
     * @param el element that contains unnecessary children.
     */
    public static void removeChildren(Element el) {
        while (el.getChildNodes().getLength() > 0) {
            el.removeChild(el.getChildNodes().item(0));
        }
    }
}
