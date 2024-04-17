package gg.quartzdev.qgptrade.util;

import com.jeff_media.morepersistentdatatypes.DataType;
import gg.quartzdev.qgptrade.TradeAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PDC {

    private static NamespacedKey markKey = new NamespacedKey(TradeAPI.getPlugin(), "mark");
    private static NamespacedKey transactionKey = new NamespacedKey(TradeAPI.getPlugin(), "transaction-id");

    /**
     * Marks an {@link ItemMeta}. Note: You will need to update the {@link ItemStack} for the mark to work
     * @param itemMeta the {@link ItemMeta} to mark
     */
    public static void mark(ItemMeta itemMeta){
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(markKey, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Marks an item
     * @param itemStack the {@link ItemStack} to mark
     */
    public static void mark(ItemStack itemStack){
        if(!itemStack.hasItemMeta()){
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        mark(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     *  Checks if an {@link ItemMeta} is marked
     * @param itemMeta where to check
     * @return true if the {@link ItemMeta} is marked, false if not
     */
    public static boolean isMarked(ItemMeta itemMeta){
        return itemMeta.getPersistentDataContainer().has(markKey);
    }
    /**
     *  Checks if an item is marked
     * @param item where to check
     * @return true if the {@link Item} is marked, false if not
     */
    public static boolean isMarked(Item item){
        if(!item.getItemStack().hasItemMeta()) return false;
        return isMarked(item.getItemStack().getItemMeta());
    }

    public static @Nullable UUID getTransactionId(ItemMeta itemMeta){
        final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(transactionKey, DataType.UUID);
    }

    public static @Nullable UUID getTransactionId(ItemStack itemStack){
        final ItemMeta itemMeta = itemStack.getItemMeta();
        return getTransactionId(itemMeta);
    }

    public static void setTransactionId(ItemMeta itemMeta, UUID transactionId){
        final PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(transactionKey, DataType.UUID, transactionId);
    }

    public static void setTransactionId(ItemStack itemStack, UUID transactionId){
        final ItemMeta itemMeta = itemStack.getItemMeta();
        setTransactionId(itemMeta, transactionId);
        itemStack.setItemMeta(itemMeta);
    }

}
