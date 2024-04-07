package gg.quartzdev.qtemplateplugin;

import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.QPluginAPI;
import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.qtemplateplugin.commands.CMD;
import gg.quartzdev.qtemplateplugin.commands.CMDreload;
import gg.quartzdev.qtemplateplugin.commands.CMDset;
import gg.quartzdev.qtemplateplugin.storage.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class QTemplateAPI implements QPluginAPI {
    private static QTemplateAPI apiInstance;
    private static QTemplatePlugin pluginInstance;
    private static QCommandMap commandMap;
    private static Metrics metrics;
    private static Config config;

    public static QTemplatePlugin getPlugin(){
        return pluginInstance;
    }

    public static Config getConfig(){
        return config;
    }

    private QTemplateAPI(){

    }

    private QTemplateAPI(QTemplatePlugin plugin, int bStatsPluginId){
        pluginInstance = plugin;

        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }

        registerCommands();
        registerListeners();
        setupConfig();
    }

    protected static void enable(QTemplatePlugin plugin, int bStatsPluginId){
        if(apiInstance != null){
            QLogger.error(GenericMessages.ERROR_PLUGIN_ENABLE);
            return;
        }
        apiInstance = new QTemplateAPI(plugin, bStatsPluginId);
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
        commandMap.create(pluginInstance.getName(), new CMD("", QPerm.GROUP_PLAYER), List.of("template", "kekw"));
        commandMap.addSubCommand(pluginInstance.getName(), new CMDreload("reload", QPerm.GROUP_ADMIN));
        commandMap.addSubCommand(pluginInstance.getName(), new CMDset("set", QPerm.GROUP_ADMIN));
    }

    public void registerListeners(){

    }

    public void setupConfig(){
        config = new Config(pluginInstance, "config.yml");
    }

}
