package com.example.model;

import android.content.Context;

/**
 * Represents a menu item that can either be a beverage (Flavor) or a side (Side),
 * and provides access to its display name and associated image resource.
 *
 * Author: Elvin Xu
 */
public class SideOrBeverageItem {
    private final Flavor flavor;
    private final Side side;
    private final int imageResId;

    /**
     * Constructs a BeverageItem based on the given flavor and looks up its image resource.
     *
     * @param context The context used to access resources
     * @param flavor  The flavor enum
     */
    public SideOrBeverageItem(Context context, Flavor flavor) {
        this.flavor = flavor;
        this.side = null;
        String imageName = flavor.name().toLowerCase();
        this.imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    /**
     * Constructs a BeverageItem using a Side.
     *
     * @param context Context for resource lookup
     * @param side    The side item
     */
    public SideOrBeverageItem(Context context, Side side) {
        this.flavor = null;
        this.side = side;
        String imageName = side.name().toLowerCase();
        this.imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    /**
     * Returns the display name for the item (either Flavor or Side).
     *
     * @return Human-readable name
     */
    public String getDisplayName() {
        if (flavor != null) return flavor.toString();
        if (side != null) return side.toString();
        return "Unknown Item";
    }

    /**
     * Returns the image resource ID for the item.
     *
     * @return Drawable resource ID
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * Returns the flavor, if this item represents a beverage.
     *
     * @return Flavor enum or null
     */
    public Flavor getFlavor() {
        return flavor;
    }

    /**
     * Returns the side, if this item represents a side.
     *
     * @return Side enum or null
     */
    public Side getSide() {
        return side;
    }

    /**
     * Checks if this item is a beverage (Flavor).
     *
     * @return true if flavor, false otherwise
     */
    public boolean isFlavor() {
        return flavor != null;
    }
}
