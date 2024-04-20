package gg.quartzdev.qgptrade.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qgptrade.util.Messages;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        if(playerData == null){
            Sender.message(sender, Messages.ERROR_WITHDRAW_LOAD_PLAYER.parse("player", sender.getName()));
            return false;
        }

        int blocksToWithdraw = Integer.parseInt(args[1]);
        int blocksAvailable = playerData.getRemainingClaimBlocks();
        int blocksRemaining = blocksAvailable - blocksToWithdraw;

        if(blocksRemaining < 0){
            Sender.message(player, "Not enough blocks!");
            return false;
        }

        int blocksBonus = playerData.getBonusClaimBlocks();
        int blocksAccrued = playerData.getAccruedClaimBlocks();

        if(blocksBonus >= blocksToWithdraw){
            playerData.setBonusClaimBlocks(blocksBonus - blocksToWithdraw);
        } else {
            playerData.setAccruedClaimBlocks(blocksAccrued - blocksToWithdraw);
        }

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
}
