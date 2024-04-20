package gg.quartzdev.qgptrade.transaction;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.util.PDC;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Transaction implements ConfigurationSerializable {

    private UUID transactionId;
    private UUID creatorId;
    private int claimBlocks;
    private Date creationDate;
    private ItemStack slip;

    public Transaction(Player creator, int claimBlocks){
        this.transactionId = UUID.randomUUID();
        this.creatorId = creator.getUniqueId();
        this.claimBlocks = claimBlocks;
        createSlip();
    }

    public Transaction(Map<String, Object> map){
        this.creatorId = UUID.fromString((String) map.get("creator-id"));
        this.claimBlocks = (int) map.get("claim-blocks");
        this.creationDate = Date.from(Instant.ofEpochMilli((long) map.get("creation-date")));
        createSlip();
    }

    public void initId(String id){
        if(this.transactionId != null){
            QLogger.error("<prefix> <red>Error: Transaction already has an ID. This is most likely caused by an addon.");
            return;
        }
        transactionId = UUID.fromString(id);
    }

    private void createSlip(){
        Sender.broadcast("creating slip");
        slip = new ItemStack(TradeAPI.getConfig().getDepositSlipMaterial());
        slip.setType(Material.DIAMOND);
        ItemMeta itemMeta = slip.getItemMeta();
        PDC.setTransactionId(itemMeta, transactionId);
        updateLore(itemMeta);
        slip.setItemMeta(itemMeta);
    }

    public UUID getId(){
        return transactionId;
    }

    public int claimBlocks(){
        return claimBlocks;
    }

    public void claimBlocks(int claimBlocks){
        this.claimBlocks = claimBlocks;
    }

    public ItemStack slip(){
        return slip;
    }

    public void updateLore(ItemMeta itemMeta){
        Sender.broadcast("updating lore");
        itemMeta.displayName(Component.text("sup"));
    }

    @Override
    public @NotNull LinkedHashMap<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("creator-id", creatorId);
        map.put("claim-blocks", claimBlocks);
        map.put("creation-date", creationDate.getTime());
        return map;
    }

    @Override
    public String toString(){
        return "Transaction: " + transactionId + ", Withdrawer: " + creatorId + ", Claimblocks: " + claimBlocks;
    }
}
