package io.github.ellismatthew4.empireeconomy.cmd.conversations;

import io.github.ellismatthew4.empireeconomy.data.Listing;
import io.github.ellismatthew4.empireeconomy.data.Shop;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ListingPrompt extends ValidatingPrompt {
    private ItemStack item;
    private Inventory inv;
    private Shop shop;
    private int index;

    public ListingPrompt(Inventory inv, Shop shop, int index) {
        this.index = index;
        this.shop = shop;
        this.inv = inv;
    }

    private void getNextItem() {
        int prevIndex = Integer.valueOf(index);
        for (int i = index; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) {
                item = inv.getItem(i);
                inv.setItem(i, null);
                index = i;
                break;
            }
        }
    }

    private boolean nextItemExists() {
        for (int i = index; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) {
                index = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        getNextItem();
        return "§e[SYSTEM] How much for ["
                + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().toString())
                + "] x"
                + item.getAmount()
                +"? Or, type S to skip, or Q to exit.";
    }

    @Override public boolean blocksForInput(ConversationContext context) {
        return true;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return true;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        boolean isInteger = input.matches("-?\\d+") && Integer.parseInt(input) >= 0;
        if (item == null || item.getType() == Material.AIR) {
            return Prompt.END_OF_CONVERSATION;
        } else {
            switch(input.toLowerCase()) {
                case "q":
                    inv.setItem(index, item);
                    return Prompt.END_OF_CONVERSATION;
                case "s":
                    inv.setItem(index++, item);
                    if (!nextItemExists()) {
                        return Prompt.END_OF_CONVERSATION;
                    }
                    return new ListingPrompt(inv, shop, index);
                default:
                    if (isInteger) {
                        if (shop.listings.size() < 27) {
                            shop.addListing(new Listing(Integer.parseInt(input), item));
                            if (!nextItemExists()) {
                                return Prompt.END_OF_CONVERSATION;
                            }
                            return new ListingPrompt(inv, shop, index);
                        } else {
                            inv.setItem(index, item);
                            return new EndPrompt();
                        }
                    } else {
                        inv.setItem(index, item);
                        return new EndPrompt();
                    }
            }
        }
    }
}
