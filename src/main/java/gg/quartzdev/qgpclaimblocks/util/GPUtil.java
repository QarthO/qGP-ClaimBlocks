package gg.quartzdev.qgpclaimblocks.util;

import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

import java.util.UUID;

public class GPUtil
{
    public static QMessage hasEnoughBlocks(PlayerData playerData, int blocks)
    {
        int blocksAvailable = playerData.getRemainingClaimBlocks();
        int blocksRemaining = blocksAvailable - blocks;

        if(blocksRemaining < 0){
            return Messages.ERROR_NOT_ENOUGH_CLAIM_BLOCKS
                    .parse("blocks", String.valueOf(blocksAvailable));
        }
        return null;
    }

    public static QMessage subtractBlocks(PlayerData playerData, UUID playerID, int blocksToSubtract){
        final QMessage response = hasEnoughBlocks(playerData, blocksToSubtract);
        if(response != null) return response;

        int blocksBonus = playerData.getBonusClaimBlocks();
        playerData.setBonusClaimBlocks(blocksBonus - blocksToSubtract);
        GriefPrevention.instance.dataStore.savePlayerData(playerID, playerData);
        return null;
    }

    public static void addBlocks(PlayerData playerData, UUID playerID, int blocksToAdd){
        final int blocksBonus = playerData.getBonusClaimBlocks();
        playerData.setBonusClaimBlocks(blocksBonus + blocksToAdd);
        GriefPrevention.instance.dataStore.savePlayerData(playerID, playerData);
    }
}
