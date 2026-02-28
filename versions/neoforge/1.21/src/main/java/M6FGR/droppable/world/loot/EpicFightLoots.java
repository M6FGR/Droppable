package M6FGR.droppable.world.loot;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.config.CommonConfig;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.registry.entries.EpicFightItems;
import yesman.epicfight.registry.entries.EpicFightSkills;

import java.util.HashMap;
import java.util.Map;

public class EpicFightLoots {
    // We use a ConcurrentHashMap if you expect reloads while the game is running to prevent crashes
    public static final Map<EntityType<?>, MobDropData> ACTIVE_LOOTS = new HashMap<>();

    public record MobDropData(float chance, Object[] skillPool) {}

    // 1. Move the defaults to a method so we can call it whenever we reset the config
    public static void initDefaults() {
        ACTIVE_LOOTS.clear();

        Object[] defaultSkills = new Object[]{
                1.0F, EpicFightSkills.REVELATION,
                1.0F, EpicFightSkills.METEOR_SLAM,
                1.0F, EpicFightSkills.PARRYING,
                1.0F, EpicFightSkills.TECHNICIAN,
                1.0F, EpicFightSkills.IMPACT_GUARD,
                1.0F, EpicFightSkills.EMERGENCY_ESCAPE,
                1.0F, EpicFightSkills.DEMOLITION_LEAP
        };

        addLoot(EntityType.ZOMBIE, 0.005F, defaultSkills);
        addLoot(EntityType.HUSK, 0.005F, defaultSkills);
        addLoot(EntityType.DROWNED, 0.005F, defaultSkills);
        addLoot(EntityType.STRAY, 0.005F, defaultSkills);
        addLoot(EntityType.SKELETON, 0.005F, defaultSkills);
        addLoot(EntityType.SPIDER, 0.005F, defaultSkills);
        addLoot(EntityType.CAVE_SPIDER, 0.005F, defaultSkills);
        addLoot(EntityType.CREEPER, 0.005F, defaultSkills);
        addLoot(EntityType.ENDERMAN, 0.05F, defaultSkills);
        addLoot(EntityType.PIGLIN, 0.005F, defaultSkills);
        addLoot(EntityType.PIGLIN_BRUTE, 0.005F, defaultSkills);
        addLoot(EntityType.WITHER_SKELETON, 0.05F, defaultSkills);
        addLoot(EntityType.WITCH, 0.005F, defaultSkills);
        addLoot(EntityType.PILLAGER, 0.005F, defaultSkills);
        addLoot(EntityType.VINDICATOR, 0.005F, defaultSkills);
        addLoot(EntityType.EVOKER, 0.02F, defaultSkills);
        addLoot(EntityType.WITHER, 0.8F, defaultSkills);
        addLoot(EntityType.ENDER_DRAGON, 0.8F, defaultSkills);
    }

    private static void addLoot(EntityType<?> type, float chance, Object[] skills) {
        ACTIVE_LOOTS.put(type, new MobDropData(chance, skills));
    }

    public static void onSkillDrops() {
        EpicFightEventHooks.Registry.SKILLBOOK_LOOT_TABLE.registerEvent(event -> {
            int modifier = CommonConfig.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
            float dropChanceModifier = (float) (100 + modifier) / Math.max(1, 100 - modifier);
            ACTIVE_LOOTS.forEach((entityType, data) -> {
                float finalChance = data.chance() * dropChanceModifier;
                if (finalChance > 0 && data.skillPool().length > 0) {
                    event.add(entityType, LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(finalChance))
                            .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get())
                                    .apply(SetSkillFunction.builder(data.skillPool()))
                            )
                    );
                }
            });
        });
    }
}