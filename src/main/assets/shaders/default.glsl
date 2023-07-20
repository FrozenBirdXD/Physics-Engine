#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;

uniform mat4 uProjectionMatrix;
uniform mat4 uViewMatrix;

out vec4 fColor;
out vec2 fTexCoords;

void main() 
{
    fColor = aColor;
    fTexCoords = aTexCoords;
    gl_Position = uProjectionMatrix * uViewMatrix * vec4(aPos.x, aPos.y, aPos.z, 1.0f);
}

#type fragment
#version 330 core

uniform float uTime;
uniform sampler2D TEX_SAMPLER;

in vec4 fColor;
in vec2 fTexCoords;

out vec4 color;

void main() 
{
    color = texture(TEX_SAMPLER, fTexCoords);
}