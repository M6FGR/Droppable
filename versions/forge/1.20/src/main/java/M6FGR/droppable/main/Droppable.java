package M6FGR.droppable.main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;


@Mod("droppable")
@SuppressWarnings("removal")
public class Droppable {
    public static final String MOD_ID = "droppable";
    public static final Logger LOGGER = Logger.getLogger("droppable");
    public Droppable() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
