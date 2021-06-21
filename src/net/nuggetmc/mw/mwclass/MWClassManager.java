package net.nuggetmc.mw.mwclass;

import net.nuggetmc.mw.utils.ItemUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MWClassManager implements Listener {

    private static Map<String, MWClass> classes = new HashMap<>();
    private static Map<Player, MWClass> active = new HashMap<>();

    public static void register(MWClass mwclass) {
        classes.put(mwclass.getName(), mwclass);
    }

    public static Map<String, MWClass> getClasses() {
        return classes;
    }

    public static MWClass fetch(String name) {
        if (classes.containsKey(name)) {
            return classes.get(name);
        }

        return null;
    }

    public static void assign(Player player, MWClass mwclass) {
        player.getInventory().clear();
        player.setMaxHealth(40);
        player.setHealth(40);
        player.setFoodLevel(20);
        player.setSaturation(20);

        mwclass.assign(player);

        active.put(player, mwclass);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        check(event, event.getPlayer(), event.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        check(event, event.getPlayer(), event.getItem().getItemStack());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (active.containsKey(player)) {
            active.remove(player);
        }

        event.getDrops().removeIf(ItemUtils::isKitItem);
    }

    @EventHandler
    public void onItemTransfer(InventoryClickEvent event) {
        InventoryType type = event.getInventory().getType();

        if (type == InventoryType.PLAYER || type == InventoryType.CRAFTING) {
            return;
        }

        check(event, (Player) event.getWhoClicked(), event.getCurrentItem());
    }

    private void check(Cancellable event, Player player, ItemStack item) {
        if (ItemUtils.isKitItem(item) && player.getGameMode() == GameMode.SURVIVAL) {
            event.setCancelled(true);
        }
    }
}
