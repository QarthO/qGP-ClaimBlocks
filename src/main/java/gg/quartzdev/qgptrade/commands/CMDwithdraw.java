package gg.quartzdev.qgptrade.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
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
        Sender.message(sender, label + " - " + Arrays.toString(args));
        if(!(sender instanceof Player player)) return false;
        if(args.length != 2){
            String syntax = "<prefix> <red>Syntax: /" + label.toLowerCase() + " " + commandName() + " <amount>";
            Sender.message(sender, syntax);
            return false;
        }
        Sender.message(sender, "<prefix> <green>Withdrawing <yellow>" + args[1] + " <green> claimblocks");
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());

        Sender.message(player, "<prefix> getRemainingClaimBlocks: " + playerData.getRemainingClaimBlocks());
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
}
