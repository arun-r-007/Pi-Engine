#version 330 core
out vec4 FragColor;

in vec2 v_UV;

uniform sampler2D u_Texture0;

// Gaussian blur parameters
const float offset = 1.0 / 10.0;
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

    vec3 accumColor = vec3(0.0);
    float accumAlpha = 0.0;

    for (int i = 0; i < 9; ++i)
    {
        vec4 sample = texture(u_Texture0, v_UV + offsets[i]);
        accumColor += sample.rgb * sample.a * kernel[i]; // Premultiply
        accumAlpha += sample.a * kernel[i];
    }

    // Avoid division by zero
    if (accumAlpha > 0.0)
        accumColor /= accumAlpha;

    FragColor = vec4(accumColor, accumAlpha);
}
