package script.packetdecoder.widget;

import com.palidino.osrs.io.Widget;
import com.palidino.osrs.io.cache.WidgetId;
import com.palidino.osrs.model.player.Player;

public class UnmorphWidget implements Widget {
    @Override
    public int[] getIds() {
        return new int[] {
            WidgetId.UNMORPH
        };
    }

    @Override
    public void execute(Player player, int index, int widgetId, int childId, int slot, int itemId) {
        switch (childId) {
        case 5:
            player.getMovement().stopViewing();
            player.getWidgetManager().removeInteractiveWidgets();
            player.getAppearance().setNpcId(-1);
            break;
        }
    }
}
