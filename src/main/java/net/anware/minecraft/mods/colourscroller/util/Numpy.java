package net.anware.minecraft.mods.colourscroller.util;

public class Numpy {
	
	public static int roundIndex(int index, int size) {
		if (index < 0) {
			index = index * (1 - size);
		}
		return index % size;
	}
}
