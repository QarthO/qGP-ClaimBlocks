package gg.quartzdev.qgpclaimblocks;

import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.QPluginAPI;
import gg.quartzdev.lib.qlibpaper.UpdateChecker;
import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.metrics.bukkit.Metrics;
import gg.quartzdev.qgpclaimblocks.commands.*;
import gg.quartzdev.qgpclaimblocks.datastore.ConfigPath;
import gg.quartzdev.qgpclaimblocks.listeners.ExploitListener;
import gg.quartzdev.qgpclaimblocks.listeners.SlipListener;
import gg.quartzdev.qgpclaimblocks.datastore.YMLconfig;
import gg.quartzdev.qgpclaimblocks.transaction.TransactionManager;
import gg.quartzdev.qgpclaimblocks.util.Messages;
import gg.quartzdev.qgpclaimblocks.util.VaultUtil;
import org.bukkit.Bukkit;
import java.util.List;

public class ClaimBlocksAPI implements QPluginAPI {

    private final String CONSOLE_PREFIX = "<white>[<red>qGP<white>-<aqua>ClaimBlocks<white>]";
    private final String CHAT_PREFIX = "<red>qGP<white>-<aqua>ClaimBlocks <bold><gray>>></bold>";
    private static ClaimBlocksAPI apiInstance;
    private static QGPClaimBlocks pluginInstance;
    private static Messages messages;
    private static QCommandMap commandMap;
    private static Metrics metrics;
    private static YMLconfig config;
    private static VaultUtil economy;
    private static TransactionManager transactionManager;

    public static QGPClaimBlocks getPlugin(){
        return pluginInstance;
    }

    public static YMLconfig getConfig(){
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

//        Used to get plugin instance in other classes
        pluginInstance = plugin;

//        Initializes custom logger
        QLogger.init(pluginInstance.getComponentLogger());

//        Loads custom messages defined in messages.yml
        setupMessages();

//        Sets up bStats metrics
        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }

//        Sets up config.yml
        setupConfig();

//        Sets up vault hook
        if(config.get(ConfigPath.ECO_ENABLED, false)){
            setupEconomy();
        }

//        Checks for updates
        if(config.get(ConfigPath.CHECK_UPDATES, true)){
            UpdateChecker updateChecker = new UpdateChecker("qgp-claimblocks", "paper");
            updateChecker.checkForUpdatesAsync(pluginInstance, ClaimBlocksAPI.getVersion(), null);
        }

//        Initializes bukkit event listeners
        registerListeners();

//        Sets up transaction manager
        setupTransactionManager();

//        Registers all commands
        registerCommands();
    }

    @SuppressWarnings("SameParameterValue")
    protected static void enable(QGPClaimBlocks plugin, int bStatsPluginId){
        if(apiInstance != null){
            QLogger.error(GenericMessages.ERROR_PLUGIN_ENABLE);
            return;
        }
        apiInstance = new ClaimBlocksAPI(plugin, bStatsPluginId);
    }

    protected static void disable(){

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
        metrics = new Metrics(pluginInstance, pluginId);
    }

    public void registerCommands(){
        commandMap = new QCommandMap();
        String label = "qclaimblocks";
        commandMap.create(label, new CMD("", QPerm.GROUP_PLAYER), List.of("claimblocks", "cb"));
        commandMap.addSubCommand(label, new CMDreload("reload", QPerm.GROUP_ADMIN));
        commandMap.addSubCommand(label, new CMDwithdraw("withdraw", QPerm.GROUP_PLAYER));
        commandMap.addSubCommand(label, new CMDtransfer("transfer", QPerm.GROUP_PLAYER));
        commandMap.addSubCommand(label, new CMDtransaction("transaction", QPerm.GROUP_ADMIN));
    }

    public void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new SlipListener(), pluginInstance);
        Bukkit.getPluginManager().registerEvents(new ExploitListener(), pluginInstance);
    }

    public void setupConfig(){
        config = new YMLconfig(pluginInstance, "config.yml");
    }
    public void setupMessages(){
        messages = new Messages(CONSOLE_PREFIX, CHAT_PREFIX);
    }
    public static void loadCustomMessages(){
        messages.reload();
    }

    public static void setupEconomy(){
        if(config.get(ConfigPath.ECO_ENABLED, false))
            economy = new VaultUtil();
    }

    public void setupTransactionManager(){
        transactionManager = new TransactionManager();
    }

}
