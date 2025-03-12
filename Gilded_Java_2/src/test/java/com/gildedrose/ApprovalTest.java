package com.gildedrose;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.List;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

//@UseReporter(DiffReporter.class)
public class ApprovalTest {

	@Test

	void instantanea() {
		Item[] items = new Item[] { 
				new Item("+5 Dexterity Vest", 10, 20), 
				new Item("Aged Brie", 2, 0),
				new Item("Elixir of the Mongoose", 5, 7), 
				new Item("Sulfuras, Hand of Ragnaros", 0, 80),
				new Item("Sulfuras, Hand of Ragnaros", -1, 80),
				new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
				new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
				new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
				// this conjured item does not work properly yet
				new Item("Conjured Mana Cake", 3, 6) 
				};

		GildedRose app = new GildedRose(items);
		StringBuilder output = new StringBuilder();
		output.append("day,name, sellIn, quality\n");
		List.of(items).forEach(item -> output.append("0," + item + "\n"));
		for (int i = 1; i <= 31; i++) {
			app.updateQuality();
			for(Item item: items)
				output.append(i + "," + item + "\n");
		}
		Approvals.verify(output);
	}
}
