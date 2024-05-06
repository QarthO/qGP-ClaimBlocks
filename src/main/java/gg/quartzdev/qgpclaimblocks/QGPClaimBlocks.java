package gg.quartzdev.qgpclaimblocks;

import gg.quartzdev.qgpclaimblocks.transaction.Transaction;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class QGPClaimBlocks extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Transaction.class);
        ClaimBlocksAPI.enable(this, -1);
    }

    @Override
    public void onDisable() {
        ClaimBlocksAPI.disable();
    }
}