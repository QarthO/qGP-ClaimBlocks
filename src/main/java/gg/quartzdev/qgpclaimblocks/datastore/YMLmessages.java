package gg.quartzdev.qgpclaimblocks.datastore;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import gg.quartzdev.qgpclaimblocks.util.Messages;
import org.bukkit.plugin.java.JavaPlugin;

public class YMLmessages extends QConfiguration {
    public YMLmessages(JavaPlugin plugin, String fileName) {
        super(plugin, fileName, false);
        loadAllData();
    }

    @Override
    public void loadAllData() {
        QLogger.info("<prefix> Loading messages");
        for(String key : yamlConfiguration.getKeys(false)){
            QMessage message = Messages.getCustomMessage(key);
            if(message == null){
                QLogger.error("Message with key <yellow>" + key + "</yellow> does not exist");
                continue;
            }
            message.set(yamlConfiguration.getString(key));
        }
    }

    @Override
    public void saveAllData() {

    }
}
