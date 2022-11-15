package net.william278.mapdataapi;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Color palette for Minecraft maps, as of Minecraft 1.19.2
 */
public enum MapColor {

    TRANSPARENT(new Color(0, 0, 0, 0)),
    PALE_GREEN(new Color(127, 178, 56)),
    BEIGE(new Color(247, 233, 163)),
    SILVER(new Color(199, 199, 199)),
    BRIGHT_RED(new Color(255, 0, 0)),
    LIGHT_BLUE(new Color(160, 160, 255)),
    GRAY(new Color(167, 167, 167)),
    GREEN(new Color(0, 124, 0)),
    WHITE(new Color(255, 255, 255)),
    CLAY_GRAY(new Color(164, 168, 184)),
    PALE_BROWN(new Color(151, 109, 77)),
    DARK_GRAY(new Color(112, 112, 112)),
    DEEP_BLUE(new Color(64, 64, 255)),
    LIGHT_BRONZE(new Color(216, 127, 51)),
    EGGSHELL(new Color(255, 252, 245)),
    MUDDY_ORANGE(new Color(216, 127, 51)),
    BRIGHT_PURPLE(new Color(178, 76, 216)),
    BRIGHT_BLUE(new Color(102, 153, 216)),
    BRIGHT_YELLOW(new Color(229, 229, 51)),
    LIME(new Color(127, 204, 25)),
    HOT_PINK(new Color(242, 127, 165)),
    MUDDY_GRAY(new Color(76, 76, 76)),
    DARK_SILVER(new Color(153, 153, 153)),
    TURQUOISE(new Color(76, 127, 153)),
    DEEP_PURPLE(new Color(127, 63, 178)),
    BLUE(new Color(51, 76, 178)),
    DARK_BROWN(new Color(102, 76, 51)),
    MUDDY_GREEN(new Color(102, 127, 51)),
    DEEP_RED(new Color(153, 51, 51)),
    BLACK(new Color(25, 25, 25)),
    WARM_YELLOW(new Color(250, 238, 77)),
    AQUA(new Color(92, 219, 213)),
    SKY_BLUE(new Color(74, 128, 255)),
    LIGHT_GREEN(new Color(0, 217, 58)),
    BROWN(new Color(129, 86, 49)),
    MAROON(new Color(112, 2, 0));

    @NotNull
    public final Color colorValue;

    MapColor(@NotNull Color colorValue) {
        this.colorValue = colorValue;
    }

    private static final Color[] PALETTE = new Color[values().length * 4];

    static {
        for (MapColor color : values()) {
            final Color value = color.colorValue;
            PALETTE[color.ordinal() * 4] = offsetColor(value, 180);
            PALETTE[color.ordinal() * 4 + 1] = offsetColor(value, 255);
            PALETTE[color.ordinal() * 4 + 2] = value;
            PALETTE[color.ordinal() * 4 + 3] = offsetColor(value, 135);
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
    public static Color getColor(int id) {
        if (id < 0 || id >= PALETTE.length) {
            return TRANSPARENT.colorValue;
        }
        return PALETTE[id];
    }

    private static Color offsetColor(@NotNull Color value, final int rgbMultiplier) {
        return new Color(Math.round(value.getRed() * rgbMultiplier / 255f),
                Math.round(value.getGreen() * rgbMultiplier / 255f),
                Math.round(value.getBlue() * rgbMultiplier / 255f));
    }

    /**
     * Determine the distance between two colors
     *
     * @param color1  The color to compare to
     * @param color2 The color to compare with
     * @return The distance between the two colors
     */
    private static double colorDistance(@NotNull Color color1, @NotNull Color color2) {
        final int deltaR = color1.getRed() - color2.getRed();
        final int deltaG = color1.getGreen() - color2.getGreen();
        final int deltaB = color1.getBlue() - color2.getBlue();
        return (deltaR * deltaR) * 0.2989f
                + (deltaG * deltaG) * 0.5870f
                + (deltaB * deltaB) * 0.114f;

    }

    /**
     * Find the closest color in the palette to the given color
     *
     * @param color The color to find the closest match for
     * @return The closest color in the palette
     */
    @NotNull
    @SuppressWarnings("unused")
    public static Color getClosetColor(@NotNull Color color) {
        int closestColor = 0;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < PALETTE.length; i++) {
            double distance = colorDistance(color, PALETTE[i]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = i;
            }
        }
        return PALETTE[closestColor];
    }

    /**
     * Get the palette index of the closest color in the palette to the given color
     * @param color The color to find the closest match for
     * @return The palette index of the closest color in the palette
     */
    public static int getClosestColorIndex(@NotNull Color color) {
        int closestColor = 0;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < PALETTE.length; i++) {
            double distance = colorDistance(color, PALETTE[i]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = i;
            }
        }
        return closestColor;
    }

}
