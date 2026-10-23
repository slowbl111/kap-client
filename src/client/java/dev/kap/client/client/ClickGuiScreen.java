
package dev.kap.client.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClickGuiScreen extends Screen {
    private static final int BLUE = 0xFF258BFF;
    private static final int PANEL = 0xF0101726;
    private static final int ROW = 0xFF1B2638;

    public ClickGuiScreen() {
        super(Component.literal("Kap Client"));
    }

    @Override
    protected void init() {
        int panelWidth = 360;
        int panelHeight = 270;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;

        int buttonX = left + 145;
        int buttonWidth = 195;
        int buttonY = top + 48;

        addRenderableWidget(moduleButton(
            "HUD", "hud", buttonX, buttonY, buttonWidth));
        addRenderableWidget(moduleButton(
            "Keystrokes", "keystrokes", buttonX, buttonY + 30, buttonWidth));
        addRenderableWidget(moduleButton(
            "FPS Counter", "fps", buttonX, buttonY + 60, buttonWidth));
        addRenderableWidget(moduleButton(
            "Coordinates", "coordinates", buttonX, buttonY + 90, buttonWidth));
        addRenderableWidget(moduleButton(
            "Zoom", "zoom", buttonX, buttonY + 120, buttonWidth));
        addRenderableWidget(moduleButton(
            "Armor Status", "armor", buttonX, buttonY + 150, buttonWidth));

        addRenderableWidget(Button.builder(
            Component.literal("Close Menu"),
            button -> Minecraft.getInstance().setScreen(null)
        ).bounds(buttonX, buttonY + 190, buttonWidth, 22).build());
    }

    private Button moduleButton(
        String label, String id, int x, int y, int width
    ) {
        return Button.builder(
            buttonText(label, id),
            button -> {
                KapClientClient.toggleModule(id);
                button.setMessage(buttonText(label, id));
            }
        ).bounds(x, y, width, 24).build();
    }

    private Component buttonText(String label, String id) {
        return Component.literal(
            label + "  [" +
            (KapClientClient.isEnabled(id) ? "ON" : "OFF") + "]"
        );
    }

    @Override
    public void render(
        GuiGraphics graphics, int mouseX, int mouseY, float partialTick
    ) {
        int left = (this.width - 360) / 2;
        int top = (this.height - 270) / 2;

        graphics.fill(0, 0, this.width, this.height, 0x99000000);
        graphics.fill(left, top, left + 360, top + 270, PANEL);
        graphics.fill(left, top, left + 360, top + 3, BLUE);
        graphics.fill(left + 12, top + 40, left + 125, top + 258, ROW);

        graphics.drawString(
            this.font, "KAP CLIENT", left + 16, top + 14, 0xFFFFFFFF
        );
        graphics.drawString(
            this.font, "1.21.11  |  CLIENT MENU",
            left + 145, top + 27, 0xFF80BFFF
        );
        graphics.drawString(
            this.font, "MODULES", left + 22, top + 52, BLUE
        );
        graphics.drawString(
            this.font, "Blue Edition",
            left + 22, top + 76, 0xFFFFFFFF
        );
        graphics.drawString(
            this.font, "Right Shift",
            left + 22, top + 96, 0xFF9CAFC8
        );

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
