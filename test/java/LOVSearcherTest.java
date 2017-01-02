import com.istavrak.vocabrecommender.core.searcher.LOVSearcher;
import com.istavrak.vocabrecommender.model.lov.Results;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class LOVSearcherTest {
    @Test
    public void testLOVInvoking() {
        LOVSearcher searcher = new LOVSearcher();
        Results results = searcher.searchLOVFor("Person");

        assertTrue(!results.getResults().isEmpty());
        assertTrue(!results.getQueryString().isEmpty());
    }
}
