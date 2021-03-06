package tfar.dankstorage.client.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.network.chat.TextComponent;
import tfar.dankstorage.utils.Mode;

public class TripleToggleButton extends SmallButton {

    protected Mode mode;

    public TripleToggleButton(int x, int y, int widthIn, int heightIn, OnPress callback, Mode mode) {
        super(x, y, widthIn, heightIn, new TextComponent(""), callback);
        this.mode = mode;
    }

    @Override
    public void tint() {
        RenderSystem.color4f(mode.r(), mode.g(), mode.b(), 1);
    }

    public void toggle() {
        mode = mode.cycle();
    }
}