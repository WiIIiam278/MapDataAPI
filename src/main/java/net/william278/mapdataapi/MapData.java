package net.william278.mapdataapi;

import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NBTSerializer;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MapData {

    @NotNull
    private List<Integer> colors = new ArrayList<>(128 * 128);
    @NotNull
    private final List<MapBanner> banners = new ArrayList<>();
    @NotNull
    private final List<MapMarker> markers = new ArrayList<>();
    @NotNull
    private String dimension;
    private byte scale;
    private int xCenter;
    private int zCenter;

    private MapData(@NotNull CompoundTag mapData) {
        byte[] colors = mapData.getByteArrayTag("colors").getValue();
        for (byte color : colors) {
            this.colors.add((int) color);
        }
        this.dimension = mapData.getStringTag("dimension").getValue();
        this.scale = mapData.getByte("scale");
        this.xCenter = mapData.getInt("xCenter");
        this.zCenter = mapData.getInt("zCenter");
        mapData.getListTag("banners").asCompoundTagList().forEach(tag -> this.banners.add(new MapBanner(tag)));
        mapData.getListTag("frames").asCompoundTagList().forEach(tag -> this.markers.add(new MapMarker(tag)));
    }

    public MapData(@NotNull List<Integer> colors, @NotNull String dimension, byte scale, int xCenter, int zCenter,
                   @NotNull List<MapBanner> banners, @NotNull List<MapMarker> markers) {
        this.colors = colors;
        this.dimension = dimension;
        this.scale = scale;
        this.xCenter = xCenter;
        this.zCenter = zCenter;
        this.banners.addAll(banners);
        this.markers.addAll(markers);
    }

    public MapData(@NotNull List<Integer> colors, @NotNull String dimension, byte scale) {
        this.colors = colors;
        this.dimension = dimension;
        this.scale = scale;
    }

    @NotNull
    public static MapData fromNbt(@NotNull File file) throws IOException {
        final NamedTag mapRootTag = NBTUtil.read(file);
        final CompoundTag mapData = ((CompoundTag) mapRootTag.getTag()).getCompoundTag("data");
        return new MapData(mapData);
    }

    @NotNull
    public static MapData getFromFile(@NotNull File worldFolder, int mapId) throws IOException {
        return fromNbt(new File(worldFolder, "data/map_" + mapId + ".dat"));
    }

    @NotNull
    public static MapData fromByteArray(byte[] bytes) throws IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
            final NamedTag mapRootTag = new NBTDeserializer(true).fromStream(stream);
            final CompoundTag mapDataTag = ((CompoundTag) mapRootTag.getTag());
            return new MapData(mapDataTag);
        }
    }

    @NotNull
    public static MapData fromPixels(int[][] pixels, @NotNull String dimension, byte scale,
                                     @NotNull List<MapBanner> banners, @NotNull List<MapMarker> markers) {
        final List<Integer> colors = new ArrayList<>(128 * 128);
        for (int[] row : pixels) {
            for (int pixel : row) {
                colors.add(pixel);
            }
        }
        return new MapData(colors, dimension, scale, 0, 0, banners, markers);
    }

    @NotNull
    public static MapData fromPixels(int[][] pixels, @NotNull String dimension, byte scale) {
        return fromPixels(pixels, dimension, scale, List.of(), List.of());
    }


    @NotNull
    public NamedTag toNBT() {
        final CompoundTag mapData = new CompoundTag();

        // Set colors
        byte[] colors = new byte[128 * 128];
        for (int i = 0; i < this.colors.size(); i++) {
            colors[i] = this.colors.get(i).byteValue();
        }
        mapData.put("colors", new ByteArrayTag(colors));

        // Set metadata
        mapData.put("dimension", new StringTag(dimension));
        mapData.put("scale", new ByteTag(scale));
        mapData.put("xCenter", new IntTag(xCenter));
        mapData.put("zCenter", new IntTag(zCenter));
        mapData.put("locked", new ByteTag((byte) 1));
        mapData.put("trackingPosition", new ByteTag((byte) 0));
        mapData.put("unlimitedTracking", new ByteTag((byte) 0));

        // Add banners and markers
        final ListTag<CompoundTag> bannerList = new ListTag<>(CompoundTag.class);
        banners.forEach(banner -> bannerList.add(banner.toTag()));
        mapData.put("banners", bannerList);
        final ListTag<CompoundTag> markerList = new ListTag<>(CompoundTag.class);
        markers.forEach(marker -> markerList.add(marker.toTag()));
        mapData.put("frames", markerList);

        // Create root tag
        return new NamedTag("data", mapData);
    }

    public void toFile(@NotNull File worldFolder, int mapId) throws IOException {
        toFile(new File(worldFolder, "data/map_" + mapId + ".dat"));
    }

    public void toFile(@NotNull File file) throws IOException {
        NBTUtil.write(toNBT(), file);
    }

    public byte[] toBytes() {
        final NamedTag mapRoot = toNBT();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            new NBTSerializer(true).toStream(mapRoot, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public String toString() {
        return new String(toBytes(), StandardCharsets.ISO_8859_1);
    }

    @NotNull
    public Image toImage() {
        final BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                // Set the color of the pixel to the MapColor at the given position
                image.setRGB(x, y, getMapColorAt(x, y).getRGB());
            }
        }
        return image;
    }

    /**
     * Write the map to a bitmap image file (.PNG)
     *
     * @param file The file to write to
     * @throws IOException If an I/O error occurs
     */
    public void toImageFile(@NotNull File file) throws IOException {
        ImageIO.write((RenderedImage) toImage(), "png", file);
    }

    @NotNull
    public static MapData fromImage(@NotNull Image image) {
        // If the image is not 128x128, resize it
        image = image.getScaledInstance(128, 128, Image.SCALE_SMOOTH);

        // Convert the image to a BufferedImage
        final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics = bufferedImage.createGraphics();
        // Rotate the image by 270 degrees to make it look like the map in-game
        graphics.rotate(Math.toRadians(270), bufferedImage.getWidth() / 2.0, bufferedImage.getHeight() / 2.0);
        // Mirror the image horizontally to make it look like the map in-game
        graphics.scale(-1, 1);
        graphics.translate(-bufferedImage.getWidth(), 0);

        graphics.drawImage(image, 0, 0, null);

        // Get the colors from the image
        final List<Integer> colors = new ArrayList<>(128 * 128);
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                // Get the color of the pixel and find the closest color, setting the index of it
                final Color color = new Color(bufferedImage.getRGB(x, y));
                colors.add(MapColor.getClosestColorIndex(color));
            }
        }
        return new MapData(colors, "minecraft:overworld", (byte) 3);
    }

    public void fromImageFile(@NotNull File file) throws IOException {
        fromImage(ImageIO.read(file));
    }

    @NotNull
    public List<Integer> getColors() {
        return colors;
    }

    public int getColorAt(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > 127 || y < 0 || y > 127) {
            throw new IllegalArgumentException("x and y must be between 0 and 127");
        }
        final Integer color = colors.get(x + y * 128);
        return color == null ? 0 : color;
    }

    @NotNull
    public Color getMapColorAt(int x, int y) throws IllegalArgumentException {
        return MapColor.getColor(getColorAt(x, y));
    }


    @NotNull
    public List<MapBanner> getBanners() {
        return banners;
    }

    @NotNull
    public List<MapMarker> getMarkers() {
        return markers;
    }

    @NotNull
    public String getDimension() {
        return dimension;
    }

    public byte getScale() {
        return scale;
    }

    public int getXCenter() {
        return xCenter;
    }

    public int getZCenter() {
        return zCenter;
    }

    public void setColorAt(@NotNull Color color, int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > 127 || y < 0 || y > 127) {
            throw new IllegalArgumentException("x and y must be between 0 and 127");
        }
        colors.set(x + y * 128, MapColor.getClosestColorIndex(color));
    }

    public void setColorAt(@NotNull MapColor mapColor, int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > 127 || y < 0 || y > 127) {
            throw new IllegalArgumentException("x and y must be between 0 and 127");
        }
        colors.set(x + y * 128, (mapColor.ordinal() * 4));
    }

    public void addBanner(@NotNull MapBanner banner) {
        banners.add(banner);
    }

    public void addMarker(@NotNull MapMarker marker) {
        markers.add(marker);
    }

    public void setDimension(@NotNull String dimension) {
        // If the dimension is not a namespaced key, namespace it with minecraft:
        if (!dimension.contains(":")) {
            dimension = "minecraft:" + dimension;
        }

        this.dimension = dimension;
    }

    public void setScale(byte scale) {
        this.scale = scale;
    }

    public void setXCenter(int xCenter) {
        this.xCenter = xCenter;
    }

    public void setZCenter(int zCenter) {
        this.zCenter = zCenter;
    }
}
