package com.example.model;

/**
 * Class that represents Bread
 * @author Elvin Xu
 */
public enum Bread {
    BRIOCHE,
    WHEAT_BREAD,
    PRETZEL,
    BAGEL,
    SOURDOUGH;

    /**
     * Return a Bread enum type from string
     * @param string
     * @return Bread enum type
     */
    public static Bread fromStringtoBread(String string){
        string = string.toUpperCase();
        switch (string){
            case "BRIOCHE": return BRIOCHE;
            case "WHEAT_BREAD": return WHEAT_BREAD;
            case "PRETZEL": return PRETZEL;
            case "BAGEL": return BAGEL;
            case "SOURDOUGH": return SOURDOUGH;
            default: return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case BRIOCHE: return "Brioche";
            case WHEAT_BREAD: return "Wheat Bread";
            case PRETZEL: return "Pretzel";
            case BAGEL: return "Bagel";
            case SOURDOUGH: return "Sourdough";
            default: return "";
        }
    }
}
