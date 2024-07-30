package gg.quartzdev.qgpclaimblocks.util;

import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import org.jetbrains.annotations.Nullable;

public class Args {

    public static int parseInt(String arg){
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e){
            return 0;
        }
    }

    /**
     * Returns a {@link QMessage} if the number doesn't meet the requirements. Will return null if the number is valid
     * @param arg the number to validate
     * @param min the minimum number
     * @param max the maximum number
     * @return a response message to send to the player if the number is invalid
     */
    public static @Nullable QMessage validateNumber(String arg, int min, int max){
        try{
            int number = Integer.parseInt(arg);
            if(number < min) return Messages.ERROR_TRANSFER_INVALID_NUMBER_MIN.parse("blocks", String.valueOf(min));
            if(number > max) return Messages.ERROR_TRANSFER_INVALID_NUMBER_MAX.parse("blocks", String.valueOf(max));
            return null;
        } catch (NumberFormatException e){
            return Messages.ERROR_INVALID_NUMBER;
        }
    }
}
