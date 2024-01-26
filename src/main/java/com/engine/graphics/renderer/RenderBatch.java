package com.engine.graphics.renderer;

import static org.lwjgl.opengl.GL33.*;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.Window;
import com.engine.graphics.components.SpriteRenderer;
import com.engine.graphics.utils.AssetPool;

public class RenderBatch implements Comparable<RenderBatch> {
    // Vertex
    // =====
    // Pos ----------- Color ----------------------- TextureCoords - TextureID
    // float, float, - float, float, float, float, - float, float, - float

    private Shaders shader;
    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private List<Texture> textures;
    private int[] texSlots = { 0, 1, 2, 3, 4, 5, 6, 7 };
    private int zIndex;

    private int vaoId, vboId;
    private int maxBatchSize;

    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TEX_COORDS_SIZE = 2;
    private final int TEX_ID_SIZE = 1;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 9;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    // maxBatchSize is the max amount of sprites
    public RenderBatch(int maxBatchSize, int zIndex) {
        this.zIndex = zIndex;
        this.shader = AssetPool.getShader("src/main/assets/shaders/default.glsl");
        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        // 4 vertices per quad
        // all the floats for the vertices
        // each sprite has 4 vertices, each vertex has 9 floats (VERTEX_SIZE)
        this.vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
        this.textures = new ArrayList<>();
        this.numSprites = 0;
        this.hasRoom = true;
    }

    public void start() {
        // Generate and bind vertex array object
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Allocate space vertices
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Enable Buffer Attribute pointers; how vertex is laid out
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);
    }

    public void addSprite(SpriteRenderer spriteRenderer) {
        // Add renderobject to next space in array
        int index = numSprites;
        sprites[index] = spriteRenderer;
        numSprites++;

        // if sprite has texture
        if (spriteRenderer.getTexture() != null) {
            // if texture is not already in list (because other sprite already added it)
            if (!textures.contains(spriteRenderer.getTexture())) {
                textures.add(spriteRenderer.getTexture());
            }
        }

        // Add properties to local vertices array
        loadVertexProperties(index);

        if (numSprites >= maxBatchSize) {
            hasRoom = false;
        }
    }

    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }
        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;
        // 3, 2, 0, 0, 2, 1 7, 6, 4, 4, 6, 5
        // Create triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Create triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    // When we have data in vertices
    public void render() {
        boolean rebufferData = false;
        for (int i = 0; i < numSprites; i++) {
            SpriteRenderer renSpr = sprites[i];
            if (renSpr.isDirty()) {
                loadVertexProperties(i);
                renSpr.setClean();
                rebufferData = true;
            }
        }

        if (rebufferData) {
            // Rebuffer all data if needed
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            // Buffer data into the vbo given above
            // Upload all the vertices starting from 0
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        // Use shader
        shader.use();
        shader.uploadMat4f("uProjectionMatrix", Window.get().getScene().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uViewMatrix", Window.get().getScene().getCamera().getViewMatrix());

        // Bind every texture
        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }

        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, numSprites * 9, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind every texture
        for (int i = 0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }

        shader.detach();
    }

    private void loadVertexProperties(int index) {
        // Gets current sprite
        SpriteRenderer sprite = sprites[index];

        // Find offset in array (4 vertices per sprite)
        int offset = index * 4 * VERTEX_SIZE;
        // float, float, float, float, float, float, flaot...
        Vector4f color = sprite.getColor();
        Vector2f[] texCoords = sprite.getTexCoords();

        int texId = 0;
        // [tex, tex, tex, tex, ...]
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i).equals(sprite.getTexture())) {
                    texId = i + 1;
                    break;
                }
            }
        }

        // Add vertices with correct properties
        float xAdd = 1.0f;
        float yAdd = 1.0f;

        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;
            } else if (i == 3) {
                yAdd = 1.0f;
            }

            // Load position
            vertices[offset] = sprite.gameObject.getTransform().getPositionX()
                    + (xAdd * sprite.gameObject.getTransform().getScaleX());
            vertices[offset + 1] = sprite.gameObject.getTransform().getPositionY()
                    + (yAdd * sprite.gameObject.getTransform().getScaleY());

            // Load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            // Load texture coords
            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;

            // Load texture id
            vertices[offset + 8] = texId;

            offset += VERTEX_SIZE;
        }
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    public boolean hasTextureRoom() {
        return this.textures.size() < 8;
    }

    public boolean hasTexture(Texture texture) {
        return this.textures.contains(texture);
    }

    public int getZIndex() {
        return this.zIndex;
    }

    @Override
    public int compareTo(RenderBatch o) {
        // if left is less than right -> -1
        // if same -> 0
        // if left larger than right -> 1
        return Integer.compare(this.zIndex, o.getZIndex());
    }
}
