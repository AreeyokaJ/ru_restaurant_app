package com.example.model;

/**
 * Enum representing size options: SMALL, MEDIUM, LARGE.
 * Used by sides and beverages to determine final pricing.
 *
 * @author Elvin Xu
 */
public enum Size {
    SMALL,
    MEDIUM,
    LARGE;

    /**
     * Returns a display-friendly name for the size.
     *
     * @return Name of the size
     */
    @Override
    public String toString() {
        switch (this) {
            case SMALL: return "Small";
            case MEDIUM: return "Medium";
            case LARGE: return "Large";
            default: return "";
        }
    }
}
