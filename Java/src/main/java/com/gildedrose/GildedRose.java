package com.gildedrose;

import java.util.function.Consumer;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        Consumer<Item> increaseQuality = (item) -> { if(item.quality < 50){item.quality ++; }};
        Consumer<Item> decreaseQuality = (item) -> { if(item.quality >0 ){item.quality --; }};
        Consumer<Item> updateNormalItem = (item) -> {
            decreaseQuality.accept(item);
            if(item.sellIn <=0){
                decreaseQuality.accept(item);
            }
        };

        Consumer<Item> updateAgedBrieItem = (item) -> {
            increaseQuality.accept(item);
            if(item.sellIn <=0){
                increaseQuality.accept(item);
            }
        };

        Consumer<Item>  updateBackstagePassItem = (item) -> {
            if(item.sellIn > 10){
                increaseQuality.accept(item);
            }else if(item.sellIn > 5){
                increaseQuality.accept(item);
                increaseQuality.accept(item);
            }else if(item.sellIn > 0){
                increaseQuality.accept(item);
                increaseQuality.accept(item);
                increaseQuality.accept(item);
            }else {
                item.quality =0 ;
            }

        };
        Consumer<Item>  updateSulfurasItem = (item) -> {};
        Consumer<Item>  updateConjuredItem = (item) -> {
            decreaseQuality.accept(item);
            decreaseQuality.accept(item);
            if(item.sellIn <=0){
                decreaseQuality.accept(item);
                decreaseQuality.accept(item);
            }

        };
        boolean updateSellIn = true ;
        for (int i = 0; i < items.length; i++) {
            updateSellIn = true ;
            if(items[i].name.equals("Aged Brie")){
                updateAgedBrieItem.accept(items[i]);
            }else if(items[i].name.equals("Sulfuras, Hand of Ragnaros")){
                updateSellIn = false ;
                updateSulfurasItem.accept(items[i]);
            }else if(items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")){
                updateBackstagePassItem.accept(items[i]);
            }else if(items[i].name.startsWith("Conjured")){
                updateConjuredItem.accept(items[i]);
            }else {
                updateNormalItem.accept(items[i]);
            }
            if(updateSellIn) {
                items[i].sellIn--;
            }
        }
    }
}
