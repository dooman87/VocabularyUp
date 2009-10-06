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

package vocabularyup.test;

import vocabularyup.model.xml.Article;

/**
 * Result for one word in test.
 * @author dooman
 */
public class VocabularyTestResult {
    private Article article;
    private String userAnswer;
    private boolean result;

    public VocabularyTestResult(Article article, String userAnswer) {
        this.article = article;
        this.userAnswer = userAnswer;
        this.result = false;

        for (String t : article.getTranslates()) {
            if (t.equals(userAnswer)) {
                result = true;
                break;
            }
        }
    }

    public Article getArticle() {
        return article;
    }

    public boolean isResult() {
        return result;
    }

    public String getUserAnswer() {
        return userAnswer;
    }    
}
