package com.gildedrose;

public class BackstagePasses extends Item {

    public BackstagePasses(int sellIn, int quality) {
        super("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    }

    @Override
    public void updateQuality() {
        if (quality < 50) {
            quality++;
            if (sellIn < 11) {
                if (quality < 50) quality++;
            }
            if (sellIn < 6) {
                if (quality < 50) quality++;
            }
        }
        sellIn--;
        if (sellIn < 0) {
            quality = 0;
        }
    }
}
