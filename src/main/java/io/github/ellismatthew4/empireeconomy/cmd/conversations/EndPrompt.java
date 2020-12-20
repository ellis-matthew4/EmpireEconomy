package io.github.ellismatthew4.empireeconomy.cmd.conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

public class EndPrompt extends MessagePrompt {

    @Override
    public String getPromptText(ConversationContext context) {
        return "§4[SYSTEM] Invalid input or shop is full, exiting batch job.";
    }

    @Override public boolean blocksForInput(ConversationContext context) {
        return false;
    }

    @Override
    protected Prompt getNextPrompt(ConversationContext context) {
        return Prompt.END_OF_CONVERSATION;
    }
}
