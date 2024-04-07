package gg.quartzdev.qtemplateplugin;

import gg.quartzdev.qtemplateplugin.storage.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class QTemplatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        QTemplateAPI.enable(this, -1);

    }

    @Override
    public void onDisable() {
        QTemplateAPI.disable();
    }
}