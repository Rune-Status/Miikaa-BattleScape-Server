package script.map.area;

import java.util.List;
import com.palidino.osrs.Main;
import com.palidino.osrs.io.cache.ItemId;
import com.palidino.osrs.io.cache.NpcId;
import com.palidino.osrs.io.cache.WidgetId;
import com.palidino.osrs.model.Tile;
import com.palidino.osrs.model.dialogue.DialogueEntry;
import com.palidino.osrs.model.dialogue.SelectionDialogueEntry;
import com.palidino.osrs.model.guide.Guide;
import com.palidino.osrs.model.item.Item;
import com.palidino.osrs.model.item.MysteryBox;
import com.palidino.osrs.model.item.RandomItem;
import com.palidino.osrs.model.map.MapObject;
import com.palidino.osrs.model.map.Area;
import com.palidino.osrs.model.npc.Npc;
import com.palidino.osrs.model.player.Magic;
import com.palidino.osrs.model.player.PCombat;
import com.palidino.osrs.model.player.Player;
import com.palidino.osrs.model.player.controller.ClanWarsFreeForAllPC;
import com.palidino.osrs.world.ClanWarsTournament;
import com.palidino.setting.SqlUserRank;
import com.palidino.util.Utils;
import lombok.var;

public class EdgevilleArea extends Area {
    private static final DialogueEntry SKILLING_SELLER_DIALOGUE = new ExchangeSpecialSkillItemsDialogue();
    private static final DialogueEntry FFA_PORTAL_DIALOGUE = new FreeForAllPortalDialogue();
    private static final List<RandomItem> CRYSTAL_CHEST_ITEMS =
            RandomItem.buildList(new RandomItem(ItemId.LOOP_HALF_OF_KEY), new RandomItem(ItemId.TOOTH_HALF_OF_KEY),
                    new RandomItem(ItemId.BABYDRAGON_BONES, 1, 150), new RandomItem(ItemId.DRAGON_BONES, 1, 50),
                    new RandomItem(ItemId.LAVA_DRAGON_BONES, 1, 25), new RandomItem(ItemId.WYRM_BONES, 1, 80),
                    new RandomItem(ItemId.DRAKE_BONES, 1, 30), new RandomItem(ItemId.HYDRA_BONES, 1, 20),
                    new RandomItem(ItemId.RAW_KARAMBWAN, 1, 200), new RandomItem(ItemId.RAW_SWORDFISH, 1, 650),
                    new RandomItem(ItemId.RAW_MONKFISH, 1, 350), new RandomItem(ItemId.RAW_SHARK, 1, 150),
                    new RandomItem(ItemId.RAW_ANGLERFISH, 1, 80), new RandomItem(ItemId.RAW_DARK_CRAB, 1, 100),
                    new RandomItem(ItemId.OAK_LOGS, 1, 1200), new RandomItem(ItemId.WILLOW_LOGS, 1, 900),
                    new RandomItem(ItemId.MAPLE_LOGS, 1, 600), new RandomItem(ItemId.YEW_LOGS, 1, 300),
                    new RandomItem(ItemId.MAGIC_LOGS, 1, 100), new RandomItem(ItemId.REDWOOD_LOGS, 1, 350),
                    new RandomItem(ItemId.GRIMY_RANARR_WEED, 1, 15), new RandomItem(ItemId.GRIMY_TOADFLAX, 1, 60),
                    new RandomItem(ItemId.GRIMY_IRIT_LEAF, 1, 150), new RandomItem(ItemId.GRIMY_KWUARM, 1, 100),
                    new RandomItem(ItemId.GRIMY_SNAPDRAGON, 1, 15), new RandomItem(ItemId.GRIMY_CADANTINE, 1, 80),
                    new RandomItem(ItemId.GRIMY_LANTADYME, 1, 80), new RandomItem(ItemId.GRIMY_DWARF_WEED, 1, 200),
                    new RandomItem(ItemId.GRIMY_TORSTOL, 1, 15), new RandomItem(ItemId.AMYLASE_CRYSTAL, 100),
                    new RandomItem(ItemId.LAVA_SCALE_SHARD, 1, 250), new RandomItem(ItemId.MARK_OF_GRACE, 1, 20),
                    new RandomItem(ItemId.COAL, 1, 700), new RandomItem(ItemId.GOLD_ORE, 1, 300),
                    new RandomItem(ItemId.MITHRIL_ORE, 1, 600), new RandomItem(ItemId.ADAMANTITE_ORE, 1, 80),
                    new RandomItem(ItemId.RUNITE_ORE, 1, 10), new RandomItem(ItemId.AMETHYST, 1, 30),
                    new RandomItem(ItemId.GREEN_DRAGON_LEATHER, 1, 60),
                    new RandomItem(ItemId.BLUE_DRAGON_LEATHER, 1, 50), new RandomItem(ItemId.RED_DRAGON_LEATHER, 1, 40),
                    new RandomItem(ItemId.BLACK_DRAGON_LEATHER, 1, 30), new RandomItem(ItemId.UNCUT_DRAGONSTONE),
                    new RandomItem(ItemId.FIRE_ORB, 1, 80), new RandomItem(ItemId.IRON_BAR, 1, 650),
                    new RandomItem(ItemId.STEEL_BAR, 1, 250), new RandomItem(ItemId.GOLD_BAR, 1, 1200),
                    new RandomItem(ItemId.MITHRIL_BAR, 1, 150), new RandomItem(ItemId.ADAMANTITE_BAR, 1, 50),
                    new RandomItem(ItemId.RUNITE_BAR, 1, 10), new RandomItem(ItemId.DRAGON_DART_TIP, 1, 30),
                    new RandomItem(ItemId.DRAGON_ARROWTIPS, 1, 30), new RandomItem(ItemId.DRAGON_JAVELIN_HEADS, 1, 30),
                    new RandomItem(ItemId.MAGIC_STOCK), new RandomItem(ItemId.RUNITE_LIMBS),
                    new RandomItem(ItemId.ONYX_BOLT_TIPS, 1, 12), new RandomItem(ItemId.PURE_ESSENCE, 3000, 6000),
                    new RandomItem(ItemId.UNCUT_ONYX).setWeight(1), new RandomItem(ItemId.ZENYTE_SHARD).setWeight(1));

    public EdgevilleArea() {
        super(12342, 12441, 12442);
    }

    @Override
    public boolean npcOptionHook(int index, Npc npc) {
        var player = getPlayer();
        switch (npc.getId()) {
        case NpcId.WISE_OLD_MAN:
            player.openShop("platinum_tokens");
            return true;
        case NpcId.VOTE_MANAGER:
            if (index == 0) {
                player.openDialogue("vote", 0);
            } else if (index == 3) {
                player.openShop("vote");
            }
            return true;
        case NpcId.CAPT_BOND_16018:
            player.getBonds().sendPouch();
            return true;
        case NpcId.LOYALTY_MANAGER:
            player.openDialogue("loyalty", 0);
            return true;
        case NpcId.GUIDE:
            Guide.openDialogue(player, "main");
            return true;
        case NpcId.MAKE_OVER_MAGE:
            player.getWidgetManager().sendInteractiveOverlay(WidgetId.CHARACTER_DESIGN);
            return true;
        case NpcId.PERDU:
            player.openDialogue("perdu", 0);
            return true;
        case NpcId.MAGE_OF_ZAMORAK_2582:
            player.openDialogue("magezamorak", 0);
            return true;
        case NpcId.BOB_BARTER_HERBS:
            if (index == 0) {
                player.openDialogue("bobbarter", 0);
            } else if (index == 2) {
                player.openShop("herb_exchange");
            } else if (index == 3) {
                player.getSkills().decantAllPotions();
            }
            return true;
        case NpcId.PROBITA:
            player.openShop("pets");
            return true;
        case NpcId.HEAD_CHEF:
            player.openShop("supplies");
            return true;
        case NpcId.AJJAT:
            player.openShop("equipment");
            return true;
        case NpcId.TWIGGY_OKORN:
            player.openShop("diaries");
            return true;
        case NpcId.MAC_126:
            player.openDialogue("mac", 0);
            return true;
        case NpcId.SKILLING_SELLER:
            if (index == 0) {
                player.openDialogue(SKILLING_SELLER_DIALOGUE);
            } else if (index == 2) {
                player.openShop("skilling");
            }
            return true;
        case NpcId.CAPN_IZZY_NO_BEARD:
            player.openShop("agility");
            return true;
        case NpcId.EMBLEM_TRADER_316:
            if (index == 0) {
                player.openDialogue("emblemtrader", 0);
            } else if (index == 2) {
                player.openShop("blood_money");
            } else if (index == 3) {
                player.getCombat().setShowKDR(!player.getCombat().showKDR());
                player.getGameEncoder().sendMessage("Streaks: " + player.getCombat().showKDR());
            } else if (index == 4) {
                player.getCombat().setPKSkullDelay(PCombat.SKULL_DELAY);
            }
            return true;
        case NpcId.JOSSIK:
            player.openDialogue("horrorfromthedeep", 0);
            return true;
        case NpcId.EVIL_DAVE_4806:
            player.openDialogue("shadowofthestorm", 0);
            return true;
        case NpcId.RADIMUS_ERKLE:
            if (!player.getCombat().isLegendsQuestComplete()) {
                if (player.getCombat().getRecipeForDisasterStage() != 6) {
                    player.getGameEncoder().sendMessage("You need to complete Recipe for Disaster.");
                    return true;
                } else if (!player.getCombat().getHorrorFromTheDeep()) {
                    player.getGameEncoder().sendMessage("You need to complete Horror from the Deep.");
                    return true;
                } else if (!player.getCombat().getDreamMentor()) {
                    player.getGameEncoder().sendMessage("You need to complete Dream Mentor.");
                    return true;
                } else if (!player.getCombat().getMageArena()) {
                    player.getGameEncoder().sendMessage("You need to complete the Mage Arena.");
                    return true;
                } else if (!player.getCombat().getLostCity()) {
                    player.getGameEncoder().sendMessage("You need to complete Lost City.");
                    return true;
                } else if (!player.getCombat().getDragonSlayer()) {
                    player.getGameEncoder().sendMessage("You need to complete Dragon Slayer.");
                    return true;
                } else if (!player.getCombat().getMonkeyMadness()) {
                    player.getGameEncoder().sendMessage("You need to complete Monkey Madness.");
                    return true;
                } else if (player.getCombat().getHauntedMine() <= 3) {
                    player.getGameEncoder().sendMessage("You need to complete Haunted Mine.");
                    return true;
                }
                player.getMovement().teleport(2774, 9338, 0);
            } else {
                player.getMovement().teleport(2728, 3351, 0);
            }
            return true;
        case NpcId.GUILDMASTER:
            player.openDialogue("dragonslayer", 0);
            return true;
        case NpcId.MONK_OF_ENTRANA:
            player.openDialogue("lostcity", 0);
            return true;
        case NpcId.KING_NARNODE_SHAREEN:
            player.openDialogue("monkeymadness", 0);
            return true;
        case NpcId.ONEIROMANCER:
            player.openDialogue("dreammentor", 0);
            return true;
        case NpcId.ZEALOT:
            player.openDialogue("hauntedmine", 0);
            return true;
        case NpcId.SURGEON_GENERAL_TAFANI:
            if (player.getX() != 3094 || player.getY() != 3498) {
                return true;
            }
            player.setGraphic(436);
            player.getGameEncoder().sendMessage(npc.getDef().getName() + " restores you.");
            player.rejuvenate();
            return true;
        }
        return false;
    }

    @Override
    public boolean mapObjectOptionHook(int index, MapObject mapObject) {
        var player = getPlayer();
        switch (mapObject.getId()) {
        case 172: // Closed chest: crystal chest
            openCrystalChest(player);
            return true;
        case 884: // Wishing well
            player.openDialogue("wishingwell", 0);
            return true;
        case 1581: // Trapdoor: Edgeville dungeon entrance
            player.getMovement().ladderUpTeleport(new Tile(3096, 9867));
            return true;
        case 12309: // Chest: Recipe for Disaster
            if (Main.isSpawn()) {
                return true;
            }
            player.openDialogue("recipefordisaster", 0);
            return true;
        case 17385: // Ladder: Edgeville dungeon exit
            player.getMovement().ladderUpTeleport(new Tile(3097, 3486));
            return true;
        case 23709: // Box of Health
            if (player.getController().inPvPWorldCombat()) {
                player.getGameEncoder().sendMessage("You can't use this here.");
                return true;
            }
            player.setGraphic(436);
            player.getGameEncoder().sendMessage("The pool restores you.");
            player.rejuvenate();
            return true;
        case 26081: // Gate
        case 26082: // Gate
            if (!player.getInventory().isEmpty() || !player.getEquipment().isEmpty()) {
                player.getGameEncoder().sendMessage("No items can be taken beyond this point.");
                return true;
            }
            player.getClanWars().openJoinTournament();
            return true;
        case 26645: // Free-for-all portal
            player.openDialogue(FFA_PORTAL_DIALOGUE);
            return true;
        case 26743: // Viewing orb
            player.getClanWars().teleportViewing(0);
            return true;
        case 26761: // Lever
            if (player.getMovement().getTeleportBlock() > 0) {
                player.getGameEncoder().sendMessage("A teleport block has been cast on you. It should wear off in "
                        + player.getMovement().getTeleportBlockRemaining() + ".");
                return true;
            }
            var tile = new Tile(3153, 3923, 0);
            if (player.getClientHeight() == tile.getHeight()) {
                tile.setHeight(player.getHeight());
            }
            if (!player.getController().canTeleport(tile, true)) {
                return true;
            }
            player.getMagic().standardTeleport(tile);
            player.clearHits();
            return true;
        case 27269: // Deadman chest
            if (player.getInventory().hasItem(ItemId.SINISTER_KEY)) {
                player.getCombat().getBarrows().openChest(mapObject.getX() != 3551 || mapObject.getY() != 9695);
                return true;
            }
            var mysteryId = -1;
            if (player.getInventory().hasItem(ItemId.BLOODIER_KEY_32305)) {
                mysteryId = ItemId.BLOODIER_KEY_32305;
            } else if (player.getInventory().hasItem(ItemId.BLOODY_KEY_32304)) {
                mysteryId = ItemId.BLOODY_KEY_32304;
            } else if (player.getInventory().hasItem(ItemId.DIAMOND_KEY_32309)) {
                mysteryId = ItemId.DIAMOND_KEY_32309;
            } else if (player.getInventory().hasItem(ItemId.GOLD_KEY_32308)) {
                mysteryId = ItemId.GOLD_KEY_32308;
            } else if (player.getInventory().hasItem(ItemId.SILVER_KEY_32307)) {
                mysteryId = ItemId.SILVER_KEY_32307;
            } else if (player.getInventory().hasItem(ItemId.BRONZE_KEY_32306)) {
                mysteryId = ItemId.BRONZE_KEY_32306;
            }
            if (mysteryId != -1) {
                MysteryBox.open(player, mysteryId);
            } else {
                player.getGameEncoder().sendMessage("You need a key to open this chest.");
            }
            return true;
        case 29087: // Coffer
            if (player.getRights() == Player.RIGHTS_ADMIN || player.isUsergroup(SqlUserRank.ADVERTISEMENT_MANAGER)) {
                player.openDialogue("clanwars", 6);
            } else {
                ClanWarsTournament.viewDonatedItems(player);
            }
            return true;
        case 29150: // Altar of the Occult
            if (player.getController().inPvPWorldCombat()) {
                player.getGameEncoder().sendMessage("You can't use this here.");
                return true;
            }
            if (index == 0) {
                player.openDialogue("spellbooks", 0);
            } else if (index == 1) {
                if (player.getMagic().getSpellbook() == Magic.STANDARD_MAGIC) {
                    player.getMagic().setSpellbook(Magic.ANCIENT_MAGIC);
                } else if (player.getMagic().getSpellbook() == Magic.ANCIENT_MAGIC
                        || player.getMagic().getSpellbook() == Magic.LUNAR_MAGIC) {
                    player.getMagic().setSpellbook(Magic.STANDARD_MAGIC);
                }
            } else if (index == 2) {
                if (player.getMagic().getSpellbook() == Magic.STANDARD_MAGIC
                        || player.getMagic().getSpellbook() == Magic.ANCIENT_MAGIC) {
                    player.getMagic().setSpellbook(Magic.LUNAR_MAGIC);
                } else if (player.getMagic().getSpellbook() == Magic.LUNAR_MAGIC) {
                    player.getMagic().setSpellbook(Magic.ANCIENT_MAGIC);
                }
            }
            return true;
        case 29156: // Ornate Jewellery Box
            player.getWidgetManager().sendInteractiveOverlay(WidgetId.JEWELRY_BOX);
            player.getGameEncoder().sendScript(1685, 15, "Ornate Jewellery Box", 3);
            player.getGameEncoder().sendWidgetSettings(WidgetId.JEWELRY_BOX, 0, 0, 24, 1);
            return true;
        case 29229: // Spiritual Fairy Tree
            if (index == 0) {
                player.openDialogue("spirittree", 0);
            } else if (index == 1) {
                player.openDialogue("fairyring", 0);
            }
            return true;
        case 29241: // Ornate rejuvenation pool
            if (player.getController().inPvPWorldCombat()) {
                player.getGameEncoder().sendMessage("You can't use this here.");
                return true;
            }
            player.setGraphic(436);
            player.getGameEncoder().sendMessage("The pool restores you.");
            player.rejuvenate();
            return true;
        case 28857: // Rope ladder
            player.getMovement().ladderUpTeleport(new Tile(3127, 3468, 3));
            return true;
        case 28858: // Rope ladder
            player.getMovement().ladderDownTeleport(new Tile(3127, 3469));
            return true;
        }
        return false;
    }

    private void openCrystalChest(Player player) {
        if (player.getInventory().hasItem(ItemId.CRYSTAL_KEY)) {
            if (player.getInventory().getRemainingSlots() < 4) {
                player.getInventory().notEnoughSpace();
                return;
            }
            player.getInventory().deleteItem(ItemId.CRYSTAL_KEY);
            player.getInventory().addOrDropItem(ItemId.UNCUT_DRAGONSTONE_NOTED);
            var clueItems = new RandomItem[] {
                new RandomItem(ItemId.CLUE_SCROLL_EASY).setWeight(8),
                new RandomItem(ItemId.CLUE_SCROLL_MEDIUM).setWeight(6),
                new RandomItem(ItemId.CLUE_SCROLL_HARD).setWeight(4),
                new RandomItem(ItemId.CLUE_SCROLL_ELITE).setWeight(2),
                new RandomItem(ItemId.CLUE_SCROLL_MASTER).setWeight(1)
            };
            if (Utils.randomE(4) == 0) {
                player.getInventory().addOrDropItem(RandomItem.getItem(clueItems));
            }
            for (int i = 0; i < 2; i++) {
                var item = RandomItem.getItem(CRYSTAL_CHEST_ITEMS);
                var amount = item.getAmount();
                if (amount > 1 && player.isGameModeIronmanRelated()) {
                    amount /= 2;
                }
                player.getInventory().addOrDropItem(item.getNotedId(), amount);
            }
            player.getGameEncoder().sendMessage("You find some treasure in the chest!");
        } else {
            player.getGameEncoder().sendMessage("You need a key to open this.");
        }
    }
}


class ExchangeSpecialSkillItemsDialogue extends SelectionDialogueEntry {
    public ExchangeSpecialSkillItemsDialogue() {
        super("Choose an Option", "View shop.", "Exchange special skilling items.", "Nevermind.");
        setScript((player, index, childId, slot) -> {
            if (slot == 0) {
                player.openShop("skilling");
            } else if (slot == 1) {
                var unusualFishCount = player.getInventory().getCount(ItemId.UNUSUAL_FISH);
                if (unusualFishCount > 0) {
                    var value = 300_000;
                    if (player.isGameModeIronmanRelated()) {
                        value /= 2;
                    }
                    player.getInventory().deleteItem(ItemId.UNUSUAL_FISH, unusualFishCount);
                    player.getInventory().addOrDropItem(ItemId.COINS, value * unusualFishCount);
                    if (Utils.randomE(156 / unusualFishCount) == 0) {
                        player.getInventory().addOrDropItem(ItemId.GOLDEN_TENCH);
                        player.getGameEncoder()
                                .sendMessage("<col=ff0000>The cormorant has brought you a very strange tench.</col>");
                    }
                }
                var coloredEggCount = player.getInventory().getCount(ItemId.BIRDS_EGG)
                        + player.getInventory().getCount(ItemId.BIRDS_EGG_5077)
                        + player.getInventory().getCount(ItemId.BIRDS_EGG_5078);
                if (coloredEggCount > 0) {
                    var value = 500_000;
                    if (player.isGameModeIronmanRelated()) {
                        value /= 2;
                    }
                    player.getInventory().deleteItem(ItemId.BIRDS_EGG, coloredEggCount);
                    player.getInventory().deleteItem(ItemId.BIRDS_EGG_5077, coloredEggCount);
                    player.getInventory().deleteItem(ItemId.BIRDS_EGG_5078, coloredEggCount);
                    player.getInventory().addOrDropItem(ItemId.COINS, value * coloredEggCount);
                    player.getInventory().addOrDropItem(ItemId.BIRD_NEST_5073, 1 * coloredEggCount);
                    if (Utils.randomE(132 / coloredEggCount) == 0) {
                        Item[] evilChickenOutfit = {
                            new Item(ItemId.EVIL_CHICKEN_FEET), new Item(ItemId.EVIL_CHICKEN_WINGS),
                            new Item(ItemId.EVIL_CHICKEN_HEAD), new Item(ItemId.EVIL_CHICKEN_LEGS)
                        };
                        if (!player.hasItem(ItemId.EVIL_CHICKEN_FEET)) {
                            player.getInventory().addOrDropItem(ItemId.EVIL_CHICKEN_FEET);
                        } else if (!player.hasItem(ItemId.EVIL_CHICKEN_WINGS)) {
                            player.getInventory().addOrDropItem(ItemId.EVIL_CHICKEN_WINGS);
                        } else if (!player.hasItem(ItemId.EVIL_CHICKEN_HEAD)) {
                            player.getInventory().addOrDropItem(ItemId.EVIL_CHICKEN_HEAD);
                        } else if (!player.hasItem(ItemId.EVIL_CHICKEN_LEGS)) {
                            player.getInventory().addOrDropItem(ItemId.EVIL_CHICKEN_LEGS);
                        } else {
                            player.getInventory().addOrDropItem(Utils.arrayRandom(evilChickenOutfit));
                        }
                    }
                }
                var unidentifiedMineralCount = player.getInventory().getCount(ItemId.UNIDENTIFIED_MINERALS);
                if (unidentifiedMineralCount > 0) {
                    var value = 300_000;
                    if (player.isGameModeIronmanRelated()) {
                        value /= 2;
                    }
                    player.getInventory().deleteItem(ItemId.UNIDENTIFIED_MINERALS, unidentifiedMineralCount);
                    player.getInventory().addOrDropItem(ItemId.COINS, value * unidentifiedMineralCount);
                }
                if (unusualFishCount == 0 && coloredEggCount == 0 && unidentifiedMineralCount == 0) {
                    player.getGameEncoder().sendMessage("You have no special items to exchange.");
                }
            }
        });
    }
}


class FreeForAllPortalDialogue extends SelectionDialogueEntry {
    public FreeForAllPortalDialogue() {
        super("Choose an Option", "Safe Free-For-All", "Risk Zone");
        setScript((player, index, childId, slot) -> {
            if (slot == 0) {
                player.getMovement().teleport(3327, 4752);
                player.setController(new ClanWarsFreeForAllPC());
            } else if (slot == 1) {
                player.getMovement().teleport(2655, 5471);
            }
        });
    }
}
