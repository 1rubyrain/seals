package in.rubyra.seals.client;

import in.rubyra.seals.client.model.SealEntityModel;
import in.rubyra.seals.client.render.SealEntityRenderer;
import in.rubyra.seals.entity.behavior.SealEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class EntityRendererListener {
    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(SealEntity.class, new SealEntityRenderer(new SealEntityModel()));
    }
}