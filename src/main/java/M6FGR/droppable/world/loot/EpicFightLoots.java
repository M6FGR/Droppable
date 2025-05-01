package M6FGR.droppable.world.loot;

import M6FGR.droppable.main.Droppable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@EventBusSubscriber(
        modid = Droppable.MOD_ID,
        bus = Bus.MOD
)
public class EpicFightLoots {
    public EpicFightLoots() {
    }

    @SubscribeEvent
    public static void SkillDrops(SkillLootTableRegistryEvent event) {
        int modifier = ConfigManager.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = 100 - modifier;
        float dropChanceModifier = antiDropChance == 0 ? Float.MAX_VALUE : (float)dropChance / (float)antiDropChance;
        event.add(EntityType.ZOMBIE, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get())
                        .apply(SetSkillFunction.builder(new Object[]
                                {1.0F, "epicfight:revelation",
                                        1.0F, "epicfight:meteor_slam",
                                        1.0F, "epicfight:demolition_leap",}))))
                .add(EntityType.HUSK, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"))))
                .add(EntityType.DROWNED, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()
                        ).apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                "epicfight:demolition_leap", 1.0F,
                                "epicfight:meteor_slam"}))))
                .add(EntityType.STRAY, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.SKELETON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.SPIDER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.CAVE_SPIDER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.CREEPER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.ENDERMAN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.01F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.PIGLIN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                         "epicfight:meteor_slam"}))))
                .add(EntityType.PIGLIN_BRUTE, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                         "epicfight:meteor_slam"}))))
                .add(EntityType.WITHER_SKELETON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                         "epicfight:meteor_slam"}))))
                .add(EntityType.WITCH, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                         "epicfight:meteor_slam"}))))
                .add(EntityType.WITHER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.8F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.ENDER_DRAGON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.8F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.PILLAGER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                         "epicfight:meteor_slam"}))))
                .add(EntityType.VINDICATOR, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))))
                .add(EntityType.EVOKER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]{1.0F, "epicfight:revelation", 1.0F,
                                        "epicfight:demolition_leap", 1.0F,
                                        "epicfight:meteor_slam"}))));
    }
}
