package M6FGR.droppable.world.loot;

import M6FGR.droppable.main.Droppable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.config.CommonConfig;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "droppable", bus = Bus.MOD)
public class EpicFightLoots {

    // Record for Mob data
    private record MobDrop(EntityType<?> type, float baseChance) {}

    // Record for Skill data (Weight and the String ID)
    private record SkillData(float weight, String id) {}

    // The Master Skill List
    private static final List<SkillData> MASTER_SKILLS = List.of(
            new SkillData(1.0F, "epicfight:revelation"),
            new SkillData(1.0F, "epicfight:meteor_slam"),
            new SkillData(1.0F, "epicfight:parrying"),
            new SkillData(1.0F, "epicfight:technician"),
            new SkillData(1.0F, "epicfight:impact_guard"),
            new SkillData(1.0F, "epicfight:emergency_escape"),
            new SkillData(1.0F, "epicfight:demolition_leap"),
            new SkillData(1.0F, "epicfight:death_harvest")
    );

    // The Master Mob List
    private static final List<MobDrop> TARGET_MOBS = List.of(
            new MobDrop(EntityType.ZOMBIE, 0.005F),
            new MobDrop(EntityType.HUSK, 0.005F),
            new MobDrop(EntityType.DROWNED, 0.005F),
            new MobDrop(EntityType.STRAY, 0.005F),
            new MobDrop(EntityType.SKELETON, 0.005F),
            new MobDrop(EntityType.SPIDER, 0.005F),
            new MobDrop(EntityType.CAVE_SPIDER, 0.005F),
            new MobDrop(EntityType.CREEPER, 0.005F),
            new MobDrop(EntityType.ENDERMAN, 0.01F),
            new MobDrop(EntityType.PIGLIN, 0.005F),
            new MobDrop(EntityType.PIGLIN_BRUTE, 0.005F),
            new MobDrop(EntityType.WITHER_SKELETON, 0.01F),
            new MobDrop(EntityType.WITCH, 0.005F),
            new MobDrop(EntityType.PILLAGER, 0.005F),
            new MobDrop(EntityType.VINDICATOR, 0.005F),
            new MobDrop(EntityType.EVOKER, 0.02F),
            new MobDrop(EntityType.WITHER, 0.8F),
            new MobDrop(EntityType.ENDER_DRAGON, 0.8F)
    );

    @SubscribeEvent
    public static void onSkillLootTableRegistry(SkillLootTableRegistryEvent event) {
        int modifier = CommonConfig.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = Math.max(1, 100 - modifier);
        float dropChanceModifier = (float) dropChance / (float) antiDropChance;
        if (dropChance <= 0) {
            Droppable.LOGGER.warning("Skillbook drop chance is negative! Check the config.");
        }

        for (MobDrop mob : TARGET_MOBS) {
            List<Object> combinedArgs = new ArrayList<>();
            for (SkillData skill : MASTER_SKILLS) {
                combinedArgs.add(skill.weight());
                combinedArgs.add(skill.id());
            }
            event.add(mob.type(), LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .when(LootItemRandomChanceCondition.randomChance(mob.baseChance() * dropChanceModifier))
                    .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get())
                            .apply(SetSkillFunction.builder(combinedArgs.toArray()))
                    )
            );
        }
    }
}