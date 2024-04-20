package gg.quartzdev.qgptrade.storage;

import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends QConfiguration {


    private final String PATH_REQUIRES_PERMiSSION = "requires-permission";
    private final String PATH_DEPOSIT_SLIP__MATERIAL = "deposit-slip.material";
    private final String PATH_DEPOSIT_SLIP__NAME = "deposit-slip.name";
    private final String PATH_DEPOSIT_SLIP__LORE = "deposit-slip.lore";
    private boolean requiresPermission = false;
    private Material depositSlipMaterial = Material.PAPER;



    public Config(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    @Override
    public void loadAllData() {
        loadRequiresPermission();
        loadDepositSlipMaterial();
    }

    @Override
    public void saveAllData() {
        saveDepositSlipMaterial();
    }

//    requires permission
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

//
    final String PATH_DEPOPSIT_SLIP_MATERIAL = "deposit-slip-material";
    public void loadDepositSlipMaterial(){
        depositSlipMaterial = getMaterial(PATH_DEPOPSIT_SLIP_MATERIAL);
    }
    public Material getDepositSlipMaterial(){
        return depositSlipMaterial;
    }
    public void saveDepositSlipMaterial(){
        yamlConfiguration.set(PATH_DEPOPSIT_SLIP_MATERIAL, depositSlipMaterial.name());
    }
}
