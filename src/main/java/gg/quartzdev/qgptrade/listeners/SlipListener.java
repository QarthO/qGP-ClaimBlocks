package gg.quartzdev.qgptrade.listeners;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.transaction.Transaction;
import gg.quartzdev.qgptrade.util.Messages;
import gg.quartzdev.qgptrade.util.PDC;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SlipListener implements Listener {
    @EventHandler
    public void onUseSlip(PlayerInteractEvent event){

        if(event.getAction().isLeftClick()){
            return;
        }

        ItemStack heldItem = event.getItem();
        if(heldItem == null){
            return;
        }

        Transaction transaction = getTransactionFromSlip(heldItem);
        if(transaction == null){
            return;
        }

        Player player = event.getPlayer();
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());

        if(playerData == null){
            Sender.message(player, Messages.ERROR_LOAD_CLAIM_BLOCKS);
            return;
        }

        playerData.setBonusClaimBlocks(playerData.getBonusClaimBlocks() + transaction.claimBlocks());
        GriefPrevention.instance.dataStore.savePlayerData(player.getUniqueId(), playerData);

        player.getInventory().remove(event.getItem());

        QMessage successMessage = Messages.DEPOSIT_CLAIM_BLOCKS
                .parse("blocks_deposit", String.valueOf(transaction.claimBlocks()))
                .parse("blocks_remaining", String.valueOf(playerData.getRemainingClaimBlocks()));
        Sender.message(player, successMessage);

        TradeAPI.getTransactionManager().closeTransaction(transaction);
    }

    private @Nullable Transaction getTransactionFromSlip(ItemStack heldItem){
        UUID transactionId = PDC.getTransactionId(heldItem);
        return transactionId != null ? TradeAPI.getTransactionManager().getTransaction(transactionId) : null;
    }
}
