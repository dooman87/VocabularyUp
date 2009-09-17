/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
