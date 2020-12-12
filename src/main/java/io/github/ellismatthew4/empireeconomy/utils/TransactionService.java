package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.EmpireEconomy;
import io.github.ellismatthew4.empireeconomy.cmd.conversations.PaymentPrompt;
import io.github.ellismatthew4.empireeconomy.permissions.EmperorService;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.function.Supplier;

public class TransactionService {
    private static TransactionService instance;
    private JavaPlugin plugin = EmpireEconomy.plugin;

    public static TransactionService getInstance() {
        return instance == null ? newInstance() : instance;
    }

    private static TransactionService newInstance() {
        instance = new TransactionService();
        return instance;
    }

    public boolean transact(Player from, Player to, int amount, Supplier<Boolean> callback) {
        int tax = getTaxAddon(amount);
        int total = tax + amount;
        Map<String, Integer> currency = DataStoreService.getInstance().data.currency;
        if (currency.get(from.getDisplayName()) < total) {
            if (EmperorService.getInstance().isEmperor(from.getDisplayName())) {
                currency.put(from.getDisplayName(), total);
            } else {
                return false;
            }
        }
        ConversationFactory cf = new ConversationFactory(plugin);
        cf.withFirstPrompt(new PaymentPrompt(total))
                .withTimeout(5)
                .addConversationAbandonedListener((event) -> {
                    boolean success = event.gracefulExit();
                    if (success) {
                        currency.put(
                                from.getDisplayName(),
                                currency.get(from.getDisplayName()) - total
                        );
                        currency.put(
                                to.getDisplayName(),
                                currency.get(to.getDisplayName()) + amount
                        );
                        currency.put(
                                EmperorService.getInstance().getEmpName(),
                                currency.get(EmperorService.getInstance().getEmpName()) + tax
                        );
                        callback.get();
                    } else {
                        from.sendMessage("§4[SYSTEM] Transaction timed out.");
                    }
                    return;
                });
        Conversation convo = cf.buildConversation(from);
        convo.begin();
        return true;
    }

    public boolean transactTaxFree(Player from, Player to, int amount, Supplier<Boolean> callback) {
        int total = amount;
        Map<String, Integer> currency = DataStoreService.getInstance().data.currency;
        if (currency.get(from.getDisplayName()) < total) {
            return false;
        } else {
            ConversationFactory cf = new ConversationFactory(plugin);
            cf.withFirstPrompt(new PaymentPrompt(total))
                    .withTimeout(5)
                    .addConversationAbandonedListener((event) -> {
                        boolean success = event.gracefulExit();
                        if (success) {
                            currency.put(
                                    from.getDisplayName(),
                                    currency.get(from.getDisplayName()) - total
                            );
                            currency.put(
                                    to.getDisplayName(),
                                    currency.get(to.getDisplayName()) + amount
                            );
                            callback.get();
                        } else {
                            from.sendMessage("§4[SYSTEM] Transaction timed out.");
                        }
                        return;
                    });
            Conversation convo = cf.buildConversation(from);
            convo.begin();
            return true;
        }
    }

    public boolean silentTransact(Player from, Player to, int amount) {
        int tax = getTaxAddon(amount);
        int total = tax + amount;
        Map<String, Integer> currency = DataStoreService.getInstance().data.currency;
        if (currency.get(from.getDisplayName()) < total) {
            return false;
        } else {
            currency.put(
                from.getDisplayName(),
                currency.get(from.getDisplayName()) - total
            );
            currency.put(
                    to.getDisplayName(),
                    currency.get(to.getDisplayName()) + amount
            );
            currency.put(
                    EmperorService.getInstance().getEmpName(),
                    currency.get(EmperorService.getInstance().getEmpName()) + tax
            );
        }
        return true;
    }

    public int getTaxAddon(int price) {
        return (int) Math.floor(price * DataStoreService.getInstance().data.salesTax);
    }

    public boolean setSalesTax(float value) {
        if (value < 0 || value > 1) return false;
        DataStoreService.getInstance().data.salesTax = value;
        return true;
    }
}
