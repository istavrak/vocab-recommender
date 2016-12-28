import com.istavrak.vocabrecommender.core.searcher.LOVSearcher;
import com.istavrak.vocabrecommender.model.lov.LOVResults;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class LOVSearcherTest {
    private static final String LOV_URL = "http://lov.okfn.org/dataset/lov/api/v2/term/search?q=Person&type=class";

    @Test
    public void testLOVInvoking() {
        LOVSearcher searcher = new LOVSearcher();
        LOVResults results = searcher.invokeViaHttp(LOV_URL);

        assertTrue(!results.getResults().isEmpty());
        assertTrue(!results.getQueryString().isEmpty());
    }
}
