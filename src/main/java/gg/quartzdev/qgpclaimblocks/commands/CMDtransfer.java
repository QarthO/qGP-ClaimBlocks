package gg.quartzdev.qgpclaimblocks.commands;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.QGPClaimBlocks;
import gg.quartzdev.qgpclaimblocks.datastore.ConfigPath;
import org.bukkit.command.CommandSender;

public class CMDtransfer extends QCommand {
    public CMDtransfer(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        QLogger.info("TEST: " + ClaimBlocksAPI.getConfig().getString(ConfigPath.CHECK_UPDATES.get()));
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender commandSender, String[] strings) {
        return null;
    }
}
