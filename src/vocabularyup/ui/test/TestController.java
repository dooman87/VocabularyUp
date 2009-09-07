/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.ui.test;

import java.awt.Component;

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
}
