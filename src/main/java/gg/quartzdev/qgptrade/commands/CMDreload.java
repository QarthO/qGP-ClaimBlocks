package gg.quartzdev.qgptrade.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.qgptrade.TradeAPI;
import org.bukkit.command.CommandSender;

public class CMDreload extends QCommand {
    public CMDreload(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        TradeAPI.getConfig().reload();
        Sender.message(sender, GenericMessages.RELOAD_COMPLETE.parse(QPlaceholder.FILE,  "config.yml"));
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        return null;
    }
}
