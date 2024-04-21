package gg.quartzdev.qgptrade.storage;

import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import gg.quartzdev.qgptrade.transaction.Transaction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YMLtransactions extends QConfiguration {

    final List<Transaction> transactions;
    public YMLtransactions(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
        transactions = new ArrayList<>();
    }

    @Override
    public void loadAllData() {

    }

    @Override
    public void saveAllData() {

    }

    public void saveTransaction(Transaction transaction){
        yamlConfiguration.set("transactions." + transaction.getId(), transaction);
        save();
    }

    public void loadTransactions(){
        transactions.clear();
        ConfigurationSection transactionSection = yamlConfiguration.getConfigurationSection("transactions");
        if(transactionSection == null){
            return;
        }
        for(String transactionId : transactionSection.getKeys(false)){
            Transaction transaction = (Transaction) yamlConfiguration.get(transactionId);
            if(transaction == null){
                continue;
            }
            transaction.initId((transactionId));
            transactions.add(transaction);
        }
    }

    public @NotNull List<Transaction> getTransactions(){
        return transactions;
    }
}
