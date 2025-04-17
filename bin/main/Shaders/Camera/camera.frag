#version 330 core

in vec2 vUV;

uniform sampler2D u_Texture;

out vec4 FragColor;

void main()
{
    FragColor = vec4(1.0, 1.0, 1.0 ,1.0); 

}
