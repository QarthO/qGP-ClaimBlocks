package gg.quartzdev.qgptrade.util;

import gg.quartzdev.lib.qlibpaper.QLogger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEco {

    Economy eco;

    public VaultEco(){
        setupEconomy();
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
    public boolean exists(){
        return eco != null;
    }

}
