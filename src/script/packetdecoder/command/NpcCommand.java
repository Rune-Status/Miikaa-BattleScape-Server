package script.packetdecoder.command;

import com.palidino.osrs.Main;
import com.palidino.osrs.io.Command;
import com.palidino.osrs.io.cache.NpcId;
import com.palidino.osrs.model.npc.Npc;
import com.palidino.osrs.model.player.Player;
import lombok.var;

public class NpcCommand implements Command {
    @Override
    public String getExample() {
        return "id_or_name";
    }

    @Override
    public boolean canUse(Player player) {
        return Main.isLocal();
    }

    @Override
    public void execute(Player player, String message) {
        var id = -1;
        if (message.matches("[0-9]+")) {
            id = Integer.parseInt(message);
        } else {
            id = NpcId.valueOf(message.toUpperCase());
        }
        var npc = new Npc(player.getController(), id, player.getX(), player.getY(), player.getHeight());
        player.getGameEncoder().sendMessage("Spawned " + npc.getDef().getName());
    }
}
