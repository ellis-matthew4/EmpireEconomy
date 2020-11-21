package io.github.ellismatthew4.empireeconomy.data;

import org.bukkit.inventory.ItemStack;

public class Listing {
    public int subtotal;
    public ItemStack item;

    public Listing(int subtotal, ItemStack item) {
        this.subtotal = subtotal;
        this.item = item;
    }
}
