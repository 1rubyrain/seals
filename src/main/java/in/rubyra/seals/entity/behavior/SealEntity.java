package in.rubyra.seals.entity.behavior;

import in.rubyra.seals.SealsMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class SealEntity extends AnimalEntity {
    public SealEntity(World world) {
        super(world);

        texture = "seals:textures/entities/seal.png";
        setBoundingBoxSpacing(0.4F, 0.3F);

        maxHealth = 30;
        health = 15;

        width = 0.4F;
        height = 0.3F;

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

    protected float getPathfindingFavor(int x, int y, int z) {
        return world.getBlockId(x, y - 1, z) == Block.SAND.id
                ? 10.0F
                : world.method_1782(x, y, z) - 0.5F;
    }

    public boolean canSpawn() {
        int spawnX = MathHelper.floor(x);
        int spawnY = MathHelper.floor(boundingBox.minY);
        int spawnZ = MathHelper.floor(z);
        return world.getBlockId(spawnX, spawnY - 1, spawnZ) == Block.SAND.id &&
               //world.dimension.biomeSource.getBiome(spawnX, spawnZ) &&
               world.getBrightness(spawnX, spawnY, spawnZ) > 8 &&
               world.canSpawnEntity(boundingBox);
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
        world.createExplosion(this, x, y, z, 1F, false);
        dropItem(Item.SLIMEBALL.id, 1);
    }

    //@Environment(EnvType.CLIENT)
    //public float getScale(float delta) {
    //    return (float) (health * 2) / maxHealth;
    //}

    public boolean interact(PlayerEntity player) {
        ItemStack selectedItem = player.inventory.getSelectedItem();
        if (selectedItem == null)
            return false;

        if (selectedItem.itemId == Item.FEATHER.id)
        {
            player.inventory.removeStack(player.inventory.selectedSlot, 1);
            if (!world.isRemote)
                sneeze();
            return true;
        }

        if (selectedItem.itemId == Item.RAW_FISH.id)
        {
            if (health > 0 && health < maxHealth) {
                player.inventory.removeStack(player.inventory.selectedSlot, 1);
                health++;
                hearts = maxHealth / 2;
            }
            return true;
        }

        return false;
    }
}