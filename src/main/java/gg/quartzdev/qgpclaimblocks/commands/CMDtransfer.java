package gg.quartzdev.qgpclaimblocks.commands;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.UpdateChecker;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.QGPClaimBlocks;
import gg.quartzdev.qgpclaimblocks.datastore.ConfigPath;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

public class CMDtransfer extends QCommand {
    public CMDtransfer(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
    }

    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender commandSender, String[] strings) {
        return null;
    }
}
