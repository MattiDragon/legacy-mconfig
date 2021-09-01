package io.github.mattidragon.mconfig;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A class that acts a reference to json config. Allowing access to all of its fields.
 * @see #getOption(String)
 */
public final class Config {
    public static final Logger LOGGER = LogManager.getLogger("mconfig");
    static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private JsonObject activeConfig;
    private JsonObject defaultConfig;
    private final File configFile;
    private final Path defaultFile;
    final Identifier id;
    
    /**
     * Creates a new config. This will load it. It's required that a default file is present in the config directory, of resources from the mod that has the mod id of the namespace of the id. This default will be merged with the users' config.
     * @param id The id of the config. The config is placed in config/&lt;namespace&gt;/&lt;path&gt;.json.
     * @return A new instance of {@code Config}
     * @throws IllegalStateException If a default file isn't present or didn't load properly.
     */
    @Contract("_ -> new")
    public static @NotNull Config of(@NotNull Identifier id) {
        Objects.requireNonNull(id, "id is null");
        return new Config(id);
    }
    
    /**
     * Creates a new config. This will load it. It's required that a default file (config.json) is present in the resources from the mod that has the mod id of the supplied id. This default will be merged with the users' config.
     * @param id The id of the mod the config belongs to. The config is placed in config/&lt;id&gt;.json.
     * @return A new instance of {@code Config}
     * @throws IllegalStateException If a default file isn't present or didn't load properly.
     */
    @Contract("_ -> new")
    public static @NotNull Config of(@NotNull String id) {
        Objects.requireNonNull(id, "id is null");
        return new Config(id);
    }
    
    private Config(@NotNull String id) {
        this.id = new Identifier(id, "");
        configFile = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(id + ".json")
                .toFile();
        defaultFile = FabricLoader.getInstance()
                .getModContainer(id)
                .orElseThrow(() -> new IllegalStateException("Could not load default config for " + this.id + " due to no mod with the correct id existing."))
                .getPath("config.json");
        loadDefault();
        load();
    }
    
    private Config(@NotNull Identifier id) {
        this.id = id;
        configFile = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(id.getNamespace())
                .resolve(id.getPath() + ".json").toFile();
        defaultFile = FabricLoader.getInstance()
                .getModContainer(id.getNamespace())
                .orElseThrow(() -> new IllegalStateException("Could not load default config for " + id + " due to no mod with the correct id existing."))
                .getPath("config")
                .resolve(id.getPath() + ".json");
    
        loadDefault();
        load();
    }
    
    /**
     * Creates a simple reload screen for the config. It's recommended that you create our own screen.
     * @param parent The parent screen.
     * @return A screen that allows reloading this config.
     */
    public Screen createScreen(Screen parent) {
        return new ConfigScreen(this, parent);
    }
    
    /**
     * Finds an option from the json tree. If the user hasn't specified it defaults will be used.
     * @param key The path to the option, separated by '.' characters. An empty key is supported, returning the root.
     * @return The option located at the path.
     * @throws NoSuchElementException If the option doesn't exist.
     * @throws IllegalStateException If a part of the path isn't an object.
     */
    public @NotNull JsonElement getOption(@NotNull String key) {
        if (key.isEmpty()) return activeConfig;
        Queue<String> queue = new LinkedList<>();
        Collections.addAll(queue, key.split("\\."));
        return getElement(activeConfig, queue);
    }
    
    /**
     * Sets an option in the json tree. Remember to call {@link #save()} to make sure this is saved.
     * @param key The path to the option, separated by '.' characters. An empty key is supported, returning the root.
     * @param element The element which is to be placed at the location of the path.
     * @throws NoSuchElementException If the option doesn't exist.
     * @throws IllegalStateException If a part of the path isn't an object.
     */
    public void setOption(@NotNull String key, @NotNull JsonElement element) {
        String[] keys = key.split("\\.");
        if (key.isEmpty()) {
            activeConfig = element.getAsJsonObject();
        }
        String name = keys[keys.length - 1];
        Queue<String> queue = new LinkedList<>();
        Collections.addAll(queue, Arrays.copyOfRange(keys, 0, keys.length - 1));
        JsonObject parent = getElement(activeConfig, queue).getAsJsonObject();
        if (!parent.has(name)) throw new NoSuchElementException("The config " + id + " doesn't have a property with the key " + key);
        parent.add(name, element);
    }
    
    /**
     * Loads the config from file.
     */
    public void load() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment())
            loadDefault();
        
        try {
            boolean changed = false;
            if (configFile.exists() && configFile.isFile()) {
                JsonObject loadedConfig = GSON.fromJson(new FileReader(configFile), JsonObject.class);
                if (loadedConfig != null)
                    activeConfig = mergeConfigs(defaultConfig, loadedConfig, new HashSet<>());
                if (!activeConfig.equals(loadedConfig))
                    changed = true;
            } else {
                changed = true;
            }
            if (changed) {
                save();
            }
        } catch (IOException | JsonParseException e) {
            LOGGER.warn("Failed to load config " + id + ", falling back to default and overriding!", e);
        }
    }
    
    /**
     * Saves the config to file.
     * @throws IOException If an I/O error occurs.
     */
    public void save() throws IOException {
        configFile.getParentFile().mkdirs();
        configFile.createNewFile();
        FileWriter writer = new FileWriter(configFile);
        GSON.toJson(activeConfig, JsonObject.class, writer);
        writer.close();
    }
    
    private void loadDefault() {
        try {
            activeConfig = defaultConfig = GSON.fromJson(Files.newBufferedReader(defaultFile), JsonObject.class);
        } catch (IOException | JsonParseException e) {
            throw new IllegalStateException("Could not load default config for " + id, e);
        }
    }
    
    private @NotNull JsonObject mergeConfigs(@NotNull JsonObject defaultConfig, @NotNull JsonObject loadedConfig, @NotNull Set<JsonObject> used) {
        JsonObject mergedConfig = new JsonObject();
        // Stop infinite recursion
        for (JsonObject obj : used) {
            if (obj == defaultConfig || obj == loadedConfig) throw new IllegalStateException("Same object used multiple times!");
        }
        used.add(defaultConfig);
        used.add(loadedConfig);
        
        for (var entry : defaultConfig.entrySet()) {
            String key = entry.getKey();
            JsonElement loadedValue = loadedConfig.get(entry.getKey());
            JsonElement defaultValue = entry.getValue();
            
            if (loadedValue != null) {
                // Primitives, nulls and arrays are replaced
                if ((defaultValue.isJsonPrimitive() && loadedValue.isJsonPrimitive())
                        || (defaultValue.isJsonNull() && loadedValue.isJsonNull())
                        || (defaultValue.isJsonArray() && loadedValue.isJsonArray())) {
                    mergedConfig.add(key, loadedValue);
                    continue;
                }
                // Objects are recursively merged
                if ((defaultValue.isJsonObject() && loadedValue.isJsonObject())) {
                    mergedConfig.add(key, mergeConfigs(defaultValue.getAsJsonObject(), loadedValue.getAsJsonObject(), used));
                    continue;
                }
                
            }
            
            // A type mismatch uses default
            mergedConfig.add(key, defaultValue);
        }
        return mergedConfig;
    }
    
    private @NotNull JsonElement getElement(JsonObject tree, @NotNull Queue<String> names) {
        if (names.isEmpty()) return tree;
        JsonElement element;
        String name = names.poll();
        
        if (names.isEmpty()) {
            element = tree.get(name);
        } else {
            element = getElement(tree.get(name).getAsJsonObject(), names);
        }
        if (element == null) throw new NoSuchElementException("No config option with the name " + name);
        return element;
    }
}
