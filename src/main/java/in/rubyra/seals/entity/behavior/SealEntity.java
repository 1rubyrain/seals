package in.rubyra.seals.entity.behavior;

import in.rubyra.seals.SealsMod;
import in.rubyra.seals.entity.EntityListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;


@HasTrackingParameters(trackingDistance = 160, updatePeriod = 2)
public class SealEntity extends AnimalEntity implements MobSpawnDataProvider {
    public SealEntity(World world) {
        super(world);

        texture = "seals:textures/entities/seal.png";
        setBoundingBoxSpacing(0.7F, 0.4F);

        maxHealth = 30;
        health = 15;

        maxAir = air = 36000;

        setDapper(random.nextInt(500) == 0);
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(16, (byte)0);
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("dapper", this.isDapper());
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.setDapper(nbt.getBoolean("dapper"));
    }

    public boolean isDapper() {
        return dataTracker.getByte(16) == 1;
    }

    public void setDapper(boolean dapper) {
        this.dataTracker.set(16, (byte)(dapper ? 1 : 0));
    }

    protected boolean canDespawn() {
        return SealsMod.SEALS_CONFIG.sealsWithHatsDespawn
                ? super.canDespawn() : !isDapper();
    }

    protected float getPathfindingFavor(int x, int y, int z) {
        return world.getBlockId(x, y - 1, z) == Block.SAND.id
                ? 10.0F : world.method_1782(x, y, z) - 0.5F;
    }

    public boolean isValidSpawnBlock(int spawnX, int spawnY, int spawnZ) {
        int floorBlockId = world.getBlockId(spawnX, spawnY - 1, spawnZ);
        if (floorBlockId == Block.SAND.id || floorBlockId == Block.GRAVEL.id) {
            return true;
        }
        // only allow spawning on grass if snow layer above
        if (floorBlockId == Block.GRASS_BLOCK.id) {
            int collidingBlockId = world.getBlockId(spawnX, spawnY, spawnZ);
            return collidingBlockId == Block.SNOW.id;
        }
        return false;
    }
    public boolean canSpawn() {
        int spawnX = MathHelper.floor(x);
        int spawnY = MathHelper.floor(boundingBox.minY);
        int spawnZ = MathHelper.floor(z);

        return isValidSpawnBlock(spawnX, spawnY, spawnZ) &&
               world.getBrightness(spawnX, spawnY, spawnZ) > 5 &&
               world.canSpawnEntity(boundingBox);
    }

    public Box getCollisionAgainstShape(Entity other) {
        return other.boundingBox;
    }
    public Box getBoundingBox() {
        return boundingBox;
    }
    public boolean isCollidable() {
        return !dead;
    }

    private int jumpCooldownTicks = 0;
    protected void tickLiving() {
        boolean inWater = isInFluid(Material.WATER);
        if (inWater) {
            movementSpeed = 0.8F;
        }

        super.tickLiving();

        if (inWater) {
            // if fully submerged, swim up
            if (isSubmergedInWater()) {
                velocityY = 0.24F;
            }
            return;
        }

        // on land

        if (jumpCooldownTicks == 0) {
            // jump is ready
            movementSpeed = 0.8F;
            jumping = (forwardSpeed > 0);
        }
        else {
            // jump not ready, force no jumps and count down
            jumping = false;
            jumpCooldownTicks--;
        }

        if (!onGround) {
            movementSpeed = 0F;
            jumpCooldownTicks = random.nextInt(5,15);
        }
    }


    public boolean isPushable() {
        return false; // too heavy
    }

    protected void jump() {
        velocityY = 0.1F;
    }

    protected String getRandomSound() {
        return "seals:mob.seal";
    }

    protected String getHurtSound() {
        return "seals:mob.seal";
    }

    protected String getDeathSound() {
        return "seals:mob.seal";
    }

    protected float getSoundVolume() {
        return 0.3F;
    }
    public int getMinAmbientSoundDelay() {
        return 1000;
    }

    protected void dropItems() {
        if (SealsMod.SEALS_CONFIG.dropSpongeOnDeath && random.nextInt(5) == 0) {
            dropItem(Block.SPONGE.id, 1);
        }
    }

    public void sneeze() {
        world.playSound(x, y, z, "seals:mob.sealsneeze", 4.0F,
                (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

        if (!world.isRemote) {
            world.createExplosion(this, x, y, z, 1F, false);
            dropItem(Item.SLIMEBALL.id, 1);
        }
    }

    //@Environment(EnvType.CLIENT)
    //public float getScale(float delta) {
    //    return (float) (health * 2) / maxHealth;
    //}

    public boolean interact(PlayerEntity player) {
        ItemStack selectedItem = player.inventory.getSelectedItem();
        if (selectedItem == null) {
            return false;
        }

        if (selectedItem.itemId == Item.FEATHER.id) {
            player.inventory.removeStack(player.inventory.selectedSlot, 1);
            sneeze();
            return true;
        }

        if (selectedItem.itemId == Item.RAW_FISH.id) {
            if (health > 0 && health < maxHealth) {
                player.inventory.removeStack(player.inventory.selectedSlot, 1);
                health++;
                hearts = maxHealth / 2;
            }
            return true;
        }

        if (SealsMod.SEALS_CONFIG.blackWoolGivesSealsHats &&
            selectedItem.itemId == Block.WOOL.id &&
            selectedItem.getDamage() == 15) { // black
            if (!isDapper()) {
                player.inventory.removeStack(player.inventory.selectedSlot, 1);
                setDapper(true);
            }
            return true;
        }

        return false;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "seal");
    }
}