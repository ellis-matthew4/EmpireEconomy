package io.github.ellismatthew4.empireeconomy.data;

import io.github.ellismatthew4.empireeconomy.utils.TransactionService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Property {
    public List<Listing> listings;
    public Inventory menu;

    public Shop(String name, String owner) {
        super(name, owner);
        this.listings = new ArrayList<Listing>();
        init();
    }

    public void addListing(Listing l) {
        listings.add(l);
        init();
    }

    public void removeListing(int i) {
        listings.remove(i);
        init();
    }

    public boolean purchase(int i, Player buyer) {
        Listing l = listings.get(i);
        ItemStack item = l.item;
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
        if (listings.size() == 0) return null;
        TransactionService ts = TransactionService.getInstance();
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        for (Listing l : listings) {
            ItemStack item = l.item;
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Subtotal: " + l.subtotal);
            lore.add("Total: " + l.subtotal + ts.getTaxAddon(l.subtotal));
            meta.setLore(lore);
            item.setItemMeta(meta);
            result.add(item);
        }
        return result;
    }

    private void init() {
        menu = Bukkit.createInventory(null, 27, name);
        int i = 0;
        for (ItemStack item : getListings()) {
            menu.setItem(i++, item);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        InventoryView inv = e.getView();
        if (inv.getTitle().equals(name)) {
            e.setCancelled(true);
            p.closeInventory();
            boolean success = purchase(slot, p);
            if (!success) {
                p.sendMessage("§4[SYSTEM] You do not have enough money to buy that.");
            }
        }
    }
}
