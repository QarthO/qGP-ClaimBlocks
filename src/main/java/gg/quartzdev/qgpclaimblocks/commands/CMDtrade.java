package gg.quartzdev.qgpclaimblocks.commands;

import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import org.bukkit.command.CommandSender;

public class CMDtrade extends QCommand {
    public CMDtrade(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender commandSender, String[] strings) {
        return null;
    }
}
