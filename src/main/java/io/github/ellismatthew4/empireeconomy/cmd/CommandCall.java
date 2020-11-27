package io.github.ellismatthew4.empireeconomy.cmd;

import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandCall {
    public final Command command;
    public final String alias;
    public final List<CommandArgument> args;

    public CommandCall(Command command, String alias, String[] args) {
        this.command = command;
        this.alias = alias;
        Boolean concatenating = false;
        String concatenatedArg = "";
        List<CommandArgument> temp = new ArrayList<>();
        for(String arg: args) {
            if (concatenating == false) {
                if (arg.charAt(0) == '"') {
                    concatenatedArg += arg;
                    if (arg.charAt(arg.length() - 1) != '"') {
                        concatenatedArg += ' ';
                        concatenating = true;
                    } else {
                        temp.add(new CommandArgument(concatenatedArg));
                    }
                } else {
                    temp.add(new CommandArgument(arg));
                }
            } else {
                concatenatedArg += arg;
                if (arg.charAt(arg.length() - 1) == '"') {
                    concatenatedArg = concatenatedArg.substring(1, concatenatedArg.length()-1);
                    temp.add(new CommandArgument(concatenatedArg));
                    concatenatedArg = "";
                    concatenating = false;
                } else {
                    concatenatedArg += ' ';
                }
            }
        }
        this.args = temp;
    }

    public CommandArgument getArg(int index) {
        return this.args.get(index);
    }
}
