package gg.quartzdev.qgptrade.util;

public class Args {

    public static int parseInt(String arg){
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e){
            return 0;
        }
    }
}
