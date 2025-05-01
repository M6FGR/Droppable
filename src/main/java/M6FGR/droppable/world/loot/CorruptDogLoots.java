package M6FGR.droppable.world.loot;

import M6FGR.droppable.main.Droppable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.compat.ICompatModule;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@EventBusSubscriber(
        modid = Droppable.MOD_ID,
        bus = Bus.MOD
)
public class CorruptDogLoots implements ICompatModule {
    public CorruptDogLoots() {
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
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.HUSK, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                         1.0F, "cdmoveset:bstep",
                                         1.0F, "cdmoveset:wolf_dodge",
                                         1.0F, "cdmoveset:bloodwolf",
                                         1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.DROWNED, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get()
                        ).apply(SetSkillFunction.builder(new Object[]
                                {1.0F, "cdmoveset:yamatostep",
                                        1.0F, "cdmoveset:bstep",
                                        1.0F, "cdmoveset:wolf_dodge",
                                        1.0F, "cdmoveset:bloodwolf",
                                        1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.STRAY, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.SKELETON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.SPIDER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.CAVE_SPIDER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.CREEPER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.ENDERMAN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.01F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.PIGLIN, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.PIGLIN_BRUTE, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.WITHER_SKELETON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.WITCH, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.WITHER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.8F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.ENDER_DRAGON, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.8F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.PILLAGER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.VINDICATOR, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))))
                .add(EntityType.EVOKER, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.005F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike)EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(new Object[]
                                        {1.0F, "cdmoveset:yamatostep",
                                                1.0F, "cdmoveset:bstep",
                                                1.0F, "cdmoveset:wolf_dodge",
                                                1.0F, "cdmoveset:bloodwolf",
                                                1.0F, "cdmoveset:fatalfalsh",}))));
    }

    @Override
    public void onModEventBus(IEventBus iEventBus) {

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
}
