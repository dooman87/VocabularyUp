/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vocabularyup.VocabularyApp;
import vocabularyup.exception.VocabularyAlreadyExistException;
import vocabularyup.exception.VocabularyModelException;
import vocabularyup.exception.VocabularyNotFoundException;

/**
 * 
 * @author 111
 */
public class XMLVocabularyTest extends TestCase {
    private static final Logger log = Logger.getLogger(XMLVocabularyTest.class.getName());

    private Vocabulary testVocabulary;
    private int expectedArticleCount = 1;
    private String expectedSource = "go";
    private List<String> expectedTranslates = Arrays.asList("идти", "ходить");
    private List<String> expectedExamples = Arrays.asList("go to school", "go to smth.");


    @Before
    @Override
    public void setUp() {
        System.setProperty("java.util.logging.config.file", "test/logging.properties");
        try {
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration();
            Vocabulary voc = Vocabulary.newVocabulary("test");
            voc.addArticle(expectedSource, expectedTranslates, expectedExamples);
            voc.save();

            File vocFile = new File(VocabularyApp.APP_HOME_DIR + "/test.xml");
            testVocabulary = Vocabulary.loadVocabulary(vocFile);
            log.fine("Load: \n" + buildLog(testVocabulary));
        } catch (Exception ex) {
            System.err.println("Cann't configure logging");
            ex.printStackTrace();
        }
    }

    @After
    @Override
    protected void tearDown() throws Exception {
        log.fine("Delete test vocabulary");
        File vocFile = new File(VocabularyApp.APP_HOME_DIR + "/test.xml");
        vocFile.delete();
    }



    /**
     * create vocabulary;
     * save vocabulary;
     * load vocabulary;
     * compare created and loaded vocabularies.
     */
    @Test
    public void testGeneral() {
        try {
            log.fine("==============BEGIN testGeneral()==============");
            File vocFile = new File(VocabularyApp.APP_HOME_DIR + "/test.xml");
            Vocabulary loadedVoc = Vocabulary.loadVocabulary(vocFile);
            Assert.assertEquals(testVocabulary.getName(), loadedVoc.getName());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testAddArticles() {
        try {
            log.fine("==============BEGIN testAddArticles()==============");
            List<Article> articles = testVocabulary.getArticles();
            Assert.assertEquals(expectedArticleCount, articles.size());

            Article a = articles.get(0);
            Assert.assertEquals(expectedSource, a.getSource());
            //equals translates
            checkStringLists(expectedTranslates, a.getTranslates());
            //equals examples
            checkStringLists(expectedExamples, a.getExamples());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testChangeArticle() {
        try {
            log.fine("==============BEGIN testChangeArticle()==============");
            Article article = testVocabulary.getArticles().get(0);
            article.setSource(expectedSource);
            article.setExamples(expectedExamples);
            article.setTranslates(expectedTranslates);

            //Change articles
            testVocabulary.save();

            log.fine(buildLog(testVocabulary));

            Article a = testVocabulary.getArticles().get(0);
            Assert.assertEquals(expectedSource, a.getSource());
            //equals translates
            checkStringLists(expectedTranslates, a.getTranslates());
            //equals examples
            checkStringLists(expectedExamples, a.getExamples());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }


    private void checkStringLists(List<String> expectedList, List<String> list) {
        Assert.assertEquals(expectedList.size(), list.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(expectedList.get(i), list.get(i));
        }
    }

    private String buildLog(Vocabulary voc) {
        StringBuilder builder = new StringBuilder();
        builder.append("Vocabulary: [").append(voc).append("]\n");
        for (Article a : voc.getArticles()) {
            builder.append("\t[").append(a).append("]");
            builder.append("\n\t\t");
            for (String t : a.getTranslates()) {
                builder.append("[").append(t).append("] ");
            }
            builder.append("\n\t\t");
            for (String e : a.getExamples()) {
                builder.append("[").append(e).append("] ");
            }
        }

        return builder.toString();
    }
}
