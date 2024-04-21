package gg.quartzdev.qgptrade.transaction;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.storage.YMLtransactions;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TransactionManager {

    private final HashMap<UUID, Transaction> transactions;
    private final YMLtransactions transactionStorage;


    public TransactionManager(){
        transactions = new HashMap<>();
        transactionStorage = new YMLtransactions(TradeAPI.getPlugin(), "transactions.yml");
        loadTransactions();
    }

    public void loadTransactions(){
        transactions.clear();
        for(Transaction transaction : transactionStorage.loadTransactions()){
            transactions.put(transaction.getId(), transaction);
        }
    }

    public @Nullable Transaction getTransaction(UUID transactionId){
        return transactions.get(transactionId);
    }

    public @NotNull Transaction createTransaction(Player creator, int claimBlocks){
        Transaction transaction = new Transaction(creator, claimBlocks);
        transactions.put(transaction.getId(), transaction);
        transactionStorage.saveTransaction(transaction);
        return transaction;
    }

    public void closeTransaction(Transaction transaction){
        transactionStorage.deleteTransaction(transaction);
        transactions.remove(transaction.getId());
    }

    public Set<UUID> getTransactionIds(){
        return transactions.keySet();
    }

}
