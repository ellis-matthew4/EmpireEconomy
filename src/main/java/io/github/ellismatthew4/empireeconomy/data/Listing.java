package io.github.ellismatthew4.empireeconomy.data;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Listing {
    public int subtotal;
    public Map<String, Object> item;

    public Listing(int subtotal, ItemStack item) {
        this.subtotal = subtotal;
        this.item = item.serialize();
    }

    public ItemStack asItemStack() {
        return ItemStack.deserialize(item);
    }
}
