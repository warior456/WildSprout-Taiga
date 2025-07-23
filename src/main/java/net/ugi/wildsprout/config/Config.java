package net.ugi.wildsprout.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.ugi.wildsprout.WildSproutTaiga;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static net.ugi.wildsprout.WildSproutTaiga.CONFIG;

public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), WildSproutTaiga.MOD_ID + "_config.json");
    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
                // Read the file, filtering out comment lines before parsing
                String jsonContent = reader.lines()
                        .filter(line -> !line.trim().startsWith("//"))
                        .collect(Collectors.joining());

                CONFIG = GSON.fromJson(jsonContent, ConfigHandler.class);

                // Immediately save the config back. This will add any new config options
                // that might have been added in a mod update.
                saveConfig();
            } catch (IOException e) {
                WildSproutTaiga.LOGGER.warn("The config could not be loaded: " + e.getLocalizedMessage());
                // If loading fails, create a new default config
                CONFIG = new ConfigHandler();
                saveConfig();
            }
        } else {
            // If the file doesn't exist, create a new one
            CONFIG = new ConfigHandler();
            saveConfig();
        }
    }

    public static void saveConfig() {
        if (!CONFIG_FILE.getParentFile().exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            writer.write("{\n");

            List<Field> fields = Arrays.asList(ConfigHandler.class.getFields());
            Iterator<Field> iterator = fields.iterator();

            while (iterator.hasNext()) {
                Field field = iterator.next();

                // Write the comment if it exists
                if (field.isAnnotationPresent(Comment.class)) {
                    String commentValue = field.getAnnotation(Comment.class).value();
                    String[] commentLines = commentValue.split("\n");
                    for (String line : commentLines) {
                        if (line.trim().isEmpty()) {
                            writer.write("\t//\n");
                        } else {
                            writer.write("\t// " + line + "\n");
                        }
                    }
                }

                // Write the key-value pair
                try {
                    Object value = field.get(CONFIG);
                    writer.write("\t\"" + field.getName() + "\": " + GSON.toJson(value));
                } catch (IllegalAccessException e) {
                    WildSproutTaiga.LOGGER.warn("Could not access config field: " + field.getName());
                }

                // Write a comma if this is not the last element
                if (iterator.hasNext()) {
                    writer.write(",\n");
                } else {
                    writer.write("\n");
                }
            }

            writer.write("}\n");
        } catch (IOException e) {
            WildSproutTaiga.LOGGER.warn("The config could not be saved: " + e.getLocalizedMessage());
        }
    }
}
