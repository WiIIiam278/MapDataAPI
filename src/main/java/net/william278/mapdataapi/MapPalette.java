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

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MapPalette {

    private final Color[] palette;

    MapPalette(@NotNull MapColor... colors) {
        this.palette = new Color[colors.length * 4];
        for (int i = 0; i < colors.length; ++i) {
            final MapColor value = colors[i];
            palette[i * 4] = value.getOffsetColor(180);
            palette[i * 4 + 1] = value.getOffsetColor(255);
            palette[i * 4 + 2] = value.getColor();
            palette[i * 4 + 3] = value.getOffsetColor(135);
        }
    }

    /**
     * Get a color from a Minecraft map palette index
     *
     * @param id The palette index
     * @return The color
     * @apiNote invalid colors will be returned as transparent
     */
    @NotNull
    public Color getColor(int id) {
        return palette[id < 0 || id >= palette.length ? 0 : id];
    }

    /**
     * Get the index of a map color as an int in this palette
     *
     * @param color the map color int
     * @return The color
     * @apiNote invalid colors will be returned as transparent
     */
    public int getIndexOf(@NotNull MapColor color) {
        for (int i = 0; i < palette.length; ++i) {
            if (palette[i].equals(color.getColor())) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Find the closest color in the palette to the given color
     *
     * @param color The color to find the closest match for
     * @return The closest color in the palette
     */
    @NotNull
    @SuppressWarnings("unused")
    public Color getClosetColor(@NotNull Color color) {
        int closestColor = 0;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < palette.length; i++) {
            double distance = MapColor.colorDistance(color, palette[i]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = i;
            }
        }
        return palette[closestColor];
    }

    /**
     * Get the palette index of the closest color in the palette to the given color
     *
     * @param color The color to find the closest match for
     * @return The palette index of the closest color in the palette
     */
    public int getClosestColorIndex(@NotNull Color color) {
        int closestColor = 0;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < palette.length; i++) {
            double distance = MapColor.colorDistance(color, palette[i]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = i;
            }
        }
        return closestColor;
    }

}
