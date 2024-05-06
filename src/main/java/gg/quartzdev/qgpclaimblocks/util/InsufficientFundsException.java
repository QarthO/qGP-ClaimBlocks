package gg.quartzdev.qgpclaimblocks.util;

public class InsufficientFundsException extends Exception {

    public static final long serialVersionUID = 1L;
    public InsufficientFundsException(String message){
        super(message);
    }

    public InsufficientFundsException(final Throwable throwable){
        super(throwable);
    }
}
