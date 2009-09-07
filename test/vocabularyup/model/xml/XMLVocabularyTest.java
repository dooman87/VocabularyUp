/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.model.xml;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import vocabularyup.VocabularyApp;

/**
 * 
 * @author 111
 */
public class XMLVocabularyTest extends TestCase {
    /**
     * create vocabulary;
     * save vocabulary;
     * load vocabulary;
     * compare created and loaded vocabularies.
     */
    @Test
    public void testGeneral() {
        try {
            //delete if exists
            File vocFile = new File(VocabularyApp.APP_HOME_DIR + "/test.xml");
            vocFile.delete();

            Vocabulary voc = Vocabulary.newVocabulary("test");
            voc.save();
            Vocabulary loadedVoc = Vocabulary.loadVocabulary(vocFile);
            Assert.assertEquals(voc.getName(), loadedVoc.getName());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testArticles() {
        try {
            File vocFile = new File(VocabularyApp.APP_HOME_DIR + "/test.xml");
            vocFile.delete();

            Vocabulary voc = Vocabulary.newVocabulary("test");
            int expectedArticleCount = 1;
            String expectedSource = "go";
            List<String> expectedTranslates = Arrays.asList("идти", "ходить");
            List<String> expectedExamples = Arrays.asList("go to school", "go to smth.");
            voc.addArticle(expectedSource, expectedTranslates, expectedExamples);
            voc.save();

            Vocabulary loadedVoc = Vocabulary.loadVocabulary(vocFile);
            List<Article> articles = loadedVoc.getArticles();
            Assert.assertEquals(expectedArticleCount, articles.size());

            Article a = articles.get(0);
            Assert.assertEquals(expectedSource, a.getSource());

            //equals translates
            List<String> translates = a.getTranslates();
            Assert.assertEquals(expectedTranslates.size(), translates.size());
            for (int i = 0; i < expectedTranslates.size(); i++) {
                Assert.assertEquals(expectedTranslates.get(i), translates.get(i));
            }

            //equals examples
            List<String> examples = a.getExamples();
            Assert.assertEquals(expectedExamples.size(), examples.size());
            for (int i = 0; i < expectedExamples.size(); i++) {
                Assert.assertEquals(expectedExamples.get(i), examples.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
