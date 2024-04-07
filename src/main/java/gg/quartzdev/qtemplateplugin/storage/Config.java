package gg.quartzdev.qtemplateplugin.storage;

import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Config extends QConfiguration {
    public Config(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    @Override
    public void loadAllData() {
        loadRequiresPermission();
    }

    @Override
    public void saveAllData() {

    }

    boolean requiresPermission = false;
    public void loadRequiresPermission(){
        requiresPermission = this.yamlConfiguration.getBoolean("requires-permission");
    }
    public boolean requiresPermisison(){
        return requiresPermission;
    }
    public void setRequiresPermission(boolean shouldRequiresPermission){
        requiresPermission = shouldRequiresPermission;
        this.yamlConfiguration.set("requires-permission", shouldRequiresPermission);
        save();
    }
}
