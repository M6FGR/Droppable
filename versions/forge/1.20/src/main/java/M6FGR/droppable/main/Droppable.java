package M6FGR.droppable.main;

import M6FGR.droppable.compat.DPRCompat;
import M6FGR.droppable.compat.EFSISSCompat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.compat.ICompatModule;

import java.util.logging.Logger;


@Mod("droppable")
@SuppressWarnings("removal")
public class Droppable {
    public static final String MOD_ID = "droppable";
    public static final Logger LOGGER = Logger.getLogger("droppable");
    public Droppable() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        MinecraftForge.EVENT_BUS.register(this);
        if (ModList.get().isLoaded("efs_iss")) {
            ICompatModule.loadCompatModule(context, EFSISSCompat.class);
        }
        if (ModList.get().isLoaded("dodge_parry_reward")) {
            ICompatModule.loadCompatModule(context, DPRCompat.class);
        }
    }
}
