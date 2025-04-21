#version 330 core
out vec4 FragColor;

uniform sampler2D u_Texture0;
uniform vec2 u_Resolution;
in vec2 v_UV;

// Gaussian weights for a 3x3 kernel
const float kernel[9] = float[](0.0751136, 0.123841, 0.0751136,
                                0.123841, 0.20418,  0.123841,
                                0.0751136, 0.123841, 0.0751136);

void main()
{
    // Offset for the neighboring pixels (pixel size)
    vec2 texOffset = 1.0 / u_Resolution; 

    // Applying the 3x3 Gaussian kernel
    vec4 color = texture(u_Texture0, v_UV) * kernel[4]; // Center pixel

    // Sample surrounding pixels and apply weights
    int index = 0;
    for (int x = -1; x <= 1; x++)
    {
        for (int y = -1; y <= 1; y++)
        {
            if (x == 0 && y == 0) continue; // Skip the center pixel, it's already sampled

            vec2 offset = vec2(x, y) * texOffset;
            color += texture(u_Texture0, v_UV + offset) * kernel[index++];
        }
    }

    FragColor = color;
}
