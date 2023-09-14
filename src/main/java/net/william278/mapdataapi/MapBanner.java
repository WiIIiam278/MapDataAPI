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

import net.querz.nbt.tag.*;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class MapBanner {

    private String color;
    private String text;
    private PositionTag position;

    protected MapBanner(@NotNull CompoundTag bannerTag) {
        this.color = bannerTag.getStringTag("Color").getValue();
        this.text = bannerTag.getStringTag("Name").getValue();
        this.position = new PositionTag(bannerTag.getCompoundTag("Pos"));
    }

    public MapBanner(@NotNull String color, @NotNull String text, int x, int y, int z) {
        this.color = color;
        this.text = text;
        this.position = new PositionTag(x, y, z);
    }

    public void setPosition(int x, int y, int z) {
        if (this.position == null) {
            this.position = new PositionTag(x, y, z);
        } else {
            this.position.setX(x);
            this.position.setY(y);
            this.position.setZ(z);
        }
    }

    @NotNull
    public CompoundTag toTag() {
        final CompoundTag bannerData = new CompoundTag();
        bannerData.putString("Color", this.color);
        bannerData.putString("Name", this.text);
        bannerData.put("Pos", this.position.toTag());
        return bannerData;
    }

    @NotNull
    public String getColor() {
        return color;
    }

    public void setColor(@NotNull String color) {
        this.color = color;
    }

    @NotNull
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull
    public PositionTag getPosition() {
        return position;
    }

    public void setPosition(@NotNull PositionTag position) {
        this.position = position;
    }
}
