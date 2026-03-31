package M6FGR.droppable.compat;

import M6FGR.droppable.main.Droppable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.IEventBus;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.compat.ICompatModule;
import yesman.epicfight.config.CommonConfig;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.ArrayList;
import java.util.List;

public class EFSISSCompat implements ICompatModule {
    @Override
    public void onModEventBus(IEventBus iEventBus) {
        iEventBus.addListener(this::onSkillLootTableRegistry);
    }

    @Override
    public void onForgeEventBus(IEventBus iEventBus) {

    }

    @Override
    public void onModEventBusClient(IEventBus iEventBus) {

    }

    @Override
    public void onForgeEventBusClient(IEventBus iEventBus) {

    }

    private record MobDrop(EntityType<?> type, float baseChance) {}

    // Record for Skill data (Weight and the String ID)
    private record SkillData(float weight, String id) {}

    // The Master Skill List
    private static final List<SkillData> MASTER_SKILLS = List.of(
            new SkillData(1.0F, "efs_iss:magic_sword"),
            new SkillData(1.0F, "efs_iss:blood_into_mana"),
            new SkillData(1.0F, "efs_iss:auto_heal"),
            new SkillData(1.0F, "efs_iss:rapid_chant"),
            new SkillData(1.0F, "efs_iss:breathe_again"),
            new SkillData(1.0F, "efs_iss:against_magic"),
            new SkillData(1.0F, "efs_iss:reserve_mana"),
            new SkillData(1.0F, "efs_iss:connect_root"),
            new SkillData(1.0F, "efs_iss:hasty_casting"),
            new SkillData(1.0F, "efs_iss:mana_shield")
    );

    // 0.015F = 1.5%
    // 0.025F = 2.5%
    // 0.02F = 2%
    // 0.9F = 90%
    private static final List<MobDrop> TARGET_MOBS = List.of(
            new MobDrop(EntityType.ZOMBIE, 0.015F),
            new MobDrop(EntityType.HUSK, 0.015F),
            new MobDrop(EntityType.DROWNED, 0.015F),
            new MobDrop(EntityType.STRAY, 0.015F),
            new MobDrop(EntityType.SKELETON, 0.015F),
            new MobDrop(EntityType.SPIDER, 0.015F),
            new MobDrop(EntityType.CAVE_SPIDER, 0.015F),
            new MobDrop(EntityType.CREEPER, 0.015F),
            new MobDrop(EntityType.ENDERMAN, 0.02F),
            new MobDrop(EntityType.PIGLIN, 0.015F),
            new MobDrop(EntityType.PIGLIN_BRUTE, 0.015F),
            new MobDrop(EntityType.WITHER_SKELETON, 0.02F),
            new MobDrop(EntityType.WITCH, 0.015F),
            new MobDrop(EntityType.PILLAGER, 0.015F),
            new MobDrop(EntityType.VINDICATOR, 0.015F),
            new MobDrop(EntityType.EVOKER, 0.03F),
            new MobDrop(EntityType.WITHER, 0.9F),
            new MobDrop(EntityType.ENDER_DRAGON, 0.9F)
    );

    private void onSkillLootTableRegistry(SkillLootTableRegistryEvent event) {
        int modifier = CommonConfig.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = Math.max(1, 100 - modifier);
        float dropChanceModifier = (float) dropChance / (float) antiDropChance;
        if (dropChance < 0) {
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
