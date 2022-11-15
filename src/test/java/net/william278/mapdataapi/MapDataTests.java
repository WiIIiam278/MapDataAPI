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

    @Test
    public void testMapDataReading() throws IOException {
        final File mapFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("map_example.dat")).getFile());
        Assertions.assertNotNull(MapData.fromNbt(mapFile).toString());
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

        final MapData mapData = MapData.fromImage(image);
        Assertions.assertNotNull(mapData.toString());

        final File outDir = new File("src/test/resources/out");
        if (!outDir.exists()) {
            Assertions.assertTrue(outDir.mkdir());
        }

        final File imageFile = new File(outDir, "image_example.png");
        ImageIO.write((RenderedImage) mapData.toImage(), "png", imageFile);
    }

}
