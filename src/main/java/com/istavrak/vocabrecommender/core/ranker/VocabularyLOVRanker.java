package com.istavrak.vocabrecommender.core.ranker;


import com.istavrak.vocabrecommender.model.vsearch.Rank;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabularyLOVRanker implements VocabularyRanker {
    public final static VocabularyLOVRanker INSTANCE = new VocabularyLOVRanker();

    private static final Logger logger = Logger.getLogger(VocabularyLOVRanker.class.getName());
    private static HashMap<String, BigDecimal> vocabFinalScore = new HashMap<>();

    protected VocabularyLOVRanker() {
        // Exists only to defeat instantiation.
    }

    @Override
    public Rank getVocabularyRanking(String vocabURI) {
        if (vocabFinalScore.isEmpty()) {
            loadScores();
        }
        return new Rank(vocabFinalScore.get(vocabURI).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    private void loadScores() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data/lovVocabs_finalscore_20170122.csv"))));
            String line;
            while((line = in.readLine()) != null) {
                String[] vocabDetails = line.split(",");
                vocabFinalScore.put(vocabDetails[0], new BigDecimal(vocabDetails[1]));
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to read the aggregated vocabulary data, exception: " + e.getMessage());
        }
    }

}
