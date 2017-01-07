import com.istavrak.vocabrecommender.core.searcher.LODStatsSearcher;
import com.istavrak.vocabrecommender.core.searcher.LOVSearcher;
import com.istavrak.vocabrecommender.core.searcher.VocabCCSearcher;
import com.istavrak.vocabrecommender.model.lodstats.Results;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SearchersTest {

    @Test
    public void testLODStatsInvoking() {
        LODStatsSearcher searcher = new LODStatsSearcher();
        Results results = searcher.searchFor("room");

        assertFalse(results == null);
        assertTrue(!results.getTerms().isEmpty());
    }

    @Test
    public void testLOVInvoking() {
        LOVSearcher searcher = new LOVSearcher();
        com.istavrak.vocabrecommender.model.lov.Results results = searcher.searchLOVFor("Person");

        assertTrue(!results.getResults().isEmpty());
        assertTrue(!results.getQueryString().isEmpty());
    }

    @Test
    public void testVocabInvoking() {
        VocabCCSearcher searcher = new VocabCCSearcher();
        com.istavrak.vocabrecommender.model.vocabcc.Results results = searcher.searchVocabccFor("hotel");

        assertFalse(results == null);
        assertTrue(!results.getDescriptions().isEmpty());
    }
}
