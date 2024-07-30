package gg.quartzdev.qgpclaimblocks.commands;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.commands.QCommand;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.datastore.ConfigPath;
import gg.quartzdev.qgpclaimblocks.datastore.YMLconfig;
import gg.quartzdev.qgpclaimblocks.util.Args;
import gg.quartzdev.qgpclaimblocks.util.GPUtil;
import gg.quartzdev.qgpclaimblocks.util.Messages;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class CMDtransfer extends QCommand {

    private final YMLconfig config;

    public CMDtransfer(String commandName, String permissionGroup) {
        super(commandName, permissionGroup);
        this.config = ClaimBlocksAPI.getConfig();
    }

//    /label transfer <player> <amount>
    @Override
    public boolean logic(CommandSender sender, String label, String[] args) {

//        player only command
        if(!(sender instanceof Player fromPlayer)){
            Sender.message(sender, Messages.ERROR_PLAYER_ONLY_COMMAND);
            return false;
        }

//        validate syntax length
        if(args.length != 3){
            Sender.message(sender, Messages.SYNTAX_TRANSFER.parse("label", label));
            return false;
        }

//        gets player from args
        final Player toPlayer = Bukkit.getPlayer(args[1]);
        if(toPlayer == null)
        {
            Sender.message(sender, Messages.ERROR_PLAYER_NOT_FOUND);
            return false;
        }

//        make sure player isn't trying to send to themselves
        if(toPlayer.getUniqueId().equals(fromPlayer.getUniqueId())){
            Sender.message(sender, Messages.ERROR_SELF_TRANSFER);
            return false;
        }

//        gets fromPlayer's gp data
        final PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(fromPlayer.getUniqueId());
        if(playerData == null){
            Sender.message(sender, Messages.ERROR_LOAD_CLAIM_BLOCKS);
            return false;
        }

//        gets number of blocks to transfer
        final int blocksToTransfer = Args.parseInt(args[2]);
//        make sure input number is a valid number
        QMessage response = Args.validateNumber(
                args[2],
                config.get(ConfigPath.MIN_TRANSFER, 1),
                config.get(ConfigPath.MAX_TRANSFER, Integer.MAX_VALUE-1)
        );
        if(response != null){
            Sender.message(sender, response);
            return false;
        }
//        transfers blocks
        response = transfer(fromPlayer, toPlayer, blocksToTransfer);
        Sender.message(fromPlayer, response.get());
        return true;
    }

    @Override
    public Iterable<String> tabCompletionLogic(CommandSender sender, String[] args) {
        if(args.length == 1) return List.of("<player> <amount>");;
        if(args.length == 2) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        if(args.length == 3) return List.of("<amount>");
        return null;
    }

    public @NotNull QMessage transfer(Player fromPlayer, Player toPlayer, int blocksToTransfer){
        final PlayerData fromPlayerData = GriefPrevention.instance.dataStore.getPlayerData(fromPlayer.getUniqueId());
        final PlayerData toPlayerData = GriefPrevention.instance.dataStore.getPlayerData(toPlayer.getUniqueId());
        if(fromPlayerData == null || toPlayerData == null){
            QLogger.warning("<red>Error: Unable to load GriefPrevention data");
            return Messages.ERROR_LOAD_CLAIM_BLOCKS;
        }

        QMessage response = GPUtil.subtractBlocks(fromPlayerData, fromPlayer.getUniqueId(), blocksToTransfer);
        if(response != null) return response;
        GPUtil.addBlocks(toPlayerData, toPlayer.getUniqueId(), blocksToTransfer);
        Sender.message(toPlayer, Messages.RECEIVE_CLAIM_BLOCKS
                .parse("blocks_receive", String.valueOf(blocksToTransfer))
                .parse("player", fromPlayer.getName())
                .parse("blocks_remaining", String.valueOf(toPlayerData.getRemainingClaimBlocks())));
        return Messages.TRANSFER_CLAIM_BLOCKS
                .parse("blocks_transfer", String.valueOf(blocksToTransfer))
                .parse("player", toPlayer.getName())
                .parse("blocks_remaining", String.valueOf(fromPlayerData.getRemainingClaimBlocks()));
    }
}
