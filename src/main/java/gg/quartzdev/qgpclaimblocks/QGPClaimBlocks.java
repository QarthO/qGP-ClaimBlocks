package gg.quartzdev.qgpclaimblocks;

import gg.quartzdev.lib.qlibpaper.UpdateChecker;
import gg.quartzdev.qgpclaimblocks.transaction.Transaction;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class QGPClaimBlocks extends JavaPlugin {

    @Override
    public void onEnable() {

//        Lets Transaction class be serializable for easing saving transactions to transactions.yml
        ConfigurationSerialization.registerClass(Transaction.class);

//        Enables the plugin
        ClaimBlocksAPI.enable(this, 21819);
    }

    @Override
    public void onDisable() {
        ClaimBlocksAPI.disable();
    }
}