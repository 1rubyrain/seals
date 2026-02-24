package in.rubyra.seals;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class SealsMod {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    @EventListener
    public void init(InitEvent event) {
    }
}
