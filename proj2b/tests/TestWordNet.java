import org.junit.jupiter.api.Test;
import wordnet.WordnetMap;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class TestWordNet {
	@Test
	public void testHyponymsSimple(){
		WordnetMap wn=new WordnetMap("./data/wordnet/synsets11.txt","./data/wordnet/hyponyms11.txt");
		assertThat(wn.getHyponysm("antihistamine")).isEqualTo(Set.of("antihistamine","actifed"));
	}

	@Test
	public void testHyponymsSimple2(){
		WordnetMap wn=new WordnetMap("./data/wordnet/synsets16.txt","./data/wordnet/hyponyms16.txt");
		assertThat(wn.getHyponysm("change")).isEqualTo(Set.of("alteration","change","demotion","increase","jump","leap","modification","saltation","transition","variation"));

	}

}
