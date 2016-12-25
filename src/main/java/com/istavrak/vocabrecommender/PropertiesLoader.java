package com.istavrak.vocabrecommender;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public final static PropertiesLoader INSTANCE = new PropertiesLoader();
    private static Properties properties = null;
    protected PropertiesLoader() {
        // Exists only to defeat instantiation.
    }

    public String getProperty(String name) {
        String propValue = "";

        if (properties == null) {
            loader();
        }
        propValue = properties.getProperty(name);
        return propValue;
    }

    public void loader() {
        properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(new File("target/classes/com/istavrak/vocabrecommender/app.properties"));
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
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
}
