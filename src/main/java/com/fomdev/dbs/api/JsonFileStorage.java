package com.fomdev.dbs.api;

import com.fomdev.dbs.init.DataStorager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Stores JSON data to the given file using {@link Gson}
 * WARNING: every {@link JsonFileStorage#append(Map)} will
 * cause the JSON file to be reformatted, so, remember to
 * do the caching work first, or, you can use {@link JsonFileCache}
 *
 * @author fomdev
 * @since 0.0.1
 */
public class JsonFileStorage {
    private       File   file;
    private final Gson   gson;
    private final String path;

    /**
     * Initializes the JSON file storage.
     * Will automatically build the file path by
     * the given {@link NamespacedKey} and cast
     * it to a json string, and will create the
     * {@link File} to read and write json. Also,
     * it will create a {@link Gson} instance by
     * using {@link GsonBuilder#create()}.
     *
     * @param path The path of the json file. The namespace should be the folder and the key should be the file name (without the suffix ".json")
     */
    public JsonFileStorage(NamespacedKey path) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.path = path.getNamespace() + "/" + path.getKey() + ".json";
        getFile();
    }

    /**
     * Just simplifies the step of initializing a new {@link NamespacedKey},
     * nothing more than {@link JsonFileStorage#JsonFileStorage(NamespacedKey)}
     *
     * @see JsonFileStorage
     * @param path The folder of the file
     * @param file The file name of the file (without the suffix ".json")
     */
    public JsonFileStorage(String path, String file) {
         this(new NamespacedKey(path, file));
    }

    /**
     * Directly loads the config from a file
     *
     * @param file The file to load
     * @since 1.0.2
     */
    public JsonFileStorage(File file) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.path = file.getPath();
        this.file = file;
    }

    /**
     * Will reformat the json file and append a completely new Map
     * object into it.
     *
     * @param data The map data to be written in the Json file
     * @return The class itself, in order to make coding json files in streams
     */
    public JsonFileStorage append(Map<String, Object> data) {
        try (FileWriter writer = new FileWriter(file)){
            gson.toJson(data, writer);
        } catch (IOException e) {
            DataStorager.logger.log(Level.SEVERE, e.getMessage() + e.getCause());
        }

        return this;
    }

    /**
     * WARNING: DONT USE THIS
     *
     * @see JsonFileStorage#append(Map)
     * @param key The key of the map
     * @param value The value of the map
     * @return The class itself, in order to make coding json files in streams
     */
    @SuppressWarnings("DANGEROUS, WILL REFORMAT THE WHOLE JSON AND REMOVE EVERYTHING IN IT")
    public JsonFileStorage append(String key, Object value) {
        Map<String, Object> data = new HashMap<>();
        data.put(key, value);
        return append(data);
    }

    /**
     * Gets the Object from the given path of the json
     *
     * @param id The storage path of the object
     * @return The object read from the json, or null if error occurred
     */
    @Nullable
    public Object get(String id) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(id, JsonObject.class);
        } catch (IOException e) {
            DataStorager.logger.log(Level.SEVERE, e.getMessage() + e.getCause());
            return null;
        }
    }

    /**
     * Will cast the json file into a Map object
     *
     * @return The cast json file
     */
    public Map<String, Object> get() {
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type map = new TypeToken<Map<String, Object>>(){}.getType();

            return gson.fromJson(reader, map);
        } catch (IOException e) {
            DataStorager.logger.log(Level.SEVERE, e.getMessage() + e.getCause());
            return new HashMap<>();
        }
    }

    /**
     * DELETES FILE, DANGEROUS
     *
     * @since 0.0.3
     */
    public void removeCacheEntry() {
        file.delete();
    }

    /**
     * ...
     * @return ...
     */
    private File getFile() {
        file = new File(getFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                DataStorager.logger.log(Level.SEVERE, e.getMessage() + e.getCause());
            }
        }
        return file;
    }

    /**
     * ...
     * @return ...
     */
    private String getFilePath() {
        return "./" + path;
    }
}