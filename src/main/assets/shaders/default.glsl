#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;

uniform mat4 uProjectionMatrix;
uniform mat4 uViewMatrix;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexId;

void main() 
{
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;

    gl_Position = uProjectionMatrix * uViewMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8];

out vec4 color;

void main() 
{
    if (fTexId > 0) {
        int id = int(fTexId);
        // (1, 1, 1, 1) * (0.56, 5, 2, 5) = (0.56, 5, 2, 5)
        // if color is white it equals the texture
        color = fColor * texture(uTextures[id], fTexCoords);
    } else {
        color = fColor;
    }
}