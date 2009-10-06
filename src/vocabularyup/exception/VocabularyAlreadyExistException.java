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

package vocabularyup.exception;

/**
 * Словарь с таким именем уже существует.
 * 
 * @author 111
 */
public class VocabularyAlreadyExistException extends Exception {

    public VocabularyAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public VocabularyAlreadyExistException(String vocabularyName, Throwable cause) {
        super(getErrorMessage(vocabularyName), cause);
    }

    public VocabularyAlreadyExistException(String vocabularyName) {
        super(getErrorMessage(vocabularyName));
    }

    private static String getErrorMessage(String vocabularyName) {
        return "Vocabulary \"" + vocabularyName + "\" already exists";
    }
}
