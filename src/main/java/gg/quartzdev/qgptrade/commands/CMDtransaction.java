package gg.quartzdev.qgptrade.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.transaction.Transaction;
import gg.quartzdev.qgptrade.transaction.TransactionManager;
import gg.quartzdev.qgptrade.util.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        if(args.length > 2){
            Sender.message(sender, Messages.SYNTAX_WITHDRAW.parse("label", label));
            return false;
        }
        if(args.length == 1){
            Sender.message(sender, "<prefix> <blue>There are <yellow>" + transactionIds.size() + " <blue>transactions");
            return true;
        }
        if(!(sender instanceof Player player)){
            Sender.message(sender, Messages.ERROR_PLAYER_ONLY_COMMAND);
            return false;
        }
        UUID argsId = UUID.fromString(args[1]);
        Transaction transaction = transactionManager.getTransaction(argsId);
        if(transaction == null){
            Sender.message(sender, "<prefix> <red>Error: Transaction not found");
            return false;
        }
        player.getInventory().addItem(transaction.slip());
        return true;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        if(args.length == 2){
            return transactionManager.getTransactionIds().stream().map(UUID::toString).collect(Collectors.toList());
        }
        return null;
    }
}
