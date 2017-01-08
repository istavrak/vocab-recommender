import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabulyLOVRanker {
    private static final Logger logger = Logger.getLogger(VocabulyLOVRanker.class.getName());
    private static List<Vocab> lovVocabs;

    private static void loader() {
        InputStream input = null;
        try {
            input = new FileInputStream(new File("out/production/lov-ranker/lovVocabs_20170108.json"));
            ObjectMapper mapper = new ObjectMapper();
            Vocab[] myObjects = mapper.readValue(input, Vocab[].class);
            lovVocabs = Arrays.asList(myObjects);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to use the vocabularies JSON file, exception: " + e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        loader();
    }

}
