package com.example.model;

/**
 * Enum representing beverage flavors.
 * The system supports up to 15 flavors.
 * A few sample options are shown below, and can be extended.
 *
 * NOTE: Price does not change based on flavor.
 *
 * @author Elvin Xu
 */
public enum Flavor {
    COLA,
    TEA,
    JUICE,
    LEMONADE,
    WATER,
    ROOT_BEER,
    SPRITE,
    APPLE_JUICE,
    GRAPE_JUICE,
    MILKSHAKE,
    MANGO_JUICE,
    STRAWBERRY_JUICE,
    MILK,
    ORANGE_SODA,
    CHOCOLATE_MILK;

    /**
     * Converts the enum to a display name.
     *
     * @return Flavor name as string
     */
    @Override
    public String toString() {
        String name = name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
