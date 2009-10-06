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
 * Throws, when user want add article that already exists.
 * @author Pokidov.Dmitry
 */
public class ArticleAlreadyExistException extends Exception {
    public ArticleAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public ArticleAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleAlreadyExistException(String message) {
        super(message);
    }
}
