package vocabularyup.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import vocabularyup.exception.VocabularyModelException;
import vocabularyup.model.xml.Article;
import vocabularyup.model.xml.Vocabulary;

/**
 * Main class for prepare and process users's test.<br/>
 * Class choose words from minimum rating to high.<br/>
 * If user's answer is right than rating of this word increments, else decrement.
 * To begin test, call method {@code start()}. For end test, call method {@code end()}.
 * To get word, call method {@code getWord()}. When user answer, you must call {@code setAnswer()}.
 * 
 * //TODO: need statistics
 * @author dooman
 */
public class VocabularyTest {
    private static final Logger log = Logger.getLogger(VocabularyTest.class.getName());

    private Vocabulary vocabulary;
    private int wordCount;
    private List<Article> testArticles;
    private boolean inProcess = false;

    private Article currentArticle = null;
    private Map<Article, String> answers = null;
    

    public VocabularyTest(Vocabulary vocabulary, int wordCount) {
        this.vocabulary = vocabulary;
        this.wordCount = wordCount;
        testArticles = new ArrayList<Article>();
        log.fine("Create new test vocabulary=[" + vocabulary.getName() + "], wordCount=[" + wordCount + "]");
    }

    /**
     * Start test. Prepare words.
     */
    public void start() {
        log.fine("Start test.");
        testArticles = new LinkedList<Article>();
        answers = new HashMap<Article, String>();
        prepareWords();
        inProcess = true;
    }

    /**
     * Get the next word for test. Before get next word, you must set answer to current({@link VocabularyTest#setAnswer(java.lang.String)})
     * @return the next word for test.
     * @throws java.lang.IllegalStateException if test wasn't begin or if no more words.
     */
    public String getWord() throws IllegalStateException {
        if (!inProcess) {
            throw new IllegalStateException("Test isn't in progress, you must call start() to begin it.");
        }
        if (testArticles.size() == 0) {
            throw new IllegalStateException("No more word for test.");
        }

        Random rand = new Random(System.currentTimeMillis());
        int wordIndex = rand.nextInt(testArticles.size());

        currentArticle = testArticles.get(wordIndex);
        testArticles.remove(currentArticle);

        log.fine("Get next article from test [" + currentArticle.getSource() + "]");
        return currentArticle.getSource();
    }

    /**
     * Set user's answer for current word.
     * @param answer user's answer.
     */
    public void setAnswer(String answer) {
        log.fine("Set answer [" + answer + "]");
        answers.put(currentArticle, answer);
    }

    /**
     * End test. Create test's result.
     * @return List of result for each word.
     */
    public List<VocabularyTestResult> end() {
        log.fine("End test, creating result");
        List<VocabularyTestResult> results = new LinkedList<VocabularyTestResult>();
        for (Map.Entry<Article, String> answer : answers.entrySet())  {
            results.add(new VocabularyTestResult(answer.getKey(), answer.getValue()));
        }
        testArticles.clear();
        testArticles = null;
        answers.clear();
        answers = null;

        for (VocabularyTestResult r : results) {
            Article article = r.getArticle();
            Integer rating = Integer.valueOf(article.getRating());
            r.getArticle().setRating(String.valueOf(rating + (r.isResult() ? 1 : -1)));
            log.fine("Set new rating to article [" + article.getSource() +
                    "], new: [" + article.getRating() + "] old: [" + rating + "]");
        }

        try {
            vocabulary.save();
        } catch (VocabularyModelException e) {
            log.log(Level.SEVERE, "Error saving test results", e);
        }

        return results;
    }

    /**
     * Has more words in test or not.
     * @return {@code true} if test has words and {@code != null}, else {@code false}.
     */
    public boolean hasMoreWords() {
        return testArticles != null && testArticles.size() != 0;
    }

    /**
     * Call before test begins.<br/>
     * Prepare words for testing.<br/>
     * Select words with minimum ratings, if need add other words.
     */
    private void prepareWords() {
        List<Article> articles = vocabulary.getArticles();
        Map<Integer, List<Article>> ratingMap = new TreeMap<Integer, List<Article>>(); //need sorted map

        //sort all articles by rating
        for (Article a : articles) {
            if (a.getTranslates() == null || a.getTranslates().size() == 0) {
                log.info("Article [" + a.getSource() + "] hasn't translate and won't add to test.");
                continue;
            }
            Integer rating = a.getRating().isEmpty() ? 0 : Integer.valueOf(a.getRating());
            List<Article> ratingArticles = ratingMap.get(rating);
            if (ratingArticles == null) {
                ratingArticles = new ArrayList<Article>();
                ratingMap.put(rating, ratingArticles);
            }

            ratingArticles.add(a);
        }

        List<Article> allSortedArticles = new LinkedList<Article>();
        for (List<Article> a : ratingMap.values()) {
            allSortedArticles.addAll(a);
        }
        Iterator<Article> allSortedArticleIt = allSortedArticles.iterator();
        while (testArticles.size() < wordCount && allSortedArticleIt.hasNext()) {
            testArticles.add(allSortedArticleIt.next());
        }
        log.fine("Add [" + testArticles.size() + "] words to test");
    }

}
