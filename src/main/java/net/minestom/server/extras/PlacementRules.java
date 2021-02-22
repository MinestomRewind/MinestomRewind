package net.minestom.server.extras;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockManager;
import net.minestom.server.instance.block.rule.vanilla.AxisPlacementRule;
import net.minestom.server.instance.block.rule.vanilla.RedstonePlacementRule;

public final class PlacementRules {

	public static void init() {
		BlockManager blockManager = MinecraftServer.getBlockManager();
		blockManager.registerBlockPlacementRule(new RedstonePlacementRule());
		blockManager.registerBlockPlacementRule(new AxisPlacementRule(Block.HAY_BLOCK));
		blockManager.registerBlockPlacementRule(new AxisPlacementRule(Block.LOG));
		blockManager.registerBlockPlacementRule(new AxisPlacementRule(Block.LOG2));
	}
}
