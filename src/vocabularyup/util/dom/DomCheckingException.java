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
