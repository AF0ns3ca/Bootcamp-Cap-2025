package com.gildedrose;

public class Item {

    public String name;

    public int sellIn;

    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    
   public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSellIn() {
		return sellIn;
	}

	public void setSellIn(int sellIn) {
		this.sellIn = sellIn;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	

    public void updateQuality() {
    	
    	
    	this.setQuality((this.sellIn>0) ? (this.quality -= 1) : (this.quality -= 2));
        this.sellIn --;
    }

@Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
