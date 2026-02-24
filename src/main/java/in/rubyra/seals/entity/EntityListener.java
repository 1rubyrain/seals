package in.rubyra.seals.entity;

import in.rubyra.seals.entity.behavior.SealEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class EntityListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(SealEntity.class, "Seal");
    }

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        Registry.register(event.registry, Identifier.of(MOD_ID, "seal"), SealEntity::new);
    }
}
