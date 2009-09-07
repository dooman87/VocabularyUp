/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup;

/**
 * Watcher for application states changes.
 * See detaile description in {@link VocabularyAppEvent}
 * @author dooman
 */
public interface VocabularyAppListener {
    /**
     * Will be invoce when current vocabulary change.
     * @param event event.
     */
    void currentVocabularyChanged(VocabularyAppEvent event);

    /**
     * Will be invoce when result of search change
     * @param event event.
     */    
    void currentArticlesChanges(VocabularyAppEvent event);

    /**
     * Will be invoce when new vocabulary create.
     * @param event event
     */
    void addedVocabulary(VocabularyAppEvent event);

    /**
     * Will be invoce when selected article change.
     * @param event event
     */
    void selectedArticleChange(VocabularyAppEvent event);
}
