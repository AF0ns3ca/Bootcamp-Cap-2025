package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

//    @Test
//    void foo() {
//        Item[] items = new Item[] { new Item("foo", 0, 0) };
//        GildedRose app = new GildedRose(items);
//        app.updateQuality();
//        assertEquals("fixme", app.items[0].name);
//    }
    
    //Prueba paso dias baja quality y baja de uno en uno ya que no expira el SellIn
    @Test
    public void testDowngradeQuality() {
    	Item[] items = new Item[] { new Item("Potion", 5, 2) };
    	GildedRose app = new GildedRose(items);
    	app.updateQuality();
    	assertEquals(4, app.items[0].quality);
    }
    
    //Prueba fecha SellIn Expirado
    @Test
    public void testExpiredSellIn() {
    	Item[] items = new Item[] { new Item("Potion", 10, 2) };
    	GildedRose app = new GildedRose(items);
		for (int i=0; i<3; i++) {
			app.updateQuality();
		}
		assertEquals(6, app.items[0].quality);
    }
    
	//Prueba Quality negativa
	@Test
	public void testNegativeQuality() {
	    Item[] items = new Item[] { new Item("Potion", 0, 2) };
	    GildedRose app = new GildedRose(items);
		for (int i=0; i<3; i++) {
			app.updateQuality();
		}
		assertEquals(0, app.items[0].quality);
	}
    
    //Prueba paso dias sube quality Aged Brie
    @Test
    public void testAgedBrieQuality() {
    	//Creamos un Queso Brie con Calidad 5 y SellIn 2
    	Item[] items = new Item[] { new Item("Aged Brie", 5, 2) };
    	GildedRose app = new GildedRose(items);

    	app.updateQuality();
    	
    	assertEquals(6, app.items[0].quality);
    	
    }

}
