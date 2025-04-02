package com.gildedrose;

public class Sulfuras extends Item {

    public Sulfuras(int sellIn, int quality) {
        super("Sulfuras, Hand of Ragnaros", sellIn, quality);
    }

    @Override
    public void updateQuality() {
        // Sulfuras nunca cambia de calidad ni tiene sellIn
    }
}

