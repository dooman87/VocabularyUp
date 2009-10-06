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

package vocabularyup.ui.test;

import java.awt.Component;
import java.util.List;
import vocabularyup.test.VocabularyTestResult;

/**
 *
 * @author dooman
 */
public interface TestController {
    /**
     * Must return next component for test dialog.
     * @param step number of step(from 0)
     * @param current current component, that was in dialog(can be {@code null})
     * @return new component for next step.
     */
    Component getNext(int step, Component current);

    /**
     * Return number of words.
     * @return number of words in current test.
     */
    int getWordCount();

    /**
     * Return list of results for the test.
     * @return List of result.
     * @throws IllegalStateException if test doesn't end.
     */
    List<VocabularyTestResult> getResult() throws IllegalStateException;

    /**
     * Return number of seconds for one word.
     * @return number of seconds for one word.
     */
    int getTimeForWord();
}
