package gg.quartzdev.qtemplateplugin.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qtemplateplugin.QTemplateAPI;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CMDset extends QCommand {
    public CMDset(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        Sender.message(sender, Arrays.toString(args));
        if(args.length > 1){
            boolean temp = args[1].equalsIgnoreCase("true");
            QTemplateAPI.getConfig().setRequiresPermission(temp);
        }
        Sender.message(sender, "<blue>requires permission: " + QTemplateAPI.getConfig().requiresPermisison());
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        return null;
    }
}
