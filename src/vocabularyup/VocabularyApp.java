package vocabularyup;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JFrame;
import vocabularyup.exception.ArticleAlreadyExistException;
import vocabularyup.exception.VocabularyAlreadyExistException;
import vocabularyup.exception.VocabularyModelException;
import vocabularyup.exception.VocabularyNotFoundException;
import vocabularyup.model.xml.Article;
import vocabularyup.model.xml.Vocabulary;
import vocabularyup.ui.EditArticleDialog;
import vocabularyup.ui.MainFrame;

/**
 * Main class of application.
 * Creating as singleton. {@code VocabularyApp} is a main controller of application.
 * @author 111
 */
public class VocabularyApp {
    private static final Logger log = Logger.getLogger(VocabularyApp.class.getName());

    public static final String APP_HOME_DIR=System.getProperty("user.home") + "/.vocabularyup";

    public static final String ADD_ARTICLE_APP_OPTION = "--addArticle";

    private static VocabularyApp instance = null;

    private MainFrame mainFrame;
    private Map<String, Vocabulary> vocabularies = new HashMap<String, Vocabulary>();
    private Vocabulary currentVocabulary;
    private Article selectedArticle;
    private String  currentFilter = "";

    private List<VocabularyAppListener> listeners = new ArrayList<VocabularyAppListener>();

    private VocabularyApp() {}

    public List<Vocabulary> getVocabularies() {
        return new ArrayList(vocabularies.values());
    }

    public void createVocabulary(String vocabularyName) throws VocabularyAlreadyExistException, VocabularyModelException {
            File vocFile = new File(APP_HOME_DIR + "/" + vocabularyName + ".xml");
            if (vocFile.exists()) {
                throw new VocabularyAlreadyExistException(vocabularyName);
            }

            Vocabulary voc = Vocabulary.newVocabulary(vocabularyName);
            voc.save();
            vocabularies.put(voc.getName(), voc);
            fireAddVocabulary(voc);
    }

    /**
     * Set current vocabulary.
     * @param vocabularyName name of vocabulary that setting as current.
     * @throws vocabularyup.exception.VocabularyNotFoundException if vocabulary 
     *   with {@code vocabularyName} does not exists.
     */
    public void setCurrentVocabulary(String vocabularyName) throws VocabularyNotFoundException {
        Vocabulary voc = vocabularies.get(vocabularyName);
        if (voc == null) {
            throw new VocabularyNotFoundException(vocabularyName);
        }
        currentVocabulary = voc;
        fireCurrentVocabularyChange(voc);
        search(currentFilter);
    }

    /**
     * Set current vocabulary.
     * Execute {@code setCurrentVocabulary(vocabulary.getName)}
     * @param vocabulary new current vocabulary.
     * @throws vocabularyup.exception.VocabularyNotFoundException if vocabulary does not exists.
     * @throws IllegalArgumentException if {@code vocabulary == null}
     */
    public void setCurrentVocabulary(Vocabulary vocabulary) throws VocabularyNotFoundException, IllegalArgumentException {
        if (vocabulary == null) {
            throw new IllegalArgumentException("vocabulary must not be [null]");
        }
        setCurrentVocabulary(vocabulary.getName());
    }

    public Vocabulary getCurrentVocabulary() {
        return currentVocabulary;
    }

    /**
     * Add new article to current vocabulary.
     * @param source source word for article.
     * @param translates translates for article.
     * @param examples examples for article.
     * @throws vocabularyup.exception.VocabularyModelException error while saving vocabulary.
     * @throws VocabularyNotFoundException when setting vocabulary as current.
     * @throws ArticleAlreadyExistException if article with the specified source already exists
     */
    public void addArticle(String source, List<String> translates, List<String> examples)
            throws VocabularyModelException, VocabularyNotFoundException, ArticleAlreadyExistException {
        if (currentVocabulary != null) {
            currentVocabulary.addArticle(source, translates, examples);
            currentVocabulary.save();
            setCurrentVocabulary(currentVocabulary);
        } else {
            throw new IllegalStateException("Current vocabulary is [null]");
        }
    }

    /**
     * Change exists article.
     * @param old article that must be modified.
     * @param source new source for the article.
     * @param translates new translates for the article.
     * @param examples new examples for the article.
     * @throws VocabularyModelException error while saving vocabulary.
     */
    public void changeArticle(Article old, String source, List<String> translates, List<String> examples) throws VocabularyModelException {
        if (currentVocabulary != null) {
            old.setSource(source);
            old.setTranslates(translates);
            old.setExamples(examples);
            currentVocabulary.save();
            fireSelectedArticleChange(old);
        }
    }

    /**
     * Search articles in current vocabulary.
     * This method send {@link ArticlesChangeEvent}.
     * @param filter article must matches th filter to be in result.
     * @return list of found articles or empty list.
     */
    public List<Article> search(String filter) {
        currentFilter = filter;

        log.info("Search in [" + currentVocabulary.getName() + "]. Filter: [" + filter +"]");
        List<Article> result = new ArrayList<Article>();
        if (currentVocabulary != null) {
            List<Article> articles = currentVocabulary.getArticles();
            for (Article a : articles) {
                if (a.getSource().startsWith(filter)) {
                    log.info("Add [" + a.getSource() + "] to current search result");
                    result.add(a);
                }
            }
        }

        fireCurrentArticlesChange(result);

        return result;
    }

    public Article getSelectedArticle() {
        return selectedArticle;
    }

    /**
     * Set selected article.
     * @param selectedArticle new selected article.
     */
    public void setSelectedArticle(Article selectedArticle) {
        log.fine("Set current article [" + (selectedArticle == null ? "null" : selectedArticle.getSource()) + "]");
        this.selectedArticle = selectedArticle;
        fireSelectedArticleChange(selectedArticle);
    }    

    /**
     * Add listener that will be fire when application states change.
     * @param listener listener that will be added, if {@code listener == null} nothing happens
     */
    public void addListener(VocabularyAppListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Remove {@code listener}.
     * @param listener listener for remove, if {@code listener == null} nothing happens
     */
    public void removeListener(VocabularyAppListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    protected void fireAddVocabulary(Vocabulary vocabulary) {
        VocabularyAppEvent e = VocabularyAppEvent.vocabularyAdd(vocabulary);
        for (VocabularyAppListener l : listeners) {
            l.addedVocabulary(e);
        }
    }

    protected void fireCurrentArticlesChange(List<Article> articles) {
        VocabularyAppEvent e = VocabularyAppEvent.currentArticlesChange(articles);
        for (VocabularyAppListener l : listeners) {
            l.currentArticlesChanges(e);
        }
    }

    protected void fireCurrentVocabularyChange(Vocabulary vocabulary) {
        VocabularyAppEvent e = VocabularyAppEvent.currentVocabularyChange(vocabulary);
        for (VocabularyAppListener l : listeners) {
            l.currentVocabularyChanged(e);
        }
    }
    

    protected void fireSelectedArticleChange(Article article) {
        VocabularyAppEvent e = VocabularyAppEvent.selectedArticleChange(article);
        for (VocabularyAppListener l : listeners) {
            l.selectedArticleChange(e);
        }
    }

    /**
     * Init application<br/>
     * Create service catalogs, load vocabularies.
     */
    private void init() {
        File appDir = new File(APP_HOME_DIR);
        if (!appDir.isDirectory()) {
            appDir.mkdirs();
        }
        try {
            for (File f : appDir.listFiles()) {
                Vocabulary voc = Vocabulary.loadVocabulary(f);
                vocabularies.put(voc.getName(), voc);
            }
        } catch (VocabularyModelException me) {
            log.log(Level.SEVERE, "Error while loading...", me);
        } catch (VocabularyNotFoundException ne) {
            log.log(Level.SEVERE, "Cann't find vocabulary...", ne);
        }
    }

    public static VocabularyApp getInstance() {
        if (instance == null) {
            instance = new VocabularyApp();
            instance.init();
        }

        return instance;
    }

    public void start(String[] args) {
        if (args.length == 1 &&
                args[0].equals(ADD_ARTICLE_APP_OPTION)) {
            //add words only
            EditArticleDialog dialog = new EditArticleDialog(null);
            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.pack();
            dialog.setVisible(true);
        } else {
            mainFrame = new MainFrame();
            mainFrame.setLocationByPlatform(true);
            mainFrame.pack();
            mainFrame.setVisible(true);
        }
    }

    public static void configureLog() {
        String LOG_CONFIG_PROPERTY = "java.util.logging.config.file";
        String DEFAULT_LOG_CONFIG = "conf/logger.properties";
        String logConfigFile = System.getProperty(LOG_CONFIG_PROPERTY);
        if (logConfigFile == null) {
            File defaultLogConfigFile = new File(DEFAULT_LOG_CONFIG);
            if (defaultLogConfigFile.exists()) {
                System.setProperty(LOG_CONFIG_PROPERTY, DEFAULT_LOG_CONFIG);
            } else {
                log.warning("Load default logging configuration");
            }
        }
        try {
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration();
            System.out.println("Logging configuration:");
            Enumeration<String> loggers = logManager.getLoggerNames();
            while (loggers.hasMoreElements()) {
                String loggerName = loggers.nextElement();
                Logger logger = logManager.getLogger(loggerName);
                System.out.println("\t [" + loggerName + "]");
                System.out.println("\t\tHandlers:");
                for (Handler h : logger.getHandlers()) {
                    System.out.println("\t\t\tClass: " + h.getClass().getName());
                    System.out.println("\t\t\tLevel: " + h.getLevel());
                    System.out.println("\t\t\tFormatter: " + h.getFormatter().getClass().getName());
                }
                System.out.println("\t\tLevel: " + logger.getLevel());
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error loading logging configuration", e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        configureLog();
        VocabularyApp app = VocabularyApp.getInstance();
        app.start(args);
    }
}
