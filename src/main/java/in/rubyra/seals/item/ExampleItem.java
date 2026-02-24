package in.rubyra.seals.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ExampleItem extends TemplateItem {
    public ExampleItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        return 9001;
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            if (user.isSneaking()) {
            }
        }
        
        return super.useOnBlock(stack, user, world, x, y, z, side);
    }
}
