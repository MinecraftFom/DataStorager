package com.fomdev.dbs.api;

import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

/**
 * A cached {@link JsonFileStorage}, which reduces IO cost.
 * WARNING: If {@link JsonFileCache#write()} isn't called,
 * file buffer won't be saved. So remember to call this to
 * make sure the data won't get missing after execution.
 *
 * @author fomdev
 * @see JsonFileStorage
 * @since 0.0.1
 */
public class JsonFileCache {
    private Map<String, Object> buffer;
    private JsonFileStorage     storage;

    /**
     * Initializes the JSON Buffer
     *
     * @see JsonFileStorage#JsonFileStorage(NamespacedKey)
     * @param path The path of the json
     */
    public JsonFileCache(NamespacedKey path) {
        storage = new JsonFileStorage(path);
        buffer  = storage.get();
        if (buffer == null) buffer = new HashMap<>();
    }

    /**
     *
     * @see JsonFileStorage#JsonFileStorage(String, String)
     * @param path The folder of the json
     * @param file The file name of the json
     */
    public JsonFileCache(String path, String file) {
        this(new NamespacedKey(path, file));
    }

    /**
     * Appends the key and value into buffer
     *
     * @param key The key to store
     * @param value The value to be stored
     * @return The class itself, in order to make coding json in streams
     */
    public JsonFileCache append(String key, Object value) {
        buffer.put(key, value);
        return this;
    }

    /**
     * Gets the data from the buffer
     *
     * @param key The path of the data
     * @return The data from the buffer
     */
    public Object get(String key) {
        return buffer.get(key);
    }

    /**
     * Gets the map data from parent
     *
     * @return The stored data
     */
    public Map<String, Object> get() { return storage.get(); }

    /**
     * Removes the object by the given key in the buffer
     *
     * @param key The key to remvoe
     * @return The class itself, in order to make coding json in streams
     */
    public JsonFileCache remove(String key) {
        buffer.remove(key);
        return this;
    }

    /**
     * DANGEROUS: CLEARS THE BUFFER
     *
     * @return The class itself, in order to make coding json in streams
     */
    public JsonFileCache clear() {
        buffer.clear();
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
     * Writes the buffer into json file, but remember, you should have
     * called this ever since the program will be closed. If you haven't
     * called it, all the buffer data will vanish as the program stops
     */
    public void write() {
        storage.append(buffer);
    }
}