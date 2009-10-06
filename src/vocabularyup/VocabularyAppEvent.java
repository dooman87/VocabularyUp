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

import java.util.Arrays;
import java.util.List;
import vocabularyup.model.xml.Article;
import vocabularyup.model.xml.Vocabulary;

/**
 * Event that represents all modifications of states in application.<br/>
 * Constructs events with static methods.<br/>
 * Available Change Events:<br/>
 * -adding new vocabulary
 * -current vocabulary changed<br/>
 * -current search result changed<br/>
 * -current selected article change<br/>
 * @author dooman
 */
public class VocabularyAppEvent {
    public static enum EventType {
        VOCABULARY_ADDED,
        CURRENT_VOCABULARY_CHANGED, CURRENT_ARTICLES_CHANGED, 
        CURRENT_SELECTED_ARTICLE_CHANGE, 
        UNDEFINED
    }

    private EventType type = EventType.UNDEFINED;
    private Vocabulary vocabulary;
    private List<Article> articles;

    public VocabularyAppEvent(EventType type, Vocabulary vocabulary, List<Article> articles) {
        this.type = type;
        this.vocabulary = vocabulary;
        this.articles = articles;
    }

    public static VocabularyAppEvent currentVocabularyChange(Vocabulary vocabulary) {
        return new VocabularyAppEvent(EventType.CURRENT_VOCABULARY_CHANGED, vocabulary, null);
    }

    public static VocabularyAppEvent currentArticlesChange(List<Article> articles) {
        return new VocabularyAppEvent(EventType.CURRENT_ARTICLES_CHANGED, null, articles);
    }

    public static VocabularyAppEvent vocabularyAdd(Vocabulary vocabulary) {
        return new VocabularyAppEvent(EventType.VOCABULARY_ADDED, vocabulary, null);
    }

    public static VocabularyAppEvent selectedArticleChange(Article a) {
        return new VocabularyAppEvent(EventType.CURRENT_SELECTED_ARTICLE_CHANGE, null, Arrays.asList(a));
    }

    public Article getArticle() {
        return articles.get(0);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public EventType getType() {
        return type;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }
}
