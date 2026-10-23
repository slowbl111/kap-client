
package dev.kap.client.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class KapClientClient implements ClientModInitializer {
    private static final String MOD_ID = "kap_client";

    private static boolean hud = true;
    private static boolean keystrokes = true;
    private static boolean fps = true;
    private static boolean coordinates = true;
    private static boolean zoom = false;
    private static boolean armor = true;

    private static KeyMapping openMenu;
    private static Integer previousFov = null;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(MOD_ID, "main")
        );

        openMenu = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.kap_client.open_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.consumeClick()) {
                if (client.screen instanceof ClickGuiScreen) {
                    client.setScreen(null);
                } else if (client.screen == null) {
                    client.setScreen(new ClickGuiScreen());
                }
            }

            if (zoom) {
                if (previousFov == null) {
                    previousFov = client.options.fov().get();
                }
                client.options.fov().set(30);
            } else if (previousFov != null) {
                client.options.fov().set(previousFov);
                previousFov = null;
            }
        });

        HudElementRegistry.addLast(
            Identifier.fromNamespaceAndPath(MOD_ID, "client_hud"),
            (graphics, deltaTracker) -> renderHud(
                Minecraft.getInstance(), graphics
            )
        );
    }

    public static boolean isEnabled(String id) {
        return switch (id) {
            case "hud" -> hud;
            case "keystrokes" -> keystrokes;
            case "fps" -> fps;
            case "coordinates" -> coordinates;
            case "zoom" -> zoom;
            case "armor" -> armor;
            default -> false;
        };
    }

    public static void toggleModule(String id) {
        switch (id) {
            case "hud" -> hud = !hud;
            case "keystrokes" -> keystrokes = !keystrokes;
            case "fps" -> fps = !fps;
            case "coordinates" -> coordinates = !coordinates;
            case "zoom" -> zoom = !zoom;
            case "armor" -> armor = !armor;
        }
    }

    private static void renderHud(
        Minecraft client, GuiGraphics graphics
    ) {
        if (!hud || client.player == null || client.options.hideGui) {
            return;
        }

        int x = 8;
        int y = 8;
        int blue = 0xFF55AAFF;
        int white = 0xFFFFFFFF;

        graphics.fill(x - 4, y - 4, x + 155, y + 15, 0xAA101B2B);
        graphics.fill(x - 4, y - 4, x - 1, y + 15, blue);
        graphics.drawString(client.font, "KAP CLIENT", x + 3, y, blue);
        y += 23;

        if (fps) {
            drawLine(graphics, client, "FPS: " + client.getFps(),
                x, y, white);
            y += 12;
        }

        if (coordinates) {
            var player = client.player;
            drawLine(graphics, client,
                String.format("XYZ: %.1f / %.1f / %.1f",
                    player.getX(), player.getY(), player.getZ()),
                x, y, white);
            y += 12;
        }

        if (keystrokes) {
            var options = client.options;
            String keys =
                (options.keyUp.isDown() ? "[W]" : " W ") + " " +
                (options.keyLeft.isDown() ? "[A]" : " A ") + " " +
                (options.keyDown.isDown() ? "[S]" : " S ") + " " +
                (options.keyRight.isDown() ? "[D]" : " D ");
            graphics.fill(x - 3, y - 3, x + 115, y + 12, 0xAA101B2B);
            drawLine(graphics, client, keys, x, y, blue);
            y += 18;
        }

        if (armor) {
int equipped = 0;

for (var slot : new net.minecraft.world.entity.EquipmentSlot[] {
    net.minecraft.world.entity.EquipmentSlot.HEAD,
    net.minecraft.world.entity.EquipmentSlot.CHEST,
    net.minecraft.world.entity.EquipmentSlot.LEGS,
    net.minecraft.world.entity.EquipmentSlot.FEET
}) {
    if (!client.player.getItemBySlot(slot).isEmpty()) {
        equipped++;
    }
}

drawLine(graphics, client, "Armor pieces: " + equipped + "/4",
    x, y, white);
