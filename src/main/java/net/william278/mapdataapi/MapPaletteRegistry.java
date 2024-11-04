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

import java.util.Map;
import java.util.TreeMap;

public final class MapPaletteRegistry {

    private static final Map<Integer, MapPalette> PALETTES = new TreeMap<>();

    public static MapPalette getPalette(int dataVersion) {
        return PALETTES.entrySet().stream().filter((k) -> k.getKey() >= dataVersion).findFirst()
                .orElse(PALETTES.entrySet().stream().findFirst().orElseThrow(
                        () -> new IllegalStateException("No palette found for data version %s".formatted(dataVersion)))
                ).getValue();
    }

    static {
        // Minecraft 1.21.3
        PALETTES.put(4082, new MapPalette(
                r(0, 0, 0, 0),
                r(127, 178, 56),
                r(247, 233, 163),
                r(199, 199, 199),
                r(255, 0, 0),
                r(160, 160, 255),
                r(167, 167, 167),
                r(0, 124, 0),
                r(255, 255, 255),
                r(164, 168, 184),
                r(151, 109, 77),
                r(112, 112, 112),
                r(64, 64, 255),
                r(143, 119, 72),
                r(255, 252, 245),
                r(216, 127, 51),
                r(178, 76, 216),
                r(102, 153, 216),
                r(229, 229, 51),
                r(127, 204, 25),
                r(242, 127, 165),
                r(76, 76, 76),
                r(153, 153, 153),
                r(76, 127, 153),
                r(127, 63, 178),
                r(51, 76, 178),
                r(102, 76, 51),
                r(102, 127, 51),
                r(153, 51, 51),
                r(25, 25, 25),
                r(250, 238, 77),
                r(92, 219, 213),
                r(74, 128, 255),
                r(0, 217, 58),
                r(129, 86, 49),
                r(112, 2, 0),
                r(209, 177, 161),
                r(159, 82, 36),
                r(149, 87, 108),
                r(112, 108, 138),
                r(186, 133, 36),
                r(103, 117, 53),
                r(160, 77, 78),
                r(57, 41, 35),
                r(135, 107, 98),
                r(87, 92, 92),
                r(122, 73, 88),
                r(76, 62, 92),
                r(76, 50, 35),
                r(76, 82, 42),
                r(142, 60, 46),
                r(37, 22, 16),
                r(189, 48, 49),
                r(148, 63, 97),
                r(92, 25, 29),
                r(22, 126, 134),
                r(58, 142, 140),
                r(86, 44, 62),
                r(20, 180, 133),
                r(100, 100, 100),
                r(216, 175, 147),
                r(127, 167, 150)
        ));

        // Minecraft 1.19.2
        PALETTES.put(3120, new MapPalette(
                r(0, 0, 0, 0),
                r(127, 178, 56),
                r(247, 233, 163),
                r(199, 199, 199),
                r(255, 0, 0),
                r(160, 160, 255),
                r(167, 167, 167),
                r(0, 124, 0),
                r(255, 255, 255),
                r(164, 168, 184),
                r(151, 109, 77),
                r(112, 112, 112),
                r(64, 64, 255),
                r(216, 127, 51),
                r(255, 252, 245),
                r(216, 127, 51),
                r(178, 76, 216),
                r(102, 153, 216),
                r(229, 229, 51),
                r(127, 204, 25),
                r(242, 127, 165),
                r(76, 76, 76),
                r(153, 153, 153),
                r(76, 127, 153),
                r(127, 63, 178),
                r(51, 76, 178),
                r(102, 76, 51),
                r(102, 127, 51),
                r(153, 51, 51),
                r(25, 25, 25),
                r(250, 238, 77),
                r(92, 219, 213),
                r(74, 128, 255),
                r(0, 217, 58),
                r(129, 86, 49),
                r(112, 2, 0)
        ));
    }

    @NotNull
    private static MapColor r(int r, int g, int b) {
        return new MapColor(r, g, b);
    }

    @NotNull
    private static MapColor r(int r, int g, int b, int t) {
        return new MapColor(r, g, b, t);
    }

}
