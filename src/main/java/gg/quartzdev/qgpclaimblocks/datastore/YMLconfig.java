package gg.quartzdev.qgpclaimblocks.datastore;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.storage.ConfigOption;
import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.Supplier;

public class YMLconfig extends QConfiguration {

    public YMLconfig(JavaPlugin plugin, String fileName) {
        super(plugin, fileName, true);
        initializeAll();
        loadAllData();
    }

    @Override
    public void loadAllData() {
        configOptions.values().forEach(ConfigOption::load);
    }

    @Override
    public void saveAllData() {
    }

    public void initializeAll(){

//        Check for updates
        setup(ConfigPath.CHECK_UPDATES, true, () -> yamlConfiguration.getBoolean(ConfigPath.CHECK_UPDATES.get()));

//        Min max claim blocks
        setup(ConfigPath.MIN_WITHDRAW, 1, () -> getNumber(ConfigPath.MIN_WITHDRAW.get()).intValue());
        setup(ConfigPath.MAX_WITHDRAW, Integer.MAX_VALUE, () -> getNumber(ConfigPath.MAX_WITHDRAW.get()).intValue());
        setup(ConfigPath.MIN_TRANSFER, 0, () -> getNumber(ConfigPath.MIN_TRANSFER.get()).intValue());
        setup(ConfigPath.MAX_TRANSFER, Integer.MAX_VALUE, () -> getNumber(ConfigPath.MAX_TRANSFER.get()).intValue());

//        Slip related
        setup(ConfigPath.SLIP_NAME, "<bold><yellow><blocks_withdraw> claim blocks", () -> getString(ConfigPath.SLIP_NAME.get()));
        setup(ConfigPath.SLIP_LORE, List.of(
                "<blue>Transaction ID: <yellow><id>",
                "<blue>Withdrawer: <yellow><player>",
                "<blue>Claim Blocks: <yellow><blocks>"), () -> getList(ConfigPath.SLIP_LORE.get()));
        setup(ConfigPath.SLIP_MATERIAL, Material.PAPER, () -> getMaterial(ConfigPath.SLIP_MATERIAL.get()));

//        Economy related
        setup(ConfigPath.ECO_ENABLED, false, () -> yamlConfiguration.getBoolean(ConfigPath.ECO_ENABLED.get()));
        setup(ConfigPath.TAX_WITHDRAW, 0, () -> getNumber(ConfigPath.TAX_WITHDRAW.get()).intValue());
        setup(ConfigPath.TAX_TRANSFER, 0, () -> getNumber(ConfigPath.TAX_TRANSFER.get()).intValue());
        setup(ConfigPath.FEE_WITHDRAW, 0, () -> getNumber(ConfigPath.FEE_WITHDRAW.get()).intValue());
        setup(ConfigPath.FEE_TRANSFER, 0, () -> getNumber(ConfigPath.FEE_TRANSFER.get()).intValue());
    }

    public <T> void setup(ConfigPath path, T defaultValue, Supplier<T> loader){
        configOptions.put(path.get(), new ConfigOption<>(path.get(), yamlConfiguration, loader));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ConfigPath key, T defaultValue){
        try{
            ConfigOption<T> option = (ConfigOption<T>) configOptions.get(key.get());
            return option != null ? option.get() : defaultValue;
        } catch (ClassCastException ignored){
            return defaultValue;
        }
    }

}
