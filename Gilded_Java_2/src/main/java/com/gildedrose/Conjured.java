package com.gildedrose;

public class Conjured extends Item {

    public Conjured(int sellIn, int quality) {
        super("Conjured", sellIn, quality);
    }

    @Override
    public void updateQuality() {
        if (quality > 0) {
            quality -= (sellIn > 0) ? 2 : 4;
        }
        sellIn--;
        if (quality < 0) {
            quality = 0;
        }
    }
}

