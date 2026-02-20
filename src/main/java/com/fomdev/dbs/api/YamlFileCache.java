package com.fomdev.dbs.api;

import org.bukkit.NamespacedKey;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a cache api for {@link YamlFileStorage}
 *
 * @author fomdev
 * @since 0.0.2
 * @see YamlFileStorage
 */
public class YamlFileCache {
    private Map<String, Object> data;
    private YamlFileStorage     storage;

    /**
     * Same as {@link YamlFileStorage#YamlFileStorage(NamespacedKey)}
     *
     * @see YamlFileStorage#YamlFileStorage(NamespacedKey)
     * @param path ...
     */
    public YamlFileCache(NamespacedKey path) {
        this.storage = new YamlFileStorage(path);
        this.data    = storage.get();
        if (this.data == null) this.data = new HashMap<>();
    }

    /**
     * Same as {@link YamlFileStorage#YamlFileStorage(String, String)}
     *
     * @see YamlFileStorage#YamlFileStorage(String, String)
     * @param path ...
     * @param file ...
     */
    public YamlFileCache(String path, String file) {
        this(new NamespacedKey(path, file));
    }

    /**
     * Directly loads config from file
     *
     * @see YamlFileStorage#YamlFileStorage(File)
     * @param file The file to load
     * @since 1.0.2
     */
    public YamlFileCache(File file) {
        this.storage = new YamlFileStorage(file);
        this.data    = storage.get();
    }

    /**
     * Appends data to buffer
     *
     * @param key The key of the data
     * @param value The value to store
     * @return ...
     */
    public YamlFileCache append(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * Gets data from cache
     *
     * @param key The key of the data
     * @return The data gotten
     */
    public Object get(String key) {
        return data.get(key);
    }

    /**
     * Gets data from parent
     *
     * @return The data from parent
     */
    public Map<String, Object> get() { return storage.get(); }

    /**
     * Removes data from cache
     *
     * @param key The key of the data
     * @return ...
     */
    public YamlFileCache remove(String key) {
        this.data.remove(key);
        return this;
    }

    /**
     * Clears cache
     *
     * @return ...
     */
    public YamlFileCache clear() {
        this.data.clear();
        return this;
    }

    /**
     * @see YamlFileStorage#removeCacheEntry()
     * @since 0.0.3
     */
    public void removeCacheEntry() {
        this.storage.removeCacheEntry();
    }

    /**
     * Applies changes to the file
     * REMEMBER TO CALL THIS EVER SINCE YOU ARE
     * GOING TO CLOSE FILE, OR THE BUFFER AND
     * CHANGES DONE WON'T BE APPLIED
     */
    public void write() {
        for (String k: data.keySet()) {
            storage.append(k, data.get(k));
        }
    }
}