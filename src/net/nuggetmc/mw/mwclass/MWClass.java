package net.nuggetmc.mw.mwclass;

import net.nuggetmc.mw.mwclass.info.Diamond;
import net.nuggetmc.mw.mwclass.info.MWClassInfo;
import net.nuggetmc.mw.mwclass.info.Playstyle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface MWClass extends Listener {
    String getName();
    Material getIcon();
    MWClassInfo getInfo();
    Playstyle[] getPlaystyles();
    Diamond[] getDiamonds();

    void assign(Player player);
}
