package gg.quartzdev.qgpclaimblocks;

import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.QPluginAPI;
import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.qgpclaimblocks.commands.CMD;
import gg.quartzdev.qgpclaimblocks.commands.CMDreload;
import gg.quartzdev.qgpclaimblocks.commands.CMDtransaction;
import gg.quartzdev.qgpclaimblocks.commands.CMDwithdraw;
import gg.quartzdev.qgpclaimblocks.listeners.ExploitListener;
import gg.quartzdev.qgpclaimblocks.listeners.SlipListener;
import gg.quartzdev.qgpclaimblocks.storage.Config;
import gg.quartzdev.qgpclaimblocks.transaction.TransactionManager;
import gg.quartzdev.qgpclaimblocks.util.Messages;
import gg.quartzdev.qgpclaimblocks.util.VaultUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import java.util.List;

public class ClaimBlocksAPI implements QPluginAPI {
    private final String CONSOLE_PREFIX = "<white>[<red>qGP<white>-<aqua>Trade<white>]";
    private final String CHAT_PREFIX = "<red>qGP<white>-<aqua>Trade <bold><gray>>></bold>";

    private static ClaimBlocksAPI apiInstance;
    private static QGPClaimBlocks pluginInstance;
    private static QCommandMap commandMap;
    private static Metrics metrics;
    private static Config config;
    private static VaultUtil economy;
    private static TransactionManager transactionManager;

    public static QGPClaimBlocks getPlugin(){
        return pluginInstance;
    }

    public static Config getConfig(){
        return config;
    }

    public static VaultUtil getEconomy(){
        return economy;
    }

    public static TransactionManager getTransactionManager(){
        return transactionManager;
    }

    private ClaimBlocksAPI(){

    }

    private ClaimBlocksAPI(QGPClaimBlocks plugin, int bStatsPluginId){
        pluginInstance = plugin;
        Messages.init(CONSOLE_PREFIX, CHAT_PREFIX);
        QLogger.init(pluginInstance.getComponentLogger());
        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }
        setupConfig();
        registerListeners();
        setupEconomy();
        setupTransactionManager();
        registerCommands();
    }

    protected static void enable(QGPClaimBlocks plugin, int bStatsPluginId){
        if(apiInstance != null){
            QLogger.error(GenericMessages.ERROR_PLUGIN_ENABLE);
            return;
        }
        apiInstance = new ClaimBlocksAPI(plugin, bStatsPluginId);
    }

    protected static void disable(){

//        Warns about reloading/plugin managers
        final boolean isStopping = Bukkit.getServer().isStopping();
        if(!isStopping){
            QLogger.warning(GenericMessages.PLUGIN_UNSAFE_DISABLE);
        }

//        Logs plugin is being disabled
        QLogger.info(GenericMessages.PLUGIN_DISABLE);

//        Clears instances
        apiInstance = null;
        pluginInstance = null;
        config = null;
        economy = null;
        transactionManager = null;
        if(commandMap != null){
            commandMap.unregisterAll();
            commandMap = null;
        }
        if(metrics != null){
            metrics.shutdown();
            metrics = null;
        }

//        Stops async tasks
//        ...
    }

    @SuppressWarnings("UnstableApiUsage")
    public static String getVersion(){
        return pluginInstance.getPluginMeta().getVersion();
    }

    public static String getName(){
        return pluginInstance.getName();
    }

    public void setupMetrics(int pluginId){

    }

    public void registerCommands(){
        commandMap = new QCommandMap();
        String label = "qgptrust";
        commandMap.create(label, new CMD("", QPerm.GROUP_PLAYER), List.of("claimblocks", "cb"));
        commandMap.addSubCommand(label, new CMDreload("reload", QPerm.GROUP_ADMIN));
        commandMap.addSubCommand(label, new CMDwithdraw("withdraw", QPerm.GROUP_PLAYER));
        commandMap.addSubCommand(label, new CMDtransaction("transaction", QPerm.GROUP_ADMIN));
    }

    public void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new SlipListener(), pluginInstance);
        Bukkit.getPluginManager().registerEvents(new ExploitListener(), pluginInstance);
    }

    public void setupConfig(){
        config = new Config(pluginInstance, "config.yml");
    }

    public void setupEconomy(){
        economy = new VaultUtil();
    }

    public void setupTransactionManager(){
        transactionManager = new TransactionManager();
    }

}
