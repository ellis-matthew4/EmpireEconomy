package io.github.ellismatthew4.empireeconomy.data;

import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Property {
    public List<Listing> listings;

    public Shop(String name, String owner) {
        super(name, owner);
        this.listings = new ArrayList<Listing>();
    }

    public void addListing(Listing l) {
        listings.add(l);
    }

    public void removeListing(int i) {
        listings.remove(i);
    }

    public boolean purchase(int i, Player buyer) {
        Listing l = listings.get(i);
        ItemStack item = l.asItemStack();
        int subtotal = l.subtotal;
        TransactionService ts = TransactionService.getInstance();
        boolean success = ts.silentTransact(buyer, Bukkit.getPlayer(owner), subtotal);
        if (success) {
            removeListing(i);
            buyer.getInventory().addItem(item);
        }
        return success;
    }

    private List<ItemStack> getListings() {
        TransactionService ts = TransactionService.getInstance();
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        for (Listing l : listings) {
            ItemStack item = l.asItemStack();
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Subtotal: " + l.subtotal);
            lore.add("Total: " + (l.subtotal + ts.getTaxAddon(l.subtotal)));
            meta.setLore(lore);
            item.setItemMeta(meta);
            result.add(item);
        }
        return result;
    }

    public Inventory getMenu() {
        Inventory menu = Bukkit.createInventory(null, 27, name);
        int i = 0;
        List<ItemStack> items = getListings();
        for (ItemStack item : items) {
            menu.setItem(i++, item);
        }
        return menu;
    }
}
