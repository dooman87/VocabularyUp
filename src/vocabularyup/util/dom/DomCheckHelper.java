/*
 *  Copyright 2009 Pokidov Dmitry.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
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
