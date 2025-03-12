package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class GildedRoseTestRefactorized {

    @Test
    public void testUpdateQuality() {
        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20),
                new AgedBrie(2, 0),
                new BackstagePasses(15, 20),
                new Sulfuras(0, 80),
                new Conjured(3, 6)
        };

        GildedRoseRefactorized app = new GildedRoseRefactorized(items);
        app.updateQuality();
        
        assertAll("Verificando la actualización de calidad y sellIn",
                
                () -> assertEquals(19, items[0].quality, "Quality for +5 Dexterity Vest should be 19"),
                () -> assertEquals(9, items[0].sellIn, "SellIn for +5 Dexterity Vest should be 9"),

                
                () -> assertEquals(1, items[1].quality, "Quality for Aged Brie should be 1"),
                () -> assertEquals(1, items[1].sellIn, "SellIn for Aged Brie should be 1"),

                
                () -> assertEquals(21, items[2].quality, "Quality for Backstage Passes should be 21"),
                () -> assertEquals(14, items[2].sellIn, "SellIn for Backstage Passes should be 14"),

                
                () -> assertEquals(80, items[3].quality, "Quality for Sulfuras should remain 80"),
                () -> assertEquals(0, items[3].sellIn, "SellIn for Sulfuras should remain 0"),

                
                () -> assertEquals(4, items[4].quality, "Quality for Conjured Mana Cake should be 4"),
                () -> assertEquals(2, items[4].sellIn, "SellIn for Conjured Mana Cake should be 2")
            );
    }
}
