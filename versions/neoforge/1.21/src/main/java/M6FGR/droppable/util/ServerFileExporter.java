package M6FGR.droppable.util;

import M6FGR.droppable.data.FileLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.neoforged.fml.loading.FMLPaths;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class ServerFileExporter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("droppable_loot").toFile();

    public static void saveAndReload(String entityId, Map<String, Float> skills, float chance) {
        if (!CONFIG_DIR.exists()) CONFIG_DIR.mkdirs();

        File file = new File(CONFIG_DIR, entityId.replace(":", "_") + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            JsonObject root = new JsonObject();
            JsonObject mobData = new JsonObject();
            mobData.addProperty("chance", chance);

            JsonObject skillsJson = new JsonObject();
            skills.forEach(skillsJson::addProperty);

            mobData.add("skills", skillsJson);
            root.add(entityId, mobData);

            GSON.toJson(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileLoader.load();
    }
}