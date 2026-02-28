package M6FGR.droppable.data;

import M6FGR.droppable.world.loot.EpicFightLoots;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootData extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();

    public LootData() {
        super(GSON, "skill_drops");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager rm, ProfilerFiller profiler) {
        object.forEach((location, element) -> {
            JsonObject root = element.getAsJsonObject();
            root.entrySet().forEach(entry -> {
                ResourceLocation mobId = ResourceLocation.parse(entry.getKey());
                EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(mobId);
                JsonObject data = entry.getValue().getAsJsonObject();
                float chance = data.get("chance").getAsFloat();

                List<Object> pool = new ArrayList<>();
                data.getAsJsonObject("skills").entrySet().forEach(s -> {
                    pool.add(s.getValue().getAsFloat()); // Weight
                    pool.add(s.getKey());
                });

                EpicFightLoots.ACTIVE_LOOTS.put(type, new EpicFightLoots.MobDropData(chance, pool.toArray()));
            });
        });
    }
}