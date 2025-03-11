
package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GildedRoseTest {

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

	@Test
	@DisplayName("1.1 Articulos Comunes -> La calidad disminuye antes de la fecha de venta")
	void testItemQualityBeforeSellIn() {
		Item[] items = new Item[] { new Item("Sword", 10, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(9, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}

	@Test
	@DisplayName("1.2 Articulos Comunes -> La calidad disminuye el doble después de la fecha de venta")
	void testItemQualityAfterSellIn() {
		Item[] items = new Item[] { new Item("Sword", 0, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(8, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}

	@Test
	@DisplayName("1.3 Articulos Comunes -> La calidad nunca es negativa")
	void testItemQualityNoNegative() {
		Item[] items = new Item[] { new Item("Sword", 10, 0) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(0, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}

	@Test
	@DisplayName("1.4 Articulos Comunes -> La calidad nunca pasa de 0 después de la fecha de venta")
	void testItemQualityAfterSellInNoNegative() {
		Item[] items = new Item[] { new Item("Sword", 0, 1) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(0, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}
	
	@Test
	@DisplayName("1.5 Resto de Articulos -> La calidad de un artículo común no es negativa después de pasar la fecha de venta")
	void testItemQualityNoNegativeAfterSellIn() {
		Item[] items = new Item[] { new Item("Sword", 0, 0) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(0, items[0].quality);
	}

	@Test
	@DisplayName("1.6 Articulos Comunes -> La calidad disminuye normalmente después de la fecha de venta")
	void testItemQualityDecreases() {
		Item[] items = new Item[] { new Item("Sword", 0, 5) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(3, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}

	@Test
	@DisplayName("1.7 Articulos Comunes -> La calidad no disminuye más de lo esperado después de la fecha de venta")
	void testItemQualityDoesNotDecreaseTooMuch() {
		Item[] items = new Item[] { new Item("Sword", 0, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(8, items[0].quality);
	}

	// -------------------- 2. Queso Brie --------------------

	@Test
	@DisplayName("2.1 Queso Brie -> La calidad aumenta antes de la fecha de venta")
	void testAgedBrieQualityBeforeSellIn() {
		Item[] items = new Item[] { new Item("Aged Brie", 10, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(11, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}

	@Test
	@DisplayName("2.2 Queso Brie -> La calidad aumenta después de la fecha de venta")
	void testAgedBrieQualityAfterSellIn() {
		Item[] items = new Item[] { new Item("Aged Brie", 0, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(12, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}

	@Test
	@DisplayName("2.3 Queso Brie -> La calidad no debe superar 50")
	void testAgedBrieQualityMax50() {
		Item[] items = new Item[] { new Item("Aged Brie", 10, 50) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(50, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}

	@Test
	@DisplayName("2.4 Queso Brie -> La calidad no pasa de 50 si se encuentra cerca del límite")
	void testAgedBrieQualityDoesNotExceed50() {
		Item[] items = new Item[] { new Item("Aged Brie", 0, 49) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(50, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}
	
	@Test
	@DisplayName("2.5 Queso Brie -> La calidad aumenta cuando el sellIn es 0")
	void testAgedBrieQualitySellInZero() {
		Item[] items = new Item[] { new Item("Aged Brie", 0, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(12, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}
	
	@Test
	@DisplayName("2.6 Queso brie -> La calidad no supera 50 cuando está cerca de 50")
	void testAgedBrieQualityDoesNotExceed501() {
		Item[] items = new Item[] { new Item("Aged Brie", 10, 49) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(50, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}
	

	@Test
	@DisplayName("2.7 Queso brie -> Calidad aumenta correctamente después de la fecha de venta")
	void testAgedBrieQualityAfterSellIn1() {
		Item[] items = new Item[] { new Item("Aged Brie", 0, 50) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(50, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}
	
	@Test
	@DisplayName("2.8 Aged Brie -> Asegura que la calidad no supera 50 después de múltiples actualizaciones")
	void testAgedBrieQuality50MultipleUpdates() {
	    Item[] items = new Item[] { new Item("Aged Brie", 10, 50) };
	    GildedRose app = new GildedRose(items);

	    for (int i = 0; i < 100; i++) {
	        app.updateQuality();
	    }

	    assertEquals(50, items[0].quality);
	}

	// -------------------- 3. Pases Backstage --------------------

	@Test
	@DisplayName("3.1 Pases Backstage -> Incrementa la calidad con más de 10 días para el concierto")
	void testBackstagePassesQualityMore10Days() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(11, items[0].quality);
		assertEquals(14, items[0].sellIn);
	}

	@Test
	@DisplayName("3.2 Pases Backstage -> Incrementa la calidad con 10 o menos días para el concierto")
	void testBackstagePassesQuality10DaysOrLess() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(12, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}

	@Test
	@DisplayName("3.3 Pases Backstage -> Incrementa la calidad con 5 o menos días para el concierto")
	void testBackstagePassesQuality5DaysOrLess() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(13, items[0].quality);
		assertEquals(4, items[0].sellIn);
	}

	@Test
	@DisplayName("3.4 Pases Backstage -> La calidad se reduce a 0 después del concierto")
	void testBackstagePassesQualityAfterConcert() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(0, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}

	@Test
	@DisplayName("3.5 Pases Backstage -> La calidad no debe superar 50")
	void testBackstagePassesQualityMax50() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(50, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}

	@Test
	@DisplayName("3.6 Pases Backstage -> El sellIn disminuye correctamente")
	void testBackstagePassesSellIn() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(14, items[0].sellIn);
	}

	@Test
	@DisplayName("3.7 Pases Backstage -> La calidad no pasa de 50 cuando tiene 50 y faltan menos de 5 dias para el concierto")
	void testBackstagePassesQualityNoExceeds50() {
		Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 3, 50) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(50, items[0].quality);
		assertEquals(2, items[0].sellIn);
	}

	// -------------------- 4. Sulfuras --------------------

	@Test
	@DisplayName("4.1 Sulfuras -> La calidad no cambia")
	void testSulfurasQualityDoesNotChange() {
		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(80, items[0].quality);
		assertEquals(10, items[0].sellIn);
	}

	@Test
	@DisplayName("4.2 Sulfuras -> El sellIn no cambia")
	void testSulfurasSellInDoesNotChange() {
		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(10, items[0].sellIn);
	}

	@Test
	@DisplayName("4.3 Sulfuras -> La calidad de Sulfuras nunca cambia aunque el sellIn pase")
	void testSulfurasQualityDoesNotChangeEvenAfterSellIn() {
		Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(80, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}

	// -------------------- 5. Conjured --------------------
	
	@Test
	@DisplayName("5.1 Conjured -> La calidad de los objetos Conjured se degradan al doble de velocidad")
	void testConjuredQuality() {
		Item[] items = new Item[] { new Item("Conjured Axe", 5, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(8, items[0].quality);
		assertEquals(4, items[0].sellIn);
	}
	
	@Test
	@DisplayName("5.1 Conjured -> La calidad de los objetos Conjured se degradan al doble de velocidad con sellIn caducado")
	void testConjuredQualityAfterSellIn() {
		Item[] items = new Item[] { new Item("Conjured Axe", 0, 10) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(6, items[0].quality);
		assertEquals(-1, items[0].sellIn);
	}
	
	@Test
	@DisplayName("5.1 Conjured -> La calidad de los objetos Conjured no puede ser negativa")
	void testConjuredQualityNoNegative() {
		Item[] items = new Item[] { new Item("Conjured Axe", 10, -1) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals(0, items[0].quality);
		assertEquals(9, items[0].sellIn);
	}
	
}
