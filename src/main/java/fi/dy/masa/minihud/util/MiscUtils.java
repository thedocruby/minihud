package fi.dy.masa.minihud.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import fi.dy.masa.malilib.util.Constants;
import fi.dy.masa.malilib.util.IntBoundingBox;

public class MiscUtils
{
    private static final Random RAND = new Random();
    private static final DecimalFormat DURATION_FORMAT = new DecimalFormat("#.##");

    public static long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }

    public static boolean canSlimeSpawnAt(int posX, int posZ, long worldSeed)
    {
        return canSlimeSpawnInChunk(posX >> 4, posZ >> 4, worldSeed);
    }

    public static boolean canSlimeSpawnInChunk(int chunkX, int chunkZ, long worldSeed)
    {
        long slimeSeed = 987234911L;
        long rngSeed = worldSeed +
                       (long) (chunkX * chunkX *  4987142) + (long) (chunkX * 5947611) +
                       (long) (chunkZ * chunkZ) * 4392871L + (long) (chunkZ * 389711) ^ slimeSeed;

        RAND.setSeed(rngSeed);

        return RAND.nextInt(10) == 0;
    }

    public static boolean isStructureWithinRange(@Nullable BlockBox bb, BlockPos playerPos, int maxRange)
    {
        if (bb == null ||
            playerPos.getX() < (bb.minX - maxRange) ||
            playerPos.getX() > (bb.maxX + maxRange) ||
            playerPos.getZ() < (bb.minZ - maxRange) ||
            playerPos.getZ() > (bb.maxZ + maxRange))
        {
            return false;
        }

        return true;
    }

    public static boolean isStructureWithinRange(@Nullable IntBoundingBox bb, BlockPos playerPos, int maxRange)
    {
        if (bb == null ||
            playerPos.getX() < (bb.minX - maxRange) ||
            playerPos.getX() > (bb.maxX + maxRange) ||
            playerPos.getZ() < (bb.minZ - maxRange) ||
            playerPos.getZ() > (bb.maxZ + maxRange))
        {
            return false;
        }

        return true;
    }

    public static boolean areBoxesEqual(IntBoundingBox bb1, IntBoundingBox bb2)
    {
        return bb1.minX == bb2.minX && bb1.minY == bb2.minY && bb1.minZ == bb2.minZ &&
               bb1.maxX == bb2.maxX && bb1.maxY == bb2.maxY && bb1.maxZ == bb2.maxZ;
    }

    @Nullable
    public static void addStewTooltip(ItemStack stack, List<Text> lines)
    {
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("Effects", Constants.NBT.TAG_LIST))
        {
            ListTag effects = tag.getList("Effects", Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < effects.size(); i++)
            {
                tag = effects.getCompound(i);
                lines.add(Math.min(1, lines.size()), new TranslatableText("minihud.label.stew_info.effect",
                    StatusEffect.byRawId(tag.getInt("EffectId")).method_5560().getString(),
                    DURATION_FORMAT.format((double) tag.getInt("EffectDuration") / 20D))
                );
            }
        }
    }
}
