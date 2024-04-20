package gg.quartzdev.qgptrade;

import gg.quartzdev.qgptrade.transaction.Transaction;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class QGPTrade extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Transaction.class);
        TradeAPI.enable(this, -1);
    }

    @Override
    public void onDisable() {
        TradeAPI.disable();
    }
}