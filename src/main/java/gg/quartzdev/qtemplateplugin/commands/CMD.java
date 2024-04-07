package gg.quartzdev.qtemplateplugin.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qtemplateplugin.QTemplateAPI;
import org.bukkit.command.CommandSender;

public class CMD extends QCommand {
    public CMD(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        Sender.message(sender, "<green>" + QTemplateAPI.getName() + " v" + QTemplateAPI.getVersion());
        return true;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender commandSender, String[] strings) {
        return null;
    }
}
