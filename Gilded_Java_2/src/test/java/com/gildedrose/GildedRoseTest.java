package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;

class GildedRoseTest {

//    @Test
//    void foo() {
//        Item[] items = new Item[] { new Item("foo", 0, 0) };
//        GildedRose app = new GildedRose(items);
//        app.updateQuality();
//        assertEquals("fixme", app.items[0].name);
//    }
	
	//-----------------------------------------------------------------------------------------------------
	@Test
	@DisplayName("Test degradación Sulfuras")
	void testDowngradeSulfuras() {
		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 5, 80) };
    	GildedRose app = new GildedRose(items);
    	app.updateQuality();
    	assertEquals(80, app.items[0].quality);
	}
	
	@Test
	@DisplayName("Test degradación Sulfuras con SellIn negativa y Quality menor")
	void testDowngradeSulfurasNegative() {
		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 66) };
    	GildedRose app = new GildedRose(items);
    	app.updateQuality();
    	assertEquals(80, app.items[0].quality);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	
	@Test
    @DisplayName("Test calidad superior a 50")
    void testOverQuality() {
    	Item[] items = new Item[] { new Item("Axe", 2, 55) };
    	GildedRose app = new GildedRose(items);
    	app.updateQuality();
    	assertEquals(50, app.items[0].quality);
    }
	
	
    //Prueba paso dias baja quality y baja de uno en uno ya que no expira el SellIn
    @Test
    @DisplayName("Test degradación calidad normal")
    void testDowngradeQuality() {
    	Item[] items = new Item[] { new Item("Potion", 2, 5) };
    	GildedRose app = new GildedRose(items);
    	app.updateQuality();
    	assertEquals(4, app.items[0].quality);
    }
    
    //Prueba fecha SellIn Expirado
    @Test
    @DisplayName("Test degradación calidad con SellIn expirado")
    void testExpiredSellIn() {
    	Item[] items = new Item[] { new Item("Map", 2, 10) };
    	GildedRose app = new GildedRose(items);
		for (int i=0; i<3; i++) {
			app.updateQuality();
		}
		assertEquals(6, app.items[0].quality);
    }
    
	//Prueba Quality negativa
	@Test
	@DisplayName("Test degradación calidad negativa")
	void testNegativeQuality() {
	    Item[] items = new Item[] { new Item("Sword", 2, 0) };
	    GildedRose app = new GildedRose(items);
		for (int i=0; i<3; i++) {
			app.updateQuality();
		}
		assertEquals(0, app.items[0].quality);
	}
    
    //Prueba paso dias sube quality Aged Brie
    @Test
    @DisplayName("Test degradación calidad Queso Brie")
    void testAgedBrieQuality() {
    	//Creamos un Queso Brie con Calidad 5 y SellIn 2
    	Item[] items = new Item[] { new Item("Aged Brie", 2, 5) };
    	GildedRose app = new GildedRose(items);

    	app.updateQuality();
    	
    	assertEquals(6, app.items[0].quality);
    	
    }

}
