package gg.quartzdev.qgpclaimblocks.storage;

import gg.quartzdev.lib.qlibpaper.storage.ConfigOption;
import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.Supplier;

public class Config extends QConfiguration {

    public Config(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
//        withdrawMin = 1;
//        withdrawMax = Integer.MAX_VALUE;
//        slipName = "<bold><yellow><blocks_withdraw> claim blocks";
//        slipLore = List.of(
//                "<blue>Transaction ID: <yellow><id>",
//                "<blue>Withdrawer: <yellow><player>",
//                "<blue>Claim Blocks: <yellow><blocks>");
//        slipMaterial = Material.PAPER;
        initializeAll();
        loadAllData();
//        options.values().forEach(ConfigOption::load);
    }

    @Override
    public void loadAllData() {
        configOptions.values().forEach(ConfigOption::load);
    }

    @Override
    public void saveAllData() {
    }

    public void initializeAll(){
        setup(ConfigPath.MIN_WITHDRAW, 1, () -> getNumber(ConfigPath.MIN_WITHDRAW.get()).intValue());
        setup(ConfigPath.MAX_WITHDRAW, Integer.MAX_VALUE, () -> getNumber(ConfigPath.MAX_WITHDRAW.get()).intValue());
        setup(ConfigPath.SLIP_NAME, "<bold><yellow><blocks_withdraw> claim blocks", () -> getString(ConfigPath.SLIP_NAME.get()));
        setup(ConfigPath.SLIP_LORE, List.of(
                "<blue>Transaction ID: <yellow><id>",
                "<blue>Withdrawer: <yellow><player>",
                "<blue>Claim Blocks: <yellow><blocks>"), () -> getList(ConfigPath.SLIP_LORE.get()));
        setup(ConfigPath.SLIP_MATERIAL, Material.PAPER, () -> getMaterial(ConfigPath.SLIP_MATERIAL.get()));
    }

    public static final String POP = "test";
    public <T> void setup(ConfigPath path, T defaultValue, Supplier<T> loader){
        configOptions.put(path.get(), new ConfigOption<>(path.get(), yamlConfiguration, loader));
    }

    public <T> T get(ConfigPath key, T defaultValue){
        ConfigOption<T> option = (ConfigOption<T>) configOptions.get(key.get());
        return option == null ? defaultValue : (T) option.get();
    }

}
