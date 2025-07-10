#version 330
layout (location = 0) in vec3 a_Position;
layout (location = 1) in vec2 a_UV;

out vec2 vUV;

uniform mat4 u_ViewProj;
uniform mat4 u_ModelMatrix;


void main()
{
    vUV = a_UV;
    gl_Position =  u_ViewProj * u_ModelMatrix * vec4(a_Position, 1.0);
}
