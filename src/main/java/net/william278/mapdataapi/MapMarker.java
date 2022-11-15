package net.william278.mapdataapi;

import net.querz.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class MapMarker {

    private int entityId;
    private int rotation;

    @NotNull
    private PositionTag position;

    protected MapMarker(@NotNull CompoundTag markerTag) {
        this.entityId = markerTag.getIntTag("EntityId").asInt();
        this.rotation = markerTag.getIntTag("Rotation").asInt();
        this.position = new PositionTag(markerTag.getCompoundTag("Pos"));
    }

    public MapMarker(int entityId, int rotation, int x, int y, int z) {
        this.entityId = entityId;
        this.rotation = rotation;
        this.position = new PositionTag(x, y, z);
    }

    @NotNull
    public CompoundTag toTag() {
        final CompoundTag markerData = new CompoundTag();
        markerData.putInt("EntityId", this.entityId);
        markerData.putInt("Rotation", this.rotation);
        markerData.put("Pos", this.position.toTag());
        return markerData;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) throws IllegalArgumentException {
        if (rotation < 0 || rotation > 360) {
            throw new IllegalArgumentException("Rotation must be between 0 and 360");
        }
        this.rotation = rotation;
    }

    @NotNull
    public PositionTag getPosition() {
        return position;
    }

    public void setPosition(@NotNull PositionTag position) {
        this.position = position;
    }
}
