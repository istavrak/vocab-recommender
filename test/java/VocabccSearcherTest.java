import com.istavrak.vocabrecommender.core.searcher.VocabCCSearcher;
import com.istavrak.vocabrecommender.model.vocabcc.Results;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class VocabccSearcherTest {

    @Test
    public void testVocabInvoking() {
        VocabCCSearcher searcher = new VocabCCSearcher();
        Results results = searcher.searchVocabccFor("hotel");

        assertFalse(results == null);
        assertTrue(!results.getDescriptions().isEmpty());
    }
}
