package com.istavrak.vocabrecommender.evaluator;

import com.istavrak.vocabrecommender.handler.RecommendationHandler;
import com.istavrak.vocabrecommender.model.RecommendationResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Evaluator {
    private static final String DATA_FOLDER = "data/evaluation/";
    private static final Logger logger = Logger.getLogger(Evaluator.class.getName());

    public static void main(String[] args) {
        RecommendationHandler handler = new RecommendationHandler();

        List<EvaluationEntry> evaluationEntries = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                    new File(DATA_FOLDER + "webpages.csv"))));
            String line;
            while((line = in.readLine()) != null) {
                String[] entryDetails = line.split(",");
                EvaluationEntry evaluationEntry =new EvaluationEntry(entryDetails[0], entryDetails[1], entryDetails[2]);
                evaluationEntries.add(evaluationEntry);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to read the aggregated vocabulary data, exception: " + e.getMessage());
        }
        for (EvaluationEntry entry : evaluationEntries) {
            String body = extractBody(entry.getFilename());
            RecommendationResponse response = handler.generateRecommendationsFor(body, entry.getUrl(), true);
            //TODO consume the results in the comparison
        }
    }

    private static String extractBody(String filename) {
        try {
            return new Scanner(new File(DATA_FOLDER + filename)).useDelimiter("\\A").next();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to parse from file the body of the page, exception: " + e.getMessage());
        }
        return null;
    }
}
