/*
 * This file is part of MapDataAPI, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
