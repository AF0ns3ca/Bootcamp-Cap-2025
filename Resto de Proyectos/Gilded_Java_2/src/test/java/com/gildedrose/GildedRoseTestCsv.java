package com.gildedrose;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTestCsv {

	@ParameterizedTest(name = "{0} => sellIn: {1} quality: {2} -> sellIn: {3} quality: {4}")
	@CsvFileSource(resources = "/items.csv", numLinesToSkip = 1)
	void dataSourceTest(String producto, int sellIn, int quality, int sellInResult, int qualityResult) {
		String name = producto.replace("\'","");
		Item product = new Item(name, sellIn, quality);
		GildedRose app = new GildedRose(new Item[] {
			product	
		});
		app.updateQuality();
		assertAll(name,
				() -> assertEquals(name, product.name, "name"),
				() -> assertEquals(sellInResult, product.sellIn, "sellIn"),
				() -> assertEquals(qualityResult, product.quality, "quality")
				);
	}
}
