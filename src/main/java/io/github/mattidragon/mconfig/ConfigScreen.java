package io.github.mattidragon.mconfig;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

class ConfigScreen extends Screen {
    private final Config config;
    private final Screen parent;
    
    ConfigScreen(Config config, Screen parent) {
        super(new TranslatableText("mconfig." + config.id.getNamespace() + ".title"));
        this.config = config;
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, new TranslatableText("mconfig.screen.load"), (button) -> {
            config.load();
        }));
        addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, new TranslatableText("mconfig.screen.done"), (button) -> {
            onClose();
        }));
    }
    
    @Override
    public void onClose() {
        client.setScreen(parent);
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xffffff);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
