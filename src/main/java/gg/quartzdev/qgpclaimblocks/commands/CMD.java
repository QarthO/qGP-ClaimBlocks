package gg.quartzdev.qgpclaimblocks.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.datastore.YMLconfig;
import gg.quartzdev.qgpclaimblocks.datastore.ConfigPath;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD extends QCommand {
    private YMLconfig config;
    public CMD(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
        config = ClaimBlocksAPI.getConfig();

    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player player)){
            return false;
        }
        Sender.message(sender, "<prefix> You have <blue>" + GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId()).getRemainingClaimBlocks() + " <green>available claim blocks");
        Sender.message(sender, "Min: " + config.get(ConfigPath.MIN_WITHDRAW,0));
//        return false;
        return true;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender commandSender, String[] strings) {
        return null;
    }
}
