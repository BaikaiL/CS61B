import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.AutograderBuddy;
import ngrams.NGramMap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestOneWordKNot0Hyponyms {
    public static final String WORDS_FILE = "data/ngrams/frequency-EECS.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets-EECS.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms-EECS.txt";

    private static final String LARGE_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    @Test
    public void testActKNot0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("CS61A");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Add more unit tests (including edge case tests) here.
    @Test
    public void testActKNot0_2() {
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";

        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                wordFile, countFile, synsetFile, hyponymFile);
        List<String> words = List.of("food","cake");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[cake, cookie, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testLargeFileWithOneWord0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("cake");

        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[Belgian_waffle, Berlin_doughnut, Boston_cream_pie, Christmas_cake, Eccles_cake, French_fritter, French_pancake, Madeira_cake, Madeira_sponge, Sally_Lunn, Swiss_roll, Toll_House_cookie, Twinkie, Victoria_sandwich, Victoria_sponge, almond_cookie, almond_crescent, angel_cake, angel_food_cake, anise_cookie, apple_fritter, applesauce_cake, apricot_bar, baba, baba_au_rhum, babka, bar, battercake, beignet, birthday_cake, biscuit, bismark, blini, blintz, blintze, bliny, brandysnap, bridecake, brownie, buckwheat_cake, butter_cookie, buttermilk_pancake, cake, cheesecake, chiffon_cake, chocolate_cake, chocolate_chip_cookie, coconut_cake, coconut_macaroon, codfish_ball, codfish_cake, coffee_cake, coffee_ring, coffeecake, cookie, cooky, corn_fritter, crape, crepe, crepe_Suzette, cruller, crumb_cake, crumpet, cupcake, date_bar, devil's_food, devil's_food_cake, dog_biscuit, donut, doughboy, doughnut, fastnacht, fish_ball, fish_cake, flannel-cake, flannel_cake, flapcake, flapjack, fortune_cookie, friedcake, fritter, fruit_bar, fruitcake, gateau, genoise, german_pancake, ginger_nut, ginger_snap, gingerbread, gingerbread_man, gingersnap, granola_bar, griddlecake, honey_cake, hot_cake, hotcake, jelly_doughnut, jellyroll, jumbal, jumble, kiss, ladyfinger, latke, layer_cake, macaroon, marble_cake, molasses_cookie, oatmeal_cookie, oreo, oreo_cookie, pancake, patty, petit_four, pfannkuchen, potato_pancake, pound_cake, prune_cake, raised_doughnut, raisin-nut_cookie, raisin_cookie, ratafia, ratafia_biscuit, refrigerator_cookie, rock_cake, rum_baba, savarin, seed_cake, seedcake, shortbread, shortbread_cookie, simnel, sinker, skillet_cake, snap, spice_cake, spice_cookie, sponge_cake, sugar_cookie, tablet, tea_biscuit, teacake, torte, tortilla, tostada, twister, upside-down_cake, wafer, waffle, wedding_cake, white_cake]";
        assertThat(actual).isEqualTo(expected);

        List<String> words2 = List.of("ai");

        NgordnetQuery nq2 = new NgordnetQuery(words2, 2000, 2020, 0, NgordnetQueryType.HYPONYMS);
        String actual2 = studentHandler.handle(nq2);
        String expected2 = "[Bradypus_tridactylus, ai, three-toed_sloth]";
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    public void testLargeFileWithOneWordNot0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("cake");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[bar, cake, kiss, snap, tablet]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testLargeFileWithTwoWordsNot0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("food", "cake");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[cake, cookie, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);
    }
}
