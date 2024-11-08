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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MapDataTests {

    private static final int TEST_DATA_VERSION = 4082;

    @Test
    public void testMapDataReading() throws IOException {
        final File mapFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("map_example.dat")).getFile());
        Assertions.assertNotNull(MapData.fromNbt(mapFile).toBytes());
    }

    @Test
    public void testImageWriting() throws IOException {
        final File mapFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("map_example.dat")).getFile());
        final MapData mapData = MapData.fromNbt(mapFile);
        final Image image = mapData.toImage();
        Assertions.assertNotNull(image);

        // Write to out directory in resources, make dir if it doesn't exist
        final File outDir = new File("src/test/resources/out");
        if (!outDir.exists()) {
            Assertions.assertTrue(outDir.mkdir());
        }

        final File imageFile = new File(outDir, "map_example.png");
        ImageIO.write((RenderedImage) image, "png", imageFile);
    }

    @Test
    public void testImageConversion() throws IOException {
        final Image image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("image_example.png")));

        final MapData mapData = MapData.fromImage(TEST_DATA_VERSION, image);
        Assertions.assertNotNull(mapData.toBytes());

        final File outDir = new File("src/test/resources/out");
        if (!outDir.exists()) {
            Assertions.assertTrue(outDir.mkdir());
        }

        final File imageFile = new File(outDir, "image_example.png");
        ImageIO.write((RenderedImage) mapData.toImage(), "png", imageFile);
    }

    @Test
    public void testCompression() throws IOException {
        final File mapFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("map_example.dat")).getFile());
        final MapData mapData = MapData.fromNbt(mapFile);
        Assertions.assertNotNull(mapData);

        final byte[] mapDataString = mapData.toBytes();
        Assertions.assertNotNull(mapDataString);

        final MapData mapDataFromString = MapData.fromByteArray(TEST_DATA_VERSION, mapDataString);
        Assertions.assertNotNull(mapDataFromString);
    }

}