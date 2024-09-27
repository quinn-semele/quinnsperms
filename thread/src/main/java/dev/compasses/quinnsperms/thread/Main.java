package dev.compasses.quinnsperms.thread;

import dev.compasses.quinnsperms.CommonMain;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonMain.init();
    }
}