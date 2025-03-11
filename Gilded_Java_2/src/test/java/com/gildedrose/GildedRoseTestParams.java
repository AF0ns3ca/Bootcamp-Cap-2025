package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTestParams {
	
	@Test
	void foo() {
	    Item[] items = new Item[] { new Item("foo", 0, 0) };
	    GildedRose app = new GildedRose(items);
	    app.updateQuality();
	    
	    assertEquals("foo, -1, 0", items[0].toString());
	    assertAll("Producto",
	    		() -> assertEquals("foo", items[0].name),
	    		() -> assertEquals(-1, items[0].sellIn, "SellIn"),
	    		() -> assertEquals(0, items[0].quality, "Quality")
	    		);
	}

    // -------------------- 1. Articulos Comunes --------------------
    
    @ParameterizedTest
    @CsvSource({
        "Sword, 10, 10, 9, 9",
        "Sword, 0, 10, 8, -1",
        "Sword, 10, 0, 0, 9",
        "Sword, 0, 1, 0, -1",
        "Sword, 0, 5, 3, -1"
    })
    @DisplayName("1 Articulos Comunes -> Test de calidad antes y después de la fecha de venta")
    void testItemQuality(String name, int sellIn, int quality, int expectedQuality, int expectedSellIn) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedQuality, items[0].quality);
        assertEquals(expectedSellIn, items[0].sellIn);
    }

    // -------------------- 2. Queso Brie --------------------

    @ParameterizedTest
    @CsvSource({
        "Aged Brie, 10, 10, 11, 9",
        "Aged Brie, 0, 10, 12, -1",
        "Aged Brie, 10, 50, 50, 9",
        "Aged Brie, 0, 49, 50, -1"
    })
    @DisplayName("2 Queso Brie -> La calidad de Aged Brie aumenta correctamente")
    void testAgedBrieQuality(String name, int sellIn, int quality, int expectedQuality, int expectedSellIn) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedQuality, items[0].quality);
        assertEquals(expectedSellIn, items[0].sellIn);
    }

    // -------------------- 3. Pases Backstage --------------------

    @ParameterizedTest
    @CsvSource({
        "Backstage passes to a TAFKAL80ETC concert, 15, 10, 11, 14",
        "Backstage passes to a TAFKAL80ETC concert, 10, 10, 12, 9",
        "Backstage passes to a TAFKAL80ETC concert, 5, 10, 13, 4",
        "Backstage passes to a TAFKAL80ETC concert, 0, 10, 0, -1"
    })
    @DisplayName("3 Pases Backstage -> Incrementa la calidad correctamente")
    void testBackstagePassesQuality(String name, int sellIn, int quality, int expectedQuality, int expectedSellIn) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedQuality, items[0].quality);
        assertEquals(expectedSellIn, items[0].sellIn);
    }

    // -------------------- 4. Sulfuras --------------------
    
    @Test
    @DisplayName("4 Sulfuras -> La calidad y el sellIn no cambian")
    void testSulfuras() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].quality);
        assertEquals(10, items[0].sellIn);
    }

    // -------------------- 5. Conjured --------------------

    @ParameterizedTest
    @CsvSource({
        "Conjured Axe, 5, 10, 8, 4",
        "Conjured Axe, 0, 10, 6, -1",
        "Conjured Axe, 10, -1, 0, 9"
    })
    @DisplayName("5 Conjured -> La calidad de los objetos Conjured se degrada correctamente")
    void testConjuredQuality(String name, int sellIn, int quality, int expectedQuality, int expectedSellIn) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedQuality, items[0].quality);
        assertEquals(expectedSellIn, items[0].sellIn);
    }
}
