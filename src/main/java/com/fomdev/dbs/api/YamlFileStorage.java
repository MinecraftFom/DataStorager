package com.fomdev.dbs.api;

import com.fomdev.dbs.init.DataStorager;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

/**
 * Stores data to yml files using {@link YamlConfiguration},
 * though this may not reformat the whole file, this still
 * remains to be a big cost in memory, so use {@link YamlFileCache}
 * instead to reduce IO operation
 *
 * @author fomdev
 * @since 0.0.2
 */
public class YamlFileStorage {
    private       File   file;
    private final String path;

    /**
     * Initializes the yaml file
     *
     * @param path The path of the yaml file
     */
    public YamlFileStorage(NamespacedKey path) {
        this.path = path.getNamespace() + "/" + path.getKey() + ".yml";
        getFile();
    }

    /**
     * Same as {@link YamlFileStorage#YamlFileStorage(NamespacedKey)},
     * but provides a more simple api to fast create it
     *
     * @param path The path of the file
     * @param file The filename of the file (without the suffix ".yml")
     */
    public YamlFileStorage(String path, String file) {
        this(new NamespacedKey(path, file));
    }

    /**
     * Directly loads config from file
     *
     * @param file The file to load
     * @since 1.0.2
     */
    public YamlFileStorage(File file) {
        this.path = file.getPath();
        this.file = file;
    }

    /**
     * Appends data to the yaml fire (directly)
     *
     * @param key The key or path of the yaml file
     * @param object The value to store
     * @return ...
     */
    public YamlFileStorage append(String key, Object object) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getFile());
        config.set(key, object);
        try {
            config.save(file);
        } catch (IOException e) {
            DataStorager.logger.log(Level.SEVERE, e.getMessage() + e.getCause());
        }
        return this;
    }

    /**
     * Gets data from configuration
     *
     * @param key The key of the entry
     * @return ...
     */
    public Object get(String key) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getFile());
        return config.get(key);
    }

    /**
     * Gets all cached data from the file entry
     *
     * @return The data gotten
     */
    public Map<String, Object> get() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getFile());
        return config.getValues(true);
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
        this.file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                DataStorager.logger.log(Level.SEVERE, e.getMessage() + e.getCause());
                return null;
            }
        }
        return file;
    }
}