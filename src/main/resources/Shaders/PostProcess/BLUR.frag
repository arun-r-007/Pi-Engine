#version 330 core
out vec4 FragColor;

in vec2 v_UV;

uniform sampler2D u_Texture0;

// Assume texel size in UV space (tweak this to control blur size)
const float offset = 1.0 / 10.0; // smaller = sharper, larger = blurrier

// Gaussian weights
const float kernel[9] = float[](
    0.0751136, 0.123841, 0.0751136,
    0.123841,  0.20418,  0.123841,
    0.0751136, 0.123841, 0.0751136
);

void main()
{
    vec2 offsets[9] = vec2[](
        vec2(-offset,  offset), // top-left
        vec2( 0.0f,    offset), // top-center
        vec2( offset,  offset), // top-right
        vec2(-offset,  0.0f),   // center-left
        vec2( 0.0f,    0.0f),   // center
        vec2( offset,  0.0f),   // center-right
        vec2(-offset, -offset), // bottom-left
        vec2( 0.0f,   -offset), // bottom-center
        vec2( offset, -offset)  // bottom-right
    );

    vec4 color = vec4(0.0);
    for (int i = 0; i < 9; i++)
    {
        vec4 sample = texture(u_Texture0, v_UV + offsets[i]);
        color += sample * kernel[i];
    }

    FragColor = color;
}
