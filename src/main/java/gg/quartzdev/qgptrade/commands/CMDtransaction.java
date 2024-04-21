package gg.quartzdev.qgptrade.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.transaction.TransactionManager;
import org.bukkit.command.CommandSender;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CMDtransaction extends QCommand {
    TransactionManager transactionManager;
    public CMDtransaction(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
        transactionManager = TradeAPI.getTransactionManager();
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        Set<UUID> transactionIds = transactionManager.getTransactionIds();
        Sender.message(sender, "<prefix> <blue>There are " + transactionIds.size() + "transactions");
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        if(args.length == 1){
            return transactionManager.getTransactionIds().stream().map(UUID::toString).collect(Collectors.toList());
        }
        return null;
    }
}
