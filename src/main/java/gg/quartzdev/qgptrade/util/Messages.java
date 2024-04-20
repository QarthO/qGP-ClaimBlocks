package gg.quartzdev.qgptrade.util;

import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.qgptrade.TradeAPI;
import gg.quartzdev.qgptrade.storage.YMLmessages;

public class Messages extends GenericMessages {
    private static Messages INSTANCE;
    YMLmessages messagesFile;
    public static QMessage WITHDRAW_CLAIMBLOCKS = new QMessage("<prefix> <blue>You withdrew <yellow><blocks> <blue>claim blocks<newline>You now have <yellow><blocks> <blue>remaining.");
    public static QMessage ERROR_WITHDRAW_LOAD_PLAYER = new QMessage("<prefix> <red>Error: Unable to load GriefPrevention data for player <player>");

    public static QMessage VAULT_HOOKED = new QMessage("<prefix> <green>Successfully hooked into <yellow>Vault's<green> Economy");
    public static QMessage ERROR_VAULT_HOOK = new QMessage("<prefix> <red>Error: Vault found, but unable to hook into it's economy");
    public static QMessage WARNING_VAULT_NOT_FOUND = new QMessage("<prefix> <yellow> Warning: Vault is required for the economy integration");
    private Messages(String consolePrefix, String chatPrefix){
        super(consolePrefix, chatPrefix);
        messagesFile = new YMLmessages(TradeAPI.getPlugin(), "messages.yml");
    }
    public static void init(String consolePrefix, String chatPrefix){
        if(INSTANCE != null){
            return;
        }
        INSTANCE = new Messages(consolePrefix, chatPrefix);
    }


}
