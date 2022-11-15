package net.william278.mapdataapi;

import net.querz.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class PositionTag {

    private int x;
    private int y;
    private int z;

    protected PositionTag(@NotNull CompoundTag positionTag) {
        this.x = positionTag.getIntTag("x").asInt();
        this.y = positionTag.getIntTag("y").asInt();
        this.z = positionTag.getIntTag("z").asInt();
    }

    public PositionTag(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CompoundTag toTag() {
        final CompoundTag positionData = new CompoundTag();
        positionData.putInt("x", this.x);
        positionData.putInt("y", this.y);
        positionData.putInt("z", this.z);
        return positionData;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
