package dev.compasses.quinnsperms.neoforge;

import dev.compasses.quinnsperms.CommonMain;
import dev.compasses.quinnsperms.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class Main {
    public Main(IEventBus eventBus) {
        CommonMain.init();
    }
}