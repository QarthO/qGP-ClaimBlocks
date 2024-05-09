package gg.quartzdev.qgpclaimblocks.util;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.datastore.YMLconfig;
import gg.quartzdev.qgpclaimblocks.datastore.ConfigPath;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultUtil {

    Economy eco;
    YMLconfig config;

    public VaultUtil(){
        setupEconomy();
        config = ClaimBlocksAPI.getConfig();
    }

    public boolean isSetup(){
        return eco != null;
    }

    private void setupEconomy(){
        if(Bukkit.getPluginManager().getPlugin("Vault") == null){
            QLogger.info(Messages.WARNING_VAULT_NOT_FOUND);
            return;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            QLogger.error(Messages.ERROR_VAULT_HOOK);
            return;
        }
        eco = rsp.getProvider();
        QLogger.info(Messages.VAULT_HOOKED);
    }

    public double getBalance(Player player){
        return eco.getBalance(player);
    }

    public void withdrawTransaction(Player player, int claimBlocks) throws InsufficientFundsException{
        double currentBalance = eco.getBalance(player);
        double tax = config.get(ConfigPath.TAX_WITHDRAW, 1);
        double fee = config.get(ConfigPath.FEE_WITHDRAW, 0);
        final double cost = claimBlocks * tax + fee;
        if(currentBalance < cost){
            throw new InsufficientFundsException(Messages.ERROR_INSUFFICIENT_FUNDS.get());
        }
        eco.withdrawPlayer(player, claimBlocks * cost);
    }

    public void transferTransaction(Player player, Player recipient, int claimBlocks) throws InsufficientFundsException{
        double currentBalance = eco.getBalance(player);
        double tax = config.get(ConfigPath.TAX_TRANSFER, 1);
        double fee = config.get(ConfigPath.FEE_TRANSFER, 0);
        final double cost = claimBlocks * tax + fee;
        if(currentBalance < cost){
            throw new InsufficientFundsException(Messages.ERROR_INSUFFICIENT_FUNDS.get());
        }
        eco.withdrawPlayer(player, claimBlocks * cost);
    }

}
