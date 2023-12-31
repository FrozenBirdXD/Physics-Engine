package com.engine.graphics.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.Component;
import com.engine.graphics.Transform;
import com.engine.graphics.renderer.Texture;
import com.engine.graphics.utils.AssetPool;

import imgui.ImGui;

public class SpriteRenderer extends Component {
    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();
    // private int num = 0;
    // private Spritesheet sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");

    // static {
    //     AssetPool.addSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png",
    //             new Spritesheet(AssetPool.getTexture(
    //                     "src/main/assets/spritesheets/spritesheet_16x16.png"), 16, 16, 352,
    //                     0));
    // }

    // transient variables are not serialized
    private transient boolean isDirty = true;
    private transient Transform lastTransform;

    @Override
    public void imgui() {
        float[] imColor = { color.x, color.y, color.z, color.w };
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            isDirty = true;
        }
        // int[] textureId = { num };
        // if (ImGui.sliderInt("Texture Id: ", textureId, 0, 20)) {
        //     this.num = textureId[0];
        //     setSprite(sprites.getSprite(num));
        // }
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    // To provide access to the gameobject
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    public Vector4f getColor() {
        return this.color;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color.set(color);
            this.isDirty = true;
        }
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
