package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.data.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataStoreService {
    public HashMap<String, String> serverProperties;
    private static DataStoreService instance = null;
    private final LoggerService logger = LoggerService.getInstance();
    private final PluginIO pluginIO;
    private String DATA_PATH;
    public Data data;

    private DataStoreService() {
        readServerProperties();
        this.DATA_PATH = serverProperties.get("level-name") + "/ee_data.json";
        pluginIO = new PluginIO();
        data = pluginIO.readFile(DATA_PATH, Data.class);
        data = data == null ? new Data() : data;
        data.init();
    }

    public static DataStoreService getInstance() {
        if (instance == null) {
            synchronized (DataStoreService.class) {
                if (instance == null) {
                    instance = new DataStoreService();
                }
            }
        }
        return instance;
    }

    public void writeData() {
        logger.info("Writing data file to: " + DATA_PATH);
        pluginIO.writeFile(DATA_PATH, data);
    }

    private void readServerProperties() {
        serverProperties = new HashMap<String, String>();
        try {
            List<String> records = Files.readAllLines(Paths.get("server.properties"));
            for (String rec : records) {
                String[] temp = rec.split("=");
                if (temp.length == 2)
                    serverProperties.put(temp[0], temp[1]);
                else
                    serverProperties.put(temp[0], "");
            }
        } catch (IOException e) {
            System.err.println("Error reading server properties");
            e.printStackTrace();
        }
    }

}
