package in.rubyra.seals;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.glasslauncher.mods.gcapi3.api.TriBoolean;
import net.glasslauncher.mods.gcapi3.api.ValueOnVanillaServer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class SealsMod {
    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    public static class SealsConfig {
        @ConfigEntry(name = "Drop sponge on death", multiplayerSynced = true)
        public Boolean dropSpongeOnDeath = false;

        @ConfigEntry(name = "Black wool gives seals hats", multiplayerSynced = true,
                     description = "If enabled, seals can be right clicked with black wool to give them a hat")
        public Boolean blackWoolGivesSealsHats = false;

        @ConfigEntry(name = "Enable pushable entity bug fix", multiplayerSynced = true,
                description = "If enabled, non-pushable entities will not be pushed by other entities")
        public Boolean enablePushableEntityBugFix = true;
    }
    @ConfigRoot(value = "config", visibleName = "seals!!! Options", index = 0)
    public static final SealsConfig SEALS_CONFIG = new SealsConfig();

    @EventListener
    public void init(InitEvent event) {

    }
}
