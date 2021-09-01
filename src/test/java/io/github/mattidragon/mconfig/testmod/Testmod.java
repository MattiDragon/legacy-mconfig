package io.github.mattidragon.mconfig.testmod;

import com.google.gson.JsonPrimitive;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import io.github.mattidragon.mconfig.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

public class Testmod implements ModInitializer, ClientModInitializer, ModMenuApi {
    public static final Config CONFIG = Config.of(new Identifier("mconfigtest", "main"));
    public static final Config CONFIG2 = Config.of("mconfigtest");
    
    @Override
    public void onInitialize() {
        if (CONFIG.getOption("object.field").getAsInt() != 7) Config.LOGGER.warn("Test 1: failed");
        else Config.LOGGER.info("Test 1: passed");
    
        if (!CONFIG2.getOption("hello").getAsString().equals("world")) Config.LOGGER.warn("Test 2: failed");
        else Config.LOGGER.info("Test 2: passed");
        
        CONFIG2.setOption("hello", new JsonPrimitive("wrld"));
        if (!CONFIG2.getOption("hello").getAsString().equals("wrld")) Config.LOGGER.warn("Test 3: failed");
        else Config.LOGGER.info("Test 3: passed");
        CONFIG2.setOption("hello", new JsonPrimitive("world"));
    }
    
    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return CONFIG::createScreen;
    }
    
    @Override
    public void onInitializeClient() {
    
    }
}
