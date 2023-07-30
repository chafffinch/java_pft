package ru.stqa.pft.rest.appmanager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {
    private final Properties properties;
    private JsonHelper jsonHelper;
    public ApplicationManager(){
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties", target))));
    }
    public void stop() {
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public JsonHelper json(){
        if (jsonHelper == null){
            jsonHelper = new JsonHelper(this);
        }
        return  jsonHelper;
    }
}
