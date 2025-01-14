package mod.linguardium.sonicnoom;

import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public final class ConfigManager {
    public static final CustomGameRuleCategory SONIC_NOOM_RULES = new CustomGameRuleCategory(Identifier.of("sonicnoom","rules"), Text.translatable("gamerule.category.sonicnoom").formatted(Formatting.YELLOW));
    public static final GameRules.Key<GameRules.BooleanRule> DISABLE_BOOM_RULE = GameRuleRegistry.register("disableSonicBoom",SONIC_NOOM_RULES, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> SHIELDS_BLOCK_BOOM = GameRuleRegistry.register("shieldsBlockSonicBoom",SONIC_NOOM_RULES, GameRuleFactory.createBooleanRule(true));
    public static void init() {
    }
}
