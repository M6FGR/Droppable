package M6FGR.droppable.main;

import M6FGR.droppable.client.gui.screen.LootsEditScreen;
import M6FGR.droppable.cls.ILoadableClass;
import M6FGR.droppable.compat.CDMoveSetCompat;
import M6FGR.droppable.network.DataGeneratorPayLoad;
import M6FGR.droppable.util.ServerFileExporter;
import M6FGR.droppable.world.loot.EpicFightLoots;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("droppable")
@EventBusSubscriber(
        modid = "efa"
)
public class Droppable {
    public static final String MOD_ID = "droppable";
    public static final Logger LOGGER = LogManager.getLogger("Droppable");

    public Droppable(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::onModCommonEvents);
        modBus.addListener(this::registerNetworking);
        container.registerExtensionPoint(IConfigScreenFactory.class, LootsEditScreen::new);
    }

    private void onModCommonEvents(FMLCommonSetupEvent event) {
        event.enqueueWork(EpicFightLoots::onSkillDrops);
    }

    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                DataGeneratorPayLoad.TYPE,
                DataGeneratorPayLoad.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        if (context.player() instanceof ServerPlayer player) {
                            if (player.hasPermissions(2)) {
                                ServerFileExporter.saveAndReload(
                                        payload.entityId(),
                                        payload.skills(),
                                        payload.chance()
                                );
                                player.sendSystemMessage(Component.literal("Success!, make sure to /reload so the loots apply to the world").withStyle(ChatFormatting.GREEN));
                            }
                        }
                    });
                }
        );
    }
}
