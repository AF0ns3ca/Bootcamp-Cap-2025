
package com.gildedrose;

// import java.util.List;

public class GildedRoseRefactorized {
    private Item[] items;

    public GildedRoseRefactorized(Item[] items) {
        this.items = items; 
    }

    public void updateQuality() {
        for (Item item : items) {
            item.updateQuality();
        }
    }
}

//
//    public void updateQuality() {
//        for (int i = 0; i < items.length; i++) {
//            Item item = items[i];
//            if (item.name.contains("Conjured")) {
//                updateConjuredItem(item);
//            } else if (item.name.equals("Aged Brie")) {
//                updateAgedBrie(item);
//            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                updateBackstagePasses(item);
//            } else {
//                updateRegularItem(item);
//            }
//            
//            item.sellIn -= 1;
//
//            if (item.sellIn < 0) {
//                handleExpiredItem(item);
//            }
//        }
//    }
//
//    private void updateConjuredItem(Item item) {
//        if (item.quality > 0) {
//            item.quality -= (item.sellIn > 0) ? 2 : 4;
//        }
//        item.quality = Math.max(item.quality, 0);
//    }
//
//    private void updateAgedBrie(Item item) {
//        if (item.quality < 50) {
//            item.quality += 1;
//        }
//    }
//
//    private void updateBackstagePasses(Item item) {
//        if (item.quality < 50) {
//            item.quality += 1;
//            if (item.sellIn < 11) {
//                item.quality += 1;
//            }
//            if (item.sellIn < 6) {
//                item.quality += 1;
//            }
//        }
//    }
//
//    private void updateRegularItem(Item item) {
//        if (item.quality > 0 && !item.name.equals("Sulfuras, Hand of Ragnaros")) {
//            item.quality -= 1;
//        }
//    }
//
//    private void handleExpiredItem(Item item) {
//        if (item.name.equals("Aged Brie")) {
//            if (item.quality < 50) {
//                item.quality += 1;
//            }
//        } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//            item.quality = 0;
//        } else {
//            if (item.quality > 0 && !item.name.equals("Sulfuras, Hand of Ragnaros")) {
//                item.quality -= 1;
//            }
//        }
//        item.quality = Math.max(item.quality, 0);
//    }
//}
