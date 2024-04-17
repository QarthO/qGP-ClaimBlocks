package gg.quartzdev.qgptrade;

import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.QPluginAPI;
import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.qgptrade.commands.CMD;
import gg.quartzdev.qgptrade.commands.CMDreload;
import gg.quartzdev.qgptrade.commands.CMDwithdraw;
import gg.quartzdev.qgptrade.storage.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import java.util.List;

public class TradeAPI implements QPluginAPI {
    private static TradeAPI apiInstance;
    private static QGPTrade pluginInstance;
    private static QCommandMap commandMap;
    private static Metrics metrics;
    private static Config config;

    public static QGPTrade getPlugin(){
        return pluginInstance;
    }

    public static Config getConfig(){
        return config;
    }

    private TradeAPI(){

    }

    private TradeAPI(QGPTrade plugin, int bStatsPluginId){
        pluginInstance = plugin;

        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }

        registerCommands();
        registerListeners();
        setupConfig();
    }

    protected static void enable(QGPTrade plugin, int bStatsPluginId){
        if(apiInstance != null){
            QLogger.error(GenericMessages.ERROR_PLUGIN_ENABLE);
            return;
        }
        apiInstance = new TradeAPI(plugin, bStatsPluginId);
    }

    protected static void disable(){

//        Warns about reloading/plugin managers
        final boolean isStopping = Bukkit.getServer().isStopping();
        if(!isStopping){
            QLogger.error(GenericMessages.PLUGIN_UNSAFE_DISABLE);
        }

//        Logs plugin is being disabled
        QLogger.info(GenericMessages.PLUGIN_DISABLE);

//        Clears instances
        apiInstance = null;
        pluginInstance = null;
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
        commandMap.create(pluginInstance.getName(), new CMD("", QPerm.GROUP_PLAYER), List.of("claimblocks", "cb"));
        commandMap.addSubCommand(pluginInstance.getName(), new CMDreload("reload", QPerm.GROUP_ADMIN));
        commandMap.addSubCommand(pluginInstance.getName(), new CMDwithdraw("withdraw", QPerm.GROUP_PLAYER));
    }

    public void registerListeners(){

    }

    public void setupConfig(){
        config = new Config(pluginInstance, "config.yml");
    }

}
