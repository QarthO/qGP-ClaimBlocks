package gg.quartzdev.qgpclaimblocks.transaction;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.util.PDC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SerializableAs("qTransaction")
public class Transaction implements ConfigurationSerializable {

    private UUID transactionId;
    private UUID withdrawerId;
    private String withdrawerName;
    private int claimBlocks;
    private ItemStack slip;

    public Transaction(Player withdrawer, int claimBlocks){
        this.transactionId = UUID.randomUUID();
        this.claimBlocks = claimBlocks;
        this.withdrawerId = withdrawer.getUniqueId();
        this.withdrawerName = withdrawer.getName();
        createSlip();
    }

    public Transaction(Map<String, Object> map){
        this.claimBlocks = (int) map.get("claim-blocks");
        this.withdrawerId = UUID.fromString((String) map.get("withdrawer-id"));
        this.withdrawerName = (String) map.get("withdrawer-name");
    }

    public void initId(String id){
        if(this.transactionId != null){
            QLogger.error("<prefix> <red>Error: Transaction already has an ID. This is most likely caused by an addon.");
            return;
        }
        transactionId = UUID.fromString(id);
    }

    public void createSlip(){
        slip = new ItemStack(ClaimBlocksAPI.getConfig().getSlipMaterial());
        ItemMeta itemMeta = slip.getItemMeta();
        PDC.setTransactionId(itemMeta, transactionId);
        updateNameLore(itemMeta);
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

    public void updateNameLore(ItemMeta itemMeta){
        Component name = MiniMessage.miniMessage().deserialize(
                ClaimBlocksAPI.getConfig().getSlipName()
                        .replaceAll("<blocks>", String.valueOf(claimBlocks))
        ).decoration(TextDecoration.ITALIC, false);
        itemMeta.displayName(name);
        List<Component> lore = new ArrayList<>();
        for(String loreLine : ClaimBlocksAPI.getConfig().getSlipLore()){
            loreLine = loreLine
                    .replaceAll("<id>", String.valueOf(transactionId))
                    .replaceAll("<withdrawer>", withdrawerName)
                    .replaceAll("<blocks>", String.valueOf(claimBlocks));
            lore.add(MiniMessage.miniMessage().deserialize(loreLine).decoration(TextDecoration.ITALIC, false));
        }
        itemMeta.lore(lore);
    }

    @Override
    public @NotNull LinkedHashMap<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("claim-blocks", claimBlocks);
        map.put("withdrawer-id", withdrawerId.toString());
        map.put("withdrawer-name", withdrawerName);
        return map;
    }

    @Override
    public String toString(){
        return "Transaction: " + transactionId + ", Withdrawer: " + withdrawerId + ", Claimblocks: " + claimBlocks;
    }
}
