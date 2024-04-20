package gg.quartzdev.qgptrade.commands;

import com.google.common.base.Charsets;
import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.transaction.Transaction;
import gg.quartzdev.qgptrade.util.Messages;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CMDwithdraw extends QCommand {

    public CMDwithdraw(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {

//        Gets player
        if(!(sender instanceof Player player)) {
            Sender.message(sender, Messages.ERROR_PLAYER_ONLY_COMMAND);
            return false;
        }

//        Checks syntax
        if(args.length != 2){
            String syntax = "<prefix> <red>Syntax: /" + label.toLowerCase() + " " + commandName() + " <amount>";
            Sender.message(sender, syntax);
            return false;
        }

//        Gets GP data
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(getId(player));
        if(playerData == null){
            Sender.message(sender, Messages.ERROR_WITHDRAW_LOAD_PLAYER.parse("player", sender.getName()));
            return false;
        }

        int blocksToWithdraw = parseWithdraw(args[1]);
        if(blocksToWithdraw <= 0){
            Sender.message(sender, "<prefix> come on bruh, type a number greater than 0 my guy...");
            return false;
        }
        int blocksAvailable = playerData.getRemainingClaimBlocks();
        int blocksRemaining = blocksAvailable - blocksToWithdraw;

        if(blocksRemaining < 0){
            Sender.message(player, "<prefix> u tryna withdraw more blocks than ya got? nice try n00b");
            return false;
        }

        int blocksBonus = playerData.getBonusClaimBlocks();
        int blocksAccrued = playerData.getAccruedClaimBlocks();

        if(blocksBonus >= blocksToWithdraw){
            playerData.setBonusClaimBlocks(blocksBonus - blocksToWithdraw);
        } else {
            playerData.setAccruedClaimBlocks(blocksAccrued - blocksToWithdraw);
        }
        savePlayerData(player, playerData);

        Transaction transaction = TradeAPI.getTransactionManager().createTransaction(player, blocksToWithdraw);

        Sender.broadcast("created transaction: " + transaction);
        player.getInventory().addItem(transaction.slip());

        QMessage successResponse = Messages.WITHDRAW_CLAIMBLOCKS
                .parse("blocks_withdraw", String.valueOf(blocksToWithdraw))
                .parse("blocks_remaining", String.valueOf(blocksRemaining));
        Sender.message(player, successResponse);
        return true;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions.add("<amount>");
            return completions;
        }
        return completions;
    }

    /**
     * Parse how many blocks the player is trying to withdraw. If
     * @param args1
     * @return
     */
    public int parseWithdraw(String args1){
        try {
            return Integer.parseInt(args1);
        } catch (NumberFormatException e){
            return 0;
        }
    }

    private void savePlayerData(Player player, PlayerData playerData){
        GriefPrevention.instance.dataStore.savePlayerData(getId(player), playerData);
    }
    private UUID getId(Player player){
        return Bukkit.getOnlineMode() ?
                player.getUniqueId() :
                UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(Charsets.UTF_8));
    }
}
