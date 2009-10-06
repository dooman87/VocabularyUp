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
