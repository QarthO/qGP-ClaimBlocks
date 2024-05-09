package gg.quartzdev.qgpclaimblocks.datastore;

import gg.quartzdev.lib.qlibpaper.Sender;
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
        Sender.broadcast("Loading All Messages");
        for(String key : yamlConfiguration.getKeys(false)){
            try{
                if(key.equalsIgnoreCase("schema-version")) continue;
                QMessage message = Messages.getCustomMessage(key);
                Sender.broadcast(message.get());
                message.set(yamlConfiguration.getString(key));
                Sender.broadcast(message.get());
            } catch (NoSuchFieldException | IllegalAccessException  | ClassCastException ignored){}
        }
    }

    @Override
    public void saveAllData() {

    }
}
