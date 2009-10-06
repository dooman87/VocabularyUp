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
