#version 330 core

in vec2 vUV;

uniform sampler2D u_Texture;
uniform float u_Time;

out vec4 FragColor;

void main()
{
    float r = 0.5 + 0.5 * sin(u_Time + 0.0);
    float g = 0.5 + 0.5 * sin(u_Time + 2.0);
    float b = 0.5 + 0.5 * sin(u_Time + 4.0);

    vec3 rainbowColor = vec3(r, g, b);
    FragColor = vec4(rainbowColor, 1.0);
}
