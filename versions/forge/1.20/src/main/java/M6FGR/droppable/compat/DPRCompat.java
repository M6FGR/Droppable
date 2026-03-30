package M6FGR.droppable.compat;

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

import java.util.HashMap;
import java.util.Map;

public class DPRCompat implements ICompatModule {
    // Local storage for this specific module's loot
    public static final Map<EntityType<?>, MobDropData> DPR_LOOTS = new HashMap<>();

    public record MobDropData(float chance, Object[] skillPool) {}

    @Override
    public void onModEventBus(IEventBus iEventBus) {
        iEventBus.addListener(this::onSkillLootTableRegistry);
    }

    private static void addLoot(EntityType<?> type, float chance, Object[] skills) {
        DPR_LOOTS.put(type, new MobDropData(chance, skills));
    }

    static Object[] dprSkills = new Object[]{
            1.0F, "dodge_parry_reward:heal1",
            1.0F, "dodge_parry_reward:heal2",
            1.0F, "dodge_parry_reward:heal3",
            1.0F, "dodge_parry_reward:heal4",
            1.0F, "dodge_parry_reward:health_boost1",
            1.0F, "dodge_parry_reward:health_boost2",
            1.0F, "dodge_parry_reward:health_boost3",
            1.0F, "dodge_parry_reward:health_boost4",
            1.0F, "dodge_parry_reward:absorb_1",
            1.0F, "dodge_parry_reward:absorb_2",
            1.0F, "dodge_parry_reward:absorb_3",
            1.0F, "dodge_parry_reward:absorb_4",
            1.0F, "dodge_parry_reward:damage_boost1",
            1.0F, "dodge_parry_reward:damage_boost2",
            1.0F, "dodge_parry_reward:damage_boost3",
            1.0F, "dodge_parry_reward:damage_boost4",
            1.0F, "dodge_parry_reward:speed1",
            1.0F, "dodge_parry_reward:speed2",
            1.0F, "dodge_parry_reward:speed3",
            1.0F, "dodge_parry_reward:speed4",
            1.0F, "dodge_parry_reward:stun_imm1",
            1.0F, "dodge_parry_reward:stun_imm2",
            1.0F, "dodge_parry_reward:stun_imm3",
            1.0F, "dodge_parry_reward:stun_imm4",
            1.0F, "dodge_parry_reward:stamina1",
            1.0F, "dodge_parry_reward:stamina2",
            1.0F, "dodge_parry_reward:stamina3",
            1.0F, "dodge_parry_reward:stamina4",
            1.0F, "dodge_parry_reward:resist1",
            1.0F, "dodge_parry_reward:resist2",
            1.0F, "dodge_parry_reward:resist3",
            1.0F, "dodge_parry_reward:resist4",
    };

    static {
        addLoot(EntityType.ZOMBIE, 0.015F, dprSkills);
        addLoot(EntityType.HUSK, 0.015F, dprSkills);
        addLoot(EntityType.DROWNED, 0.015F, dprSkills);
        addLoot(EntityType.STRAY, 0.015F, dprSkills);
        addLoot(EntityType.SKELETON, 0.015F, dprSkills);
        addLoot(EntityType.SPIDER, 0.015F, dprSkills);
        addLoot(EntityType.CAVE_SPIDER, 0.015F, dprSkills);
        addLoot(EntityType.CREEPER, 0.015F, dprSkills);
        addLoot(EntityType.ENDERMAN, 0.025F, dprSkills);
        addLoot(EntityType.PIGLIN, 0.015F, dprSkills);
        addLoot(EntityType.PIGLIN_BRUTE, 0.015F, dprSkills);
        addLoot(EntityType.WITHER_SKELETON, 0.02F, dprSkills);
        addLoot(EntityType.WITCH, 0.015F, dprSkills);
        addLoot(EntityType.PILLAGER, 0.015F, dprSkills);
        addLoot(EntityType.VINDICATOR, 0.015F, dprSkills);
        addLoot(EntityType.EVOKER, 0.025F, dprSkills);
        addLoot(EntityType.WITHER, 0.8F, dprSkills);
        addLoot(EntityType.ENDER_DRAGON, 0.8F, dprSkills);
    }

    private void onSkillLootTableRegistry(SkillLootTableRegistryEvent event) {
        int modifier = CommonConfig.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        float dropChanceModifier = (float) (100 + modifier) / Math.max(1, 100 - modifier);

        DPR_LOOTS.forEach((entityType, data) -> {
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
    }

    @Override public void onForgeEventBus(IEventBus iEventBus) {}
    @Override public void onModEventBusClient(IEventBus iEventBus) {}
    @Override public void onForgeEventBusClient(IEventBus iEventBus) {}
}