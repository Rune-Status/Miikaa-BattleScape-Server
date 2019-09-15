package script.npc.combat;

import java.util.Arrays;
import java.util.List;
import com.palidino.osrs.io.cache.NpcId;
import com.palidino.osrs.model.npc.combat.NpcCombatDefinition;
import com.palidino.osrs.model.npc.combat.NpcCombatHitpoints;
import com.palidino.osrs.model.npc.combat.NpcCombatFocus;
import com.palidino.osrs.model.npc.combat.NpcCombat;
import lombok.var;

public class RockySupportCombat extends NpcCombat {
    @Override
    public List<NpcCombatDefinition> getCombatDefinitions() {
        var combat = NpcCombatDefinition.builder();
        combat.id(NpcId.ROCKY_SUPPORT);
        combat.hitpoints(NpcCombatHitpoints.total(175));
        combat.focus(NpcCombatFocus.builder().retaliationDisabled(true).build());
        combat.combatScript("InfernoSupportCS").deathAnimation(7561);


        return Arrays.asList(combat.build());
    }
}
