package tfar.dankstorage;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import tfar.dankstorage.block.DankDispenserBehavior;
import tfar.dankstorage.block.DockBlock;
import tfar.dankstorage.client.Client;
import tfar.dankstorage.container.DankMenu;
import tfar.dankstorage.container.DockMenu;
import tfar.dankstorage.item.DankItem;
import tfar.dankstorage.item.UpgradeInfo;
import tfar.dankstorage.item.UpgradeItem;
import tfar.dankstorage.network.DankPacketHandler;
import tfar.dankstorage.recipe.Serializer2;
import tfar.dankstorage.blockentity.DockBlockEntity;
import tfar.dankstorage.utils.DankStats;

import java.util.stream.IntStream;

public class DankStorage implements ModInitializer, ClientModInitializer {

    public static final String MODID = "dankstorage";

    public static Block dock = null;
    public static MenuType<DockMenu> dank_1_container = null;
    public static MenuType<DockMenu> dank_2_container = null;
    public static MenuType<DockMenu> dank_3_container = null;
    public static MenuType<DockMenu> dank_4_container = null;
    public static MenuType<DockMenu> dank_5_container = null;
    public static MenuType<DockMenu> dank_6_container = null;
    public static MenuType<DockMenu> dank_7_container = null;
    public static MenuType<DankMenu> portable_dank_1_container = null;
    public static MenuType<DankMenu> portable_dank_2_container = null;
    public static MenuType<DankMenu> portable_dank_3_container = null;
    public static MenuType<DankMenu> portable_dank_4_container = null;
    public static MenuType<DankMenu> portable_dank_5_container = null;
    public static MenuType<DankMenu> portable_dank_6_container = null;
    public static MenuType<DankMenu> portable_dank_7_container = null;
    public static BlockEntityType<?> dank_tile = null;
    public static RecipeSerializer<?> upgrade = null;

    public DankStorage() {
    }

    @Override
    public void onInitialize() {
        Item.Properties properties = new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS);
        Registry.register(Registry.BLOCK, new ResourceLocation(MODID, "dock"), dock = new DockBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1, 30)));
        Registry.register(Registry.ITEM, new ResourceLocation(MODID, "dock"), new BlockItem(dock, properties));
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(MODID, "dank_tile"), dank_tile = BlockEntityType.Builder.of(DockBlockEntity::new, dock).build(null));

        IntStream.range(1, 8).forEach(i -> {
            DankItem dankItem = new DankItem(properties.stacksTo(1), DankStats.values()[i]);
            DispenserBlock.registerBehavior(dankItem, new DankDispenserBehavior());
            Registry.register(Registry.ITEM, new ResourceLocation(MODID, "dank_" + i), dankItem);
        });
        IntStream.range(1, 7).forEach(i -> Registry.register(Registry.ITEM, new ResourceLocation(MODID, i + "_to_" + (i + 1)), new UpgradeItem(properties, new UpgradeInfo(i, i + 1))));
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(MODID, "upgrade"), upgrade = new Serializer2());

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_1"), dank_1_container = new MenuType<>(DockMenu::t1));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_1"), portable_dank_1_container = new MenuType<>(DankMenu::t1));

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_2"), dank_2_container = new MenuType<>(DockMenu::t2));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_2"), portable_dank_2_container = new MenuType<>(DankMenu::t2));

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_3"), dank_3_container = new MenuType<>(DockMenu::t3));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_3"), portable_dank_3_container = new MenuType<>(DankMenu::t3));

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_4"), dank_4_container = new MenuType<>(DockMenu::t4));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_4"), portable_dank_4_container = new MenuType<>(DankMenu::t4));

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_5"), dank_5_container = new MenuType<>(DockMenu::t5));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_5"), portable_dank_5_container = new MenuType<>(DankMenu::t5));

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_6"), dank_6_container = new MenuType<>(DockMenu::t6));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_6"), portable_dank_6_container = new MenuType<>(DankMenu::t6));

        Registry.register(Registry.MENU, new ResourceLocation(MODID, "dank_7"), dank_7_container = new MenuType<>(DockMenu::t7));
        Registry.register(Registry.MENU, new ResourceLocation(MODID, "portable_dank_7"), portable_dank_7_container = new MenuType<>(DankMenu::t7));


        DankPacketHandler.registerMessages();
    }

    @Override
    public void onInitializeClient() {
        Client.client();
    }


    /*

  public static final ClientConfig CLIENT;
  public static final ForgeConfigSpec CLIENT_SPEC;

  public static final ServerConfig SERVER;
  public static final ForgeConfigSpec SERVER_SPEC;

  static {
    final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
    CLIENT_SPEC = specPair.getRight();
    CLIENT = specPair.getLeft();
    final Pair<ServerConfig, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
    SERVER_SPEC = specPair2.getRight();
    SERVER = specPair2.getLeft();
  }


  public static class ClientConfig {
    public static ForgeConfigSpec.BooleanValue preview;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
      builder.push("client");
      preview = builder
              .comment("Whether to display the preview of the item in the dank, disable if you have optifine")
              .define("preview", true);
      builder.pop();
    }
  }

  public static class ServerConfig {
    public static ForgeConfigSpec.IntValue stacklimit1;
    public static ForgeConfigSpec.IntValue stacklimit2;
    public static ForgeConfigSpec.IntValue stacklimit3;
    public static ForgeConfigSpec.IntValue stacklimit4;
    public static ForgeConfigSpec.IntValue stacklimit5;
    public static ForgeConfigSpec.IntValue stacklimit6;
    public static ForgeConfigSpec.IntValue stacklimit7;
    public static ForgeConfigSpec.BooleanValue useShareTag;
    public static ForgeConfigSpec.ConfigValue<List<String>> convertible_tags;

    public static final List<String> defaults = Lists.newArrayList(
            "forge:ingots/iron",
            "forge:ingots/gold",
            "forge:ores/coal",
            "forge:ores/diamond",
            "forge:ores/emerald",
            "forge:ores/gold",
            "forge:ores/iron",
            "forge:ores/lapis",
            "forge:ores/redstone",

            "forge:gems/amethyst",
            "forge:gems/peridot",
            "forge:gems/ruby",

            "forge:ingots/copper",
            "forge:ingots/lead",
            "forge:ingots/nickel",
            "forge:ingots/silver",
            "forge:ingots/tin",

            "forge:ores/copper",
            "forge:ores/lead",
            "forge:ores/ruby",
            "forge:ores/silver",
            "forge:ores/tin");

    public ServerConfig(ForgeConfigSpec.Builder builder) {
      builder.push("server");
      stacklimit1 = builder.
              comment("Stack limit of first dank storage")
              .defineInRange("stacklimit1", 256, 1, Integer.MAX_VALUE);
      stacklimit2 = builder.
              comment("Stack limit of second dank storage")
              .defineInRange("stacklimit2", 1024, 1, Integer.MAX_VALUE);
      stacklimit3 = builder.
              comment("Stack limit of third dank storage")
              .defineInRange("stacklimit3", 4096, 1, Integer.MAX_VALUE);
      stacklimit4 = builder.
              comment("Stack limit of fourth dank storage")
              .defineInRange("stacklimit4", 16384, 1, Integer.MAX_VALUE);
      stacklimit5 = builder.
              comment("Stack limit of fifth dank storage")
              .defineInRange("stacklimit5", 65536, 1, Integer.MAX_VALUE);
      stacklimit6 = builder.
              comment("Stack limit of sixth dank storage")
              .defineInRange("stacklimit6", 262144, 1, Integer.MAX_VALUE);
      stacklimit7 = builder.
              comment("Stack limit of seventh dank storage")
              .defineInRange("stacklimit7", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);

      convertible_tags = builder.
              comment("Tags that are eligible for conversion, input as a list of resourcelocation, eg 'forge:ingots/iron'")
              .define("convertible tags", defaults);
      builder.pop();
    }
  }*/
}
