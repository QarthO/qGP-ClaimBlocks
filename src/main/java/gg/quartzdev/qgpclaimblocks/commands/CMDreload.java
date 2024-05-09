package gg.quartzdev.qgpclaimblocks.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.util.Messages;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CMDreload extends QCommand {
    List<String> reloadableFiles = List.of("config", "messages", "transactions");
    public CMDreload(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        Sender.message(sender, "<blue>Args[<yellow>" + args.length + "<blue>] <gray>- <red>" + Arrays.toString(args));
//        reload all configs
        reloadMessages(sender);
        return true;
//        if (args.length == 1) {
//            reloadConfig(sender);
//            reloadMessages(sender);
//            reloadTransactions(sender);
//            return true;
//        }
//        return switch (args[1]) {
//            case "config" -> {
//                reloadConfig(sender);
//                yield true;
//            }
//            case "messages" -> {
//                reloadMessages(sender);
//                yield true;
//            }
//            case "transactions" -> {
//                reloadTransactions(sender);
//                yield true;
//            }
//            default -> {
//                Sender.message(sender, "<red>Unknown file to reload: <yellow>" + args[1] + "</red>");
//                yield false;
//            }
//        };
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return reloadableFiles;
        }
        return null;
    }

    public void reloadConfig(CommandSender sender){
        ClaimBlocksAPI.getConfig().reload();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "config"));
    }
    public void reloadMessages(CommandSender sender){
        ClaimBlocksAPI.loadCustomMessages();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "messages"));
    }
    public void reloadTransactions(CommandSender sender){
        ClaimBlocksAPI.getTransactionManager().loadTransactions();
        Sender.message(sender, Messages.FILE_RELOAD.parse(QPlaceholder.FILE,  "transactions"));
    }
}
