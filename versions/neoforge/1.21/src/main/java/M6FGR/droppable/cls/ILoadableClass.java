package M6FGR.droppable.cls;

import M6FGR.droppable.main.Droppable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import yesman.epicfight.main.EpicFightSharedConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public interface ILoadableClass {
    Set<Class<? extends ILoadableClass>> LOADED_CLASSES = new HashSet<>();

    static void load(IEventBus bus, Class<? extends ILoadableClass> loadableClass) {
        if (LOADED_CLASSES.contains(loadableClass)) {
            throw new IllegalStateException("Class [" + loadableClass.getName() + "] is already loaded!");
        }

        if (loadableClass.isInterface() || loadableClass.isEnum() || loadableClass.isAnnotation()) {
            throw new IllegalArgumentException("Cannot load [" + loadableClass.getName() + "]: It must be a Class, not an Interface, Enum, or Annotation.");
        }

        if (!hasOverriddenLogic(loadableClass)) {
            throw new RuntimeException("Class [" + loadableClass.getName() + "] implements ILoadableClass but does not override any methods. It's doing nothing!");
        }

        try {
            Constructor<? extends ILoadableClass> loadableCons = loadableClass.getConstructor();
            ILoadableClass loadableIns = loadableCons.newInstance();

            loadableIns.onModRegistry(bus);
            loadableIns.onNeoForgeRegistry(NeoForge.EVENT_BUS);

            bus.addListener(loadableIns::onModCommonSetupEvent);

            if (EpicFightSharedConstants.isPhysicalClient()) {
                loadableIns.onModClientEvents(bus);
                bus.addListener(loadableIns::onModClientSetupEvent);
            } else {
                loadableIns.onModServerEvents(bus);
                bus.addListener(loadableIns::onModServerSetupEvent);
            }

            LOADED_CLASSES.add(loadableClass);
            Droppable.LOGGER.info("Successfully loaded class: [{}]", loadableClass.getSimpleName());

        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class [" + loadableClass.getName() + "] is missing a public zero-argument constructor! Add 'public " + loadableClass.getSimpleName() + "() {}' to the class.");
        } catch (Exception e) {
            throw new RuntimeException("Error loading class [" + loadableClass.getName() + "]", e);
        }
    }

    @SafeVarargs
    static void loadAll(IEventBus bus, Class<? extends ILoadableClass>... loadableClasses) {
        for (Class<? extends ILoadableClass> clazz : loadableClasses) {
            load(bus, clazz);
        }
    }

    private static boolean hasOverriddenLogic(Class<? extends ILoadableClass> clazz) {
        String[] methodNames = {
                "onModCommonSetupEvent", "onModClientSetupEvent", "onModServerSetupEvent",
                "onModServerEvents", "onModClientEvents", "onNeoForgeRegistery", "onModRegistery"
        };
        for (Method method : clazz.getDeclaredMethods()) {
            for (String name : methodNames) {
                if (method.getName().equals(name)) return true;
            }
        }
        return false;
    }


    /** Takes FMLCommonSetupEvent. Use for cross-mod capability setup. Registered as Listener. */
    default void onModCommonSetupEvent(FMLCommonSetupEvent event) {}

    /** Takes FMLClientSetupEvent. Use for renderers/keybinds. Registered as Listener. */
    default void onModClientSetupEvent(FMLClientSetupEvent event) {}

    /** Takes FMLDedicatedServerSetupEvent. Registered as Listener. */
    default void onModServerSetupEvent(FMLDedicatedServerSetupEvent event) {}

    /** Use to register Server-only listeners to the Mod Bus. Called Directly. */
    default void onModServerEvents(IEventBus modBus) {}

    /** Use to register Client-only listeners to the Mod Bus. Called Directly. */
    default void onModClientEvents(IEventBus modBus) {}

    /** Use to register listeners to the global NeoForge.EVENT_BUS. Called Directly. */
    default void onNeoForgeRegistry(IEventBus neoForgeBus) {}

    /** Primary method to register Items, Blocks, Entities, etc... Called Directly. */
    default void onModRegistry(IEventBus modBus) {}
}