package gg.quartzdev.qgpclaimblocks.util;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qgpclaimblocks.ClaimBlocksAPI;
import gg.quartzdev.qgpclaimblocks.datastore.YMLmessages;
import org.jetbrains.annotations.Nullable;

public class Messages extends GenericMessages {
    private static Messages INSTANCE;
    YMLmessages messagesFile;

//    WITHDRAW CLAIMBLOCKS
    public static QMessage SYNTAX_WITHDRAW = new QMessage(
            "<prefix> <red>Syntax: /<label> withdraw <amount>");
    public static QMessage SYNTAX_TRANSACTION = new QMessage(
            "<prefix> <red>Syntax: /<label> transaction <id>");
    public static QMessage WITHDRAW_CLAIMBLOCKS = new QMessage(
            "<prefix> <blue>You withdrew <yellow><blocks_withdraw> <blue>claim blocks<newline>" +
            "<prefix> <blue>You now have <yellow><blocks_remaining> <blue>remaining");
    public static QMessage ERROR_WITHDRAW_NOT_ENOUGH_CLAIM_BLOCKS = new QMessage(
            "<prefix> <red>Error: You only have <yellow><blocks> <red>claim blocks");
    public static QMessage ERROR_LOAD_CLAIM_BLOCKS = new QMessage(
            "<prefix> <red>Error: Unable to load your claim blocks data from GriefPrevention");
    public static QMessage ERROR_WITHDRAW_INVALID_NUMBER = new QMessage(
            "<prefix> <red>Error: <yellow><input> <red>is an invalid number");
    public static QMessage ERROR_WITHDRAW_INVALID_NUMBER_MAX = new QMessage(
            "<prefix> <red>Error: You can't withdraw that many claim blocks");
    public static QMessage ERROR_WITHDRAW_INVALID_NUMBER_MIN = new QMessage(
            "<prefix> <red>Error: You must withdraw at least <blocks> claim blocks");

//    Deposit
    public static QMessage DEPOSIT_CLAIM_BLOCKS = new QMessage(
            "<prefix> <blue>You deposited <yellow><blocks_deposit> <blue>claim blocks<newline>" +
            "<prefix> <blue>You now have <yellow><blocks_remaining> <blue>available claim blocks");

    public static QMessage VAULT_HOOKED = new QMessage(
            "<prefix> <green>Successfully hooked into <yellow>Vault's<green> Economy");
    public static QMessage ERROR_VAULT_HOOK = new QMessage(
            "<prefix> <red>Error: Vault found, but unable to find an economy provider. Make sure you have an Economy plugin installed, (ie. EssentialsX, CoinsEngine, etc");
    public static QMessage WARNING_VAULT_NOT_FOUND = new QMessage(
            "<prefix> <yellow> Warning: Vault is required for the economy integration");
    public static QMessage ERROR_INSUFFICIENT_FUNDS = new QMessage(
            "<prefix> <red>Error: You don't have enough money to do that");

    public Messages(String consolePrefix, String chatPrefix){
        super(consolePrefix, chatPrefix);
        messagesFile = new YMLmessages(ClaimBlocksAPI.getPlugin(), "messages.yml");
    }

    /**
     * Reloads the messages file
     */
    public void reload(){
        messagesFile.reload();
    }

    /**
     * uses reflection to get the {@link QMessage} object from the class
     * @param key the name of the field to get
     * @return the {@link QMessage} or {@link null} if it doesn't exist
     */
    public static @Nullable QMessage getCustomMessage(String key) {
        try {
            return (QMessage) Messages.class.getField(key).get(QMessage.class);
        } catch(NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            return null;
        }
    }

}
