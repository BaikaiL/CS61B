package main;

import browser.NgordnetQueryHandler;
import wordnet.WordnetMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        HyponymsHandler hh = new HyponymsHandler(synsetFile,hyponymFile);
        return hh;
    }
}
