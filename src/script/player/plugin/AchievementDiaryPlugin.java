/**
 * @author Dalton H. (Palidino)
 */
package script.player.plugin;

import com.palidino.osrs.io.cache.ItemId;
import com.palidino.osrs.io.cache.NpcId;
import com.palidino.osrs.io.cache.WidgetId;
import com.palidino.osrs.model.player.Player;
import com.palidino.osrs.model.player.PlayerPlugin;
import com.palidino.osrs.model.player.Skills;
import com.palidino.util.Time;
import com.palidino.util.event.Event;
import lombok.var;

public class AchievementDiaryPlugin extends PlayerPlugin {
    private transient Player player;

    private String faladorShield1And2;
    private String faladorShield3And4;

    @Override
    public void login() {
        player = getPlayer();
    }

    @Override
    public boolean widgetHook(int index, int widgetId, int childId, int slot, int itemId) {
        var isEquipment = widgetId == WidgetId.EQUIPMENT || widgetId == WidgetId.EQUIPMENT_BONUSES;
        if (widgetId == WidgetId.INVENTORY || isEquipment) {
            var prayerLevel = player.getController().getLevelForXP(Skills.PRAYER);
            switch (itemId) {
            case ItemId.FALADOR_SHIELD_1:
            case ItemId.FALADOR_SHIELD_2:
                if (isEquipment && index == 1 || !isEquipment && index == 2) {
                    if (Time.getSimpleDate().equals(faladorShield1And2)) {
                        player.getGameEncoder().sendMessage("You can only use this once a day.");
                        return true;
                    }
                    faladorShield1And2 = Time.getSimpleDate();
                    if (itemId == ItemId.FALADOR_SHIELD_1) {
                        player.getPrayer().adjustPoints((int) (prayerLevel * 0.25));
                    } else if (itemId == ItemId.FALADOR_SHIELD_2) {
                        player.getPrayer().adjustPoints((int) (prayerLevel * 0.5));
                    }
                    player.getGameEncoder().sendMessage("Your prayer points have been restored.");
                    return true;
                }
                break;
            case ItemId.FALADOR_SHIELD_3:
            case ItemId.FALADOR_SHIELD_4:
                if (isEquipment && index == 1 || !isEquipment && index == 2) {
                    if (!Time.getSimpleDate().equals(faladorShield3And4)) {
                        faladorShield3And4 = Time.getSimpleDate();
                        player.getPrayer().adjustPoints(prayerLevel);
                        player.getGameEncoder().sendMessage("Your prayer points have been restored.");
                    } else if (!Time.getSimpleDate().equals(faladorShield1And2)) {
                        faladorShield1And2 = Time.getSimpleDate();
                        if (itemId == ItemId.FALADOR_SHIELD_3) {
                            player.getPrayer().adjustPoints((int) (prayerLevel * 0.5));
                        } else if (itemId == ItemId.FALADOR_SHIELD_4) {
                            player.getPrayer().adjustPoints(prayerLevel);
                        }
                        player.getGameEncoder().sendMessage("Your prayer points have been restored.");
                    } else {
                        player.getGameEncoder().sendMessage("You can only use this once a day.");
                    }
                    return true;
                } else if (isEquipment && index == 2 || !isEquipment && index == 3) {
                    if (player.getRegionId() != 6992 && player.getRegionId() != 6993) {
                        player.getGameEncoder().sendMessage("You can't use this here.");
                        return true;
                    }
                    var mole = player.getController().getNpc(NpcId.GIANT_MOLE_230);
                    if (mole == null) {
                        player.getGameEncoder().sendMessage("Unable to locate the giant mole.");
                        return true;
                    }
                    player.getGameEncoder().sendHintIconTile(mole);
                    player.getWorld().addEvent(new Event(16) {
                        @Override
                        public void execute() {
                            stop();
                            if (!player.isVisible()) {
                                return;
                            }
                            player.getGameEncoder().sendHintIconReset();
                        }
                    });
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
