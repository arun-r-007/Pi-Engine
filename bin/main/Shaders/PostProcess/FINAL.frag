#version 330 core
out vec4 FragColor;

uniform sampler2D u_Texture0;
uniform vec2 u_Resolution;

in vec2 v_UV;

void main()
{
    FragColor = texture(u_Texture0, v_UV);
}
