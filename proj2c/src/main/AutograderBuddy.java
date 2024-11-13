package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        NGramMap nGramMap = new NGramMap(wordFile, countFile);
        HyponymsHandler hh = new HyponymsHandler(synsetFile,hyponymFile,nGramMap);
        return hh;
    }
}
