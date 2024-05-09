package gg.quartzdev.qgpclaimblocks.datastore;

public enum ConfigPath {
    MIN_WITHDRAW("withdraw.min-claim-blocks"),
    MAX_WITHDRAW("withdraw.max-claim-blocks"),
    SLIP_MATERIAL("withdraw.deposit-slip.material"),
    SLIP_NAME("withdraw.deposit-slip.name"),
    SLIP_LORE("withdraw.deposit-slip.lore"),
    TAX_WITHDRAW("economy.tax.withdraw"),
    TAX_TRANSFER("economy.tax.transfer"),
    FEE_WITHDRAW("economy.fee.withdraw"),
    FEE_TRANSFER("economy.fee.transfer");

    private final String path;
    ConfigPath(String path){
        this.path = path;
    }

    public String get(){
        return path;
    }
}