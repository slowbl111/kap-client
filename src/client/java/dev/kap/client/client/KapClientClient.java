
package dev.kap.client.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KapClientClient implements ClientModInitializer {
    private static KeyBinding toggleHud;
    private static KeyBinding toggleBuilder;
    private static KeyBinding toggleMiner;
    private static KeyBinding toggleMiner3x3;

    private static boolean hudEnabled = true;
    private static boolean builderEnabled = false;
    private static boolean minerEnabled = false;
    private static boolean miner3x3Enabled = false;

    @Override
    public void onInitializeClient() {
        toggleHud = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kap_client.toggle_hud",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.kap_client"
        ));

        toggleBuilder = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kap_client.toggle_builder",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.kap_client"
        ));

        toggleMiner = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kap_client.toggle_miner",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "category.kap_client"
        ));

        toggleMiner3x3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kap_client.toggle_miner_3x3",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "category.kap_client"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleHud.wasPressed()) {
                hudEnabled = !hudEnabled;
                notify(client, "HUD", hudEnabled);
            }

            while (toggleBuilder.wasPressed()) {
                builderEnabled = !builderEnabled;
                notify(client, "Builder prototype", builderEnabled);
            }

            while (toggleMiner.wasPressed()) {
                minerEnabled = !minerEnabled;
                notify(client, "Miner prototype", minerEnabled);
            }

            while (toggleMiner3x3.wasPressed()) {
                miner3x3Enabled = !miner3x3Enabled;
                notify(client, "3x3 Miner prototype", miner3x3Enabled);
            }
        });
    }

    private static void notify(
        net.minecraft.client.MinecraftClient client,
        String feature,
        boolean enabled
    ) {
        if (client.player != null) {
            client.player.sendMessage(
                Text.literal("Kap Client: " + feature + " " +
                    (enabled ? "enabled" : "disabled")),
                true
            );
        }
    }
}
