package gg.quartzdev.qgpclaimblocks.datastore;

import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import gg.quartzdev.qgpclaimblocks.transaction.Transaction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class YMLtransactions extends QConfiguration {

    public YMLtransactions(JavaPlugin plugin, String fileName) {
        super(plugin, fileName, true);
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
    public void deleteTransaction(Transaction transaction){
        ConfigurationSection transactionSection = yamlConfiguration.getConfigurationSection("transactions");
        if(transactionSection == null){
            return;
        }
        transactionSection.set(transaction.getId().toString(), null);
        yamlConfiguration.set("transactions", transactionSection);
        save();
    }

    public @NotNull List<Transaction> loadTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        ConfigurationSection transactionSection = yamlConfiguration.getConfigurationSection("transactions");
        if(transactionSection == null){
            return transactions;
        }
        for(String transactionId : transactionSection.getKeys(false)){
            Transaction transaction = (Transaction) transactionSection.get(transactionId);
            if(transaction == null){
                continue;
            }
            transaction.initId((transactionId));
            transaction.createSlip();
            transactions.add(transaction);
        }
        return transactions;
    }
}
