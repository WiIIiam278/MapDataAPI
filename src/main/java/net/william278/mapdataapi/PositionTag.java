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
