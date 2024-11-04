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

/**
 * Color palette for Minecraft maps, as of Minecraft 1.19.2
 */
public class MapColor {

    private final int red;
    private final int green;
    private final int blue;
    private final Color color;

    public MapColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.color = new Color(this.red, this.green, this.blue);
    }

    public MapColor(int red, int green, int blue, int transparency) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.color = new Color(this.red, this.green, this.blue, transparency);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @NotNull
    public Color getColor() {
        return color;
    }

    @NotNull
    Color getOffsetColor(int rgbMultiplier) {
        return new Color(
                Math.round(red * rgbMultiplier / 255f),
                Math.round(green * rgbMultiplier / 255f),
                Math.round(blue * rgbMultiplier / 255f)
        );
    }

    /**
     * Determine the distance between two colors
     *
     * @param color1 The color to compare to
     * @param color2 The color to compare with
     * @return The distance between the two colors
     */
    static double colorDistance(@NotNull Color color1, @NotNull Color color2) {
        final int deltaR = color1.getRed() - color2.getRed();
        final int deltaG = color1.getGreen() - color2.getGreen();
        final int deltaB = color1.getBlue() - color2.getBlue();
        return (deltaR * deltaR) * 0.2989f
               + (deltaG * deltaG) * 0.5870f
               + (deltaB * deltaB) * 0.114f;

    }

}
