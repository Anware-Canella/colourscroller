package net.anware.minecraft.mods.colourscroller.scroll;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Scroll {
	public Scroll(String id, List<Item> items) {
		this.id = id;
		this.items = items;
	}
	public Scroll(String id, Item... items) {
		this.id = id;
		this.items = new ArrayList<>(List.of(items));
	}
	
	protected final String id;
	protected final List<Item> items;
	
	public Item getPrevious() {
	
	}
	
	public Object serialize() {
		Map<String, Object> e = new LinkedHashMap<>();
		e.put("id", id);
		e.put("items", this.items);
		return e;
	}
}
