package net.anware.minecraft.mods.colourscroller.scroll;

import net.anware.minecraft.mods.colourscroller.util.GameUtil;
import net.anware.minecraft.mods.colourscroller.util.Numpy;
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
	
	public int size() {
		return this.items.size();
	}
	
	public Item getItem(int index) {
		return this.items.get(index);
	}
	
	public Item getShifted(Item item, int shift) {
		int index = this.items.indexOf(item);
		if (index == -1) {
			return null;
		}
		return this.items.get(Numpy.roundIndex(index + shift, this.items.size()));
	}
	
	@SuppressWarnings("ConstantConditions")
	public Object serialize() {
		Map<String, Object> e = new LinkedHashMap<>();
		e.put("id", id);
		
		List<String> itemIDs = new ArrayList<>();
		for (Item item : items) {
			itemIDs.add(GameUtil.getID(item).toString());
		}
		e.put("items", itemIDs);
		
		return e;
	}

	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
}