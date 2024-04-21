package gg.quartzdev.qgptrade.storage;

import gg.quartzdev.lib.qlibpaper.storage.QConfiguration;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Config extends QConfiguration {
    private final String PATH_WITHDRAW__MIN     = "withdraw.min-claim-blocks";
    private final String PATH_WITHDRAW__MAX     = "withdraw.max-claim-blocks";
    private final String PATH_SLIP_MATERIAL     = "withdraw.deposit-slip.material";
    private final String PATH_SLIP_NAME         = "withdraw.deposit-slip.name";
    private final String PATH_SLIP_LORE         = "withdraw.deposit-slip.lore";
    private int withdrawMin;
    private int withdrawMax;
    private Material slipMaterial;
    private String slipName;
    private List<String> slipLore;



    public Config(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
        withdrawMin = 1;
        withdrawMax = Integer.MAX_VALUE;
        slipName = "<bold><yellow><blocks_withdraw> claim blocks";
        slipLore = List.of(
                "<blue>Transaction ID: <yellow><id>",
                "<blue>Withdrawer: <yellow><player>",
                "<blue>Claim Blocks: <yellow><blocks>");
        slipMaterial = Material.PAPER;
        loadAllData();
    }

    @Override
    public void loadAllData() {
        loadWithdrawMin();
        loadWithdrawMax();
        loadSlipMaterial();
        loadSlipName();
        loadSlipLore();
    }

    @Override
    public void saveAllData() {
    }


//    withdraw min
    public void loadWithdrawMin(){
        withdrawMin = getNumber(PATH_WITHDRAW__MIN).intValue();
    }
    public void setWithdrawMin(int value){
        withdrawMin = value;
        yamlConfiguration.set(PATH_WITHDRAW__MIN, value);
        save();
    }
    public int getWithdrawMin(){
        return withdrawMin;
    }

//    withdraw max
    public void loadWithdrawMax(){
        withdrawMax = getNumber(PATH_WITHDRAW__MAX).intValue();
        if(withdrawMax <= -1){
            yamlConfiguration.set(PATH_WITHDRAW__MAX, -1);
            withdrawMax = Integer.MAX_VALUE;
        }
    }
    public void setWithdrawMax(int value){
        withdrawMax = value;
        yamlConfiguration.set(PATH_WITHDRAW__MIN, value);
        save();
    }
    public int getWithdrawMax(){
        return withdrawMax;
    }

//    slip material
    public void loadSlipMaterial(){
        slipMaterial = getMaterial(PATH_SLIP_MATERIAL);
    }
    public void setSlipMaterial(Material material){
        slipMaterial = material;
        yamlConfiguration.set(PATH_SLIP_MATERIAL, slipMaterial.name());
        save();
    }
    public Material getSlipMaterial(){
        return slipMaterial;
    }

//    slip name
    public void loadSlipName(){
        slipName = getString(PATH_SLIP_NAME);
    }
    public void saveSlipName(){
        yamlConfiguration.set(PATH_SLIP_NAME, slipName);
        save();
    }
    public String getSlipName(){
        return slipName;
    }

//    slip lore
    public void loadSlipLore(){
        slipLore = getStringList(PATH_SLIP_LORE);
    }
    public void setSlipLore(List<String> value){
        slipLore = value;
        yamlConfiguration.set(PATH_SLIP_LORE, value);
        save();
    }
    public List<String> getSlipLore(){
        return slipLore;
    }
}
