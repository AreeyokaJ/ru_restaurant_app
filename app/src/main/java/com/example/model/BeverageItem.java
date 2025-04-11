package com.example.model;

import android.content.Context;

/**
 * Represents a beverage item with flavor and associated image resource for UI display.
 * Converts Flavor enums to lowercase hyphenated names to match image filenames.
 *
 * Author: Elvin Xu
 */
public class BeverageItem {
    private final Flavor flavor;
    private final int imageResId;

    /**
     * Constructs a BeverageItem based on the given flavor and looks up its image resource.
     *
     * @param context The context used to access resources
     * @param flavor  The flavor enum
     */
    public BeverageItem(Context context, Flavor flavor) {
        this.flavor = flavor;
        String imageName = flavor.name().toLowerCase();
        this.imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    /**
     * Gets the display name for the flavor (e.g., "Apple juice").
     *
     * @return Capitalized display string
     */
    public String getDisplayName() {
        return flavor.toString();
    }

    /**
     * Gets the resource ID for the image associated with this flavor.
     *
     * @return Drawable resource ID
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * Gets the raw flavor enum.
     *
     * @return The Flavor enum
     */
    public Flavor getFlavor() {
        return flavor;
    }
}
