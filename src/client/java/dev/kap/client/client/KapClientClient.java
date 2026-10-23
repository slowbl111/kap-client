
package dev.kap.client.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class KapClientClient implements ClientModInitializer {
    private static KeyMapping toggleHud;
    private static KeyMapping toggleBuilder;
    private static KeyMapping toggleMiner;
    private static KeyMapping toggleMiner3x3;

    @Override
    public void onInitializeClient() {
        toggleHud = registerKey("toggle_hud", GLFW.GLFW_KEY_H);
        toggleBuilder = registerKey("toggle_builder", GLFW.GLFW_KEY_B);
        toggleMiner = registerKey("toggle_miner", GLFW.GLFW_KEY_N);
        toggleMiner3x3 = registerKey("toggle_miner_3x3", GLFW.GLFW_KEY_M);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleHud.consumeClick()) {
                notify(client, "HUD");
            }
            while (toggleBuilder.consumeClick()) {
                notify(client, "Builder prototype");
            }
            while (toggleMiner.consumeClick()) {
                notify(client, "Miner prototype");
            }
            while (toggleMiner3x3.consumeClick()) {
                notify(client, "3x3 Miner prototype");
            }
        });
    }

    private static KeyMapping registerKey(String name, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.kap_client." + name,
            key,
            KeyMapping.Category.MISC
        ));
    }

    private static void notify(Minecraft client, String feature) {
        if (client.player != null) {
            client.player.displayClientMessage(
                Component.literal("Kap Client: " + feature + " key pressed"),
                true
            );
        }
    }
}
