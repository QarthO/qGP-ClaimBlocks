package gg.quartzdev.qgptrade.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.util.Messages;
import org.bukkit.command.CommandSender;

public class CMDreload extends QCommand {
    public CMDreload(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        TradeAPI.getConfig().reload();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "config"));
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        return null;
    }
}