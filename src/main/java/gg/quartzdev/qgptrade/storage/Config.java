package gg.quartzdev.qgptrade.storage;

import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends QConfiguration {


    private final String PATH_WITHDRAW__REQUIRES_PERMISSION = "withdraw.requires-permission";
    private final String PATH_WITHDRAW__DESPOIT_SLIP__MATERIAL = "withdraw.deposit-slip.material";
    private final String PATH_WITHDRAW__DESPOIT_SLIP__NAME = "withdraw.deposit-slip.name";
    private final String PATH_WITHDRAW__DESPOIT_SLIP__LORE = "withdraw.deposit-slip.lore";
    private boolean requiresPermission = false;
    private Material depositSlipMaterial = Material.PAPER;



    public Config(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    @Override
    public void loadAllData() {
//        loadRequiresPermission();
        loadDepositSlipMaterial();
    }

    @Override
    public void saveAllData() {
//        saveDepositSlipMaterial();
    }
    public void loadDepositSlipMaterial(){
        depositSlipMaterial = getMaterial(PATH_WITHDRAW__DESPOIT_SLIP__MATERIAL);
        if(depositSlipMaterial == null){
            depositSlipMaterial = Material.PAPER;
        }
    }
    public Material getDepositSlipMaterial(){
        return depositSlipMaterial;
    }
    public void saveDepositSlipMaterial(){
        yamlConfiguration.set(PATH_WITHDRAW__DESPOIT_SLIP__MATERIAL, depositSlipMaterial.name());
    }
}
