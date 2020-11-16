package io.github.ellismatthew4.empireeconomy.cmd.conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import java.util.Arrays;

public class PaymentPrompt extends ValidatingPrompt {
    private int cost;
    private String[] validInputs;

    public PaymentPrompt(int cost) {
        this.cost = cost;
        this.validInputs = new String[]{"yes", "y", "confirm", "ok", "okay", "yea", "yeah", "accept", "k"};
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "§e[SYSTEM] This operation will cost you $" + cost + ". Is this ok?";
    }

    @Override public boolean blocksForInput(ConversationContext context) {
        return true;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return Arrays.stream(validInputs).anyMatch(input.toLowerCase()::equals);
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        return Prompt.END_OF_CONVERSATION;
    }
}
