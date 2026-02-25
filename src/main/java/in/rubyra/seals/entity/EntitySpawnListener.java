package in.rubyra.seals.entity;

import in.rubyra.seals.entity.behavior.SealEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;

public class EntitySpawnListener {
    @EventListener
    public void registerEntitySpawn(BiomeModificationEvent event) {
        if (event.biome == Biome.SHRUBLAND ||
            event.biome == Biome.TAIGA ||
            event.biome == Biome.PLAINS ||
            event.biome == Biome.ICE_DESERT ||
            event.biome == Biome.TUNDRA) {

            event.biome.addPassiveEntity(SealEntity.class, 16);
        }
    }
}
