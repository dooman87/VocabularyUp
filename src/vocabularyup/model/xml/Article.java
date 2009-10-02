/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vocabularyup.model.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vocabularyup.exception.ArticleModelException;
import vocabularyup.util.dom.DomCheckHelper;
import vocabularyup.util.dom.DomCheckingException;
import vocabularyup.util.dom.DomHelper;

/**
 * Class describes word in vocabulary and it's translation.
 * @author Pokidov.Dmitry
 */
public class Article {
    private static final Logger log = Logger.getLogger(Article.class.getName());

    public static final  String ARTICLE_ELEMENT            = "article";
    private static final String ARTICAL_SOURCE_ELEMENT     = "source";
    private static final String ARTICLE_TRANSLATES_ELEMENT = "translates";
    private static final String ARTICLE_TRANSLATE_ELEMENT  = "translate";
    private static final String ARTICLE_EXAMPLES_ELEMENT   = "examples";
    private static final String ARTICLE_EXAMPLE_ELEMENT    = "example";
    private static final String ARTICLE_RATING_ELEMENT     = "rating";

    private Document document;
    private Element rootElement;
    private Element sourceElement;
    private Element translatesElement;
    private Element examplesElement;
    private Element ratingElement;

    /**
     * Create new article.
     * @param document DOM document where article should be placed.
     * @param source source word.
     * @param translates translates for word.
     */
    public Article(Document document, String source, List<String> translates) {
        this.document = document;
        this.rootElement = document.createElement(ARTICLE_ELEMENT);
        //translateElements = new ArrayList<Element>();
        //exampleElements = new ArrayList<Element>();
        sourceElement = document.createElement(ARTICAL_SOURCE_ELEMENT);
        sourceElement.setTextContent(source);
        rootElement.appendChild(sourceElement);

        addTranslates(translates);
        rootElement.appendChild(translatesElement);

        examplesElement = document.createElement(ARTICLE_EXAMPLES_ELEMENT);
        rootElement.appendChild(examplesElement);

        ratingElement = document.createElement(ARTICLE_RATING_ELEMENT);
        rootElement.appendChild(ratingElement);
    }

    private Article(Document document, Element rootElement) throws DomCheckingException {
        this.document = document;
        this.rootElement = rootElement;

        //reuired elements
        this.sourceElement = DomCheckHelper.getElementsByTagName(rootElement, ARTICAL_SOURCE_ELEMENT, 1, true).get(0);

        this.translatesElement = DomCheckHelper.getElementsByTagName(rootElement, ARTICLE_TRANSLATES_ELEMENT, 1, true).get(0);
        //this.translateElements = DomCheckHelper.getElementsByTagName(translatesElement, ARTICLE_TRANSLATE_ELEMENT, 0, false);

        //Not required elements
        try {
            this.examplesElement = DomCheckHelper.getElementsByTagName(rootElement, ARTICLE_EXAMPLES_ELEMENT, 1, true).get(0);
            //this.exampleElements = DomCheckHelper.getElementsByTagName(examplesElement, ARTICLE_EXAMPLE_ELEMENT, 0, false);
        } catch (DomCheckingException e) {
            log.log(Level.INFO, "No examples in article[" + e.getMessage() + "]");
        }

        try {
            this.ratingElement = DomCheckHelper.getElementsByTagName(rootElement, ARTICLE_RATING_ELEMENT, 1, true).get(0);
        } catch (DomCheckingException e) {
            log.log(Level.INFO, "No rating for article[" + e.getMessage() + "]");
        }
    }

    public String getSource() {
        return sourceElement.getTextContent();
    }

    public void setSource(String newSource) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Set new source for [" + getSource() + "]: " + newSource);
        }
        sourceElement.setTextContent(newSource);
    }

    public void addTranslates(List<String> translates) {
        for (String t : translates) {
            addTranslate(t);
        }
    }

    public void addTranslate(String translate) {
        if (translatesElement == null) {
            translatesElement = document.createElement(ARTICLE_TRANSLATES_ELEMENT);
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine("Add new translate for [" + getSource() + "]: [" + translate + "]");
        }
        Element newTranslateEl = document.createElement(ARTICLE_TRANSLATE_ELEMENT);
        newTranslateEl.setTextContent(translate);
        translatesElement.appendChild(newTranslateEl);
    }

    public List<String> getTranslates() {
        List<String> result = new ArrayList<String>();
        try {
            List<Element> translatesEl = DomCheckHelper.getElementsByTagName(translatesElement, ARTICLE_TRANSLATE_ELEMENT, 0, false);
            for (Element el : translatesEl) {
                String content = el.getTextContent();
                if (!content.isEmpty()) {
                    result.add(content);
                }
            }
        } catch (DomCheckingException e) {
            log.log(Level.WARNING, "No translates in article", e);
        }

        return result;
    }

    public void setTranslates(List<String> newTranslates) {
        if (translatesElement != null) {
            if (log.isLoggable(Level.FINE)) {
                log.fine("Remove all translates from [" + getSource() + "]");
            }
            DomHelper.removeChildren(translatesElement);
        }
        addTranslates(newTranslates);
    }

    /**
     * Add example with this word.
     * @param example example that should be added to this word.
     */
    public void addExample(String example) {
        if (examplesElement == null) {
            examplesElement = document.createElement(ARTICLE_EXAMPLES_ELEMENT);
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine("Add new example for word [" + getSource() + "]: [" + example + "]");
        }
        Element el = document.createElement(ARTICLE_EXAMPLE_ELEMENT);
        el.setTextContent(example);
        examplesElement.appendChild(el);
    }

    /**
     * Add all examples from list.
     * @param examples list of examples that will be added.
     */
    public void addExamples(List<String> examples) {
        for (String e : examples) {
            addExample(e);
        }
    }

    public List<String> getExamples() {
        List<String> result = new ArrayList<String>();
        try {
            List<Element> elements = DomCheckHelper.getElementsByTagName(examplesElement, ARTICLE_EXAMPLE_ELEMENT, 0, false);

            for (Element e : elements) {
                result.add(e.getTextContent());
            }
        } catch (DomCheckingException e) {
            log.log(Level.INFO, "No examples in article", e);
        }

        return result;
    }

    public void setExamples(List<String> newExamples) {
        if (examplesElement != null) {
            if (log.isLoggable(Level.FINE)) {
                log.fine("Remove all examples from [" + sourceElement.getTextContent() + "]");
            }
            DomHelper.removeChildren(examplesElement);
        }
        addExamples(newExamples);
    }

    /**
     * Set rating that calculates when test processed.
     * @param rating new rating for this word.
     */
    public void setRating(String rating) {
        ratingElement.setTextContent(rating);
    }

    public String getRating() {
        String content = ratingElement.getTextContent();
        return (content == null || content.isEmpty()) ? "0" : content;
    }

    /**
     * Return root DOM element of article..
     * @return complete DOM representation of current article.
     */
    public Element buildArticleElement() {
        return rootElement;
    }

    /**
     * load article from DOM model.
     * @param document document for new article.
     * @param element root element of article.
     * @return created article.
     * @throws vocabularyup.exception.ArticleModelException error in element namings or structure.
     */
    public static Article loadArticle(Document document, Element element) throws ArticleModelException {
        if (!element.getNodeName().equals(ARTICLE_ELEMENT)) {
            throw new ArticleModelException("Bad element name(" + element.getLocalName() + ", but expected " + ARTICLE_ELEMENT + ")");
        }
        try {
            return new Article(document, element);
        } catch(DomCheckingException e) {
            throw new ArticleModelException("Error building DOM model", e);
        }
    }

    @Override
    public String toString() {
        return getSource();
    }


}
