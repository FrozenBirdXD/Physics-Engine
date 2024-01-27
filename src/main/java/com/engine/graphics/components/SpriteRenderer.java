package com.engine.graphics.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.renderer.Texture;
import com.engine.graphics.utils.Transform;

import imgui.ImGui;

public class SpriteRenderer extends Component {
    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    // transient variables are not serialized
    private transient boolean isDirty = true;
    private transient Transform lastTransform;

    @Override
    public void imgui() {
        float[] imColor = { color.x, color.y, color.z, color.w };
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            setColor(new Vector4f(imColor[0], imColor[1], imColor[2], imColor[3]));
            isDirty = true;
        }
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.getTransform())) {
            this.gameObject.getTransform().copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    // To provide access to the gameobject
    public void start() {
        this.lastTransform = gameObject.getTransform().copy();
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

    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
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
