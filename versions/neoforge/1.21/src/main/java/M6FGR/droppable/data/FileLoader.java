package M6FGR.droppable.data;

import M6FGR.droppable.main.Droppable;
import M6FGR.droppable.world.loot.EpicFightLoots;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.fml.loading.FMLPaths;
import yesman.epicfight.registry.EpicFightRegistries;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    private static final File CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("droppable_loot").toFile();

    public static void load() {
        EpicFightLoots.initDefaults();

        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
            return;
        }

        File[] files = CONFIG_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return;

        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                for (String key : root.keySet()) {
                    ResourceLocation mobId = ResourceLocation.parse(key);
                    EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(mobId);

                    JsonObject data = root.getAsJsonObject(key);
                    float chance = data.get("chance").getAsFloat();

                    List<Object> pool = new ArrayList<>();
                    data.getAsJsonObject("skills").entrySet().forEach(s -> {
                        String skillIdStr = s.getKey();
                        float weight = s.getValue().getAsFloat();

                        // FIX: Convert String ID to Holder<Skill>
                        ResourceLocation skillRl = ResourceLocation.parse(skillIdStr);

                        // Look it up in the Epic Fight Registry
                        EpicFightRegistries.SKILL.getHolder(skillRl).ifPresentOrElse(holder -> {
                            pool.add(weight); // Add the weight (Float)
                            pool.add(holder); // Add the Holder (NOT the String)
                        }, () -> {
                            Droppable.LOGGER.error("Skill not found: " + skillIdStr);
                        });
                    });

                    if (!pool.isEmpty()) {
                        EpicFightLoots.ACTIVE_LOOTS.put(type, new EpicFightLoots.MobDropData(chance, pool.toArray()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}