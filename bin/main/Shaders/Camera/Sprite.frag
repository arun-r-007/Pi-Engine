#version 330 core

in vec2 vUV;

uniform sampler2D u_Texture;
uniform float u_Time;

out vec4 FragColor;

void main()
{
    vec4 texColor = texture(u_Texture, vUV);
    FragColor = texColor;
}
