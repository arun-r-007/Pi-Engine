#version 330 core

#define CURVATURE 5.4
#define BLUR 0.001
#define CA_AMT 1.005

uniform sampler2D u_Texture0;
uniform sampler2D u_Texture1;

uniform vec2 u_Resolution;
uniform float u_Time;

in vec2 v_UV;
out vec4 fragColor;

// Simple hash-based noise function
float rand(vec2 co)
{
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main()
{
    vec2 fragCoord = gl_FragCoord.xy;
    vec2 uv = v_UV;

    // CRT curvature distortion
    vec2 crtUV = uv * 2.0 - 1.0;
    vec2 offset = crtUV.yx / CURVATURE;
    crtUV += crtUV * offset * offset;
    crtUV = crtUV * 0.5 + 0.5;

    // Vignette edge fading
    vec2 edge = smoothstep(0.0, BLUR, crtUV) * (1.0 - smoothstep(1.0 - BLUR, 1.0, crtUV));

    // Chromatic aberration
    vec3 col;
    col.r = texture(u_Texture0, (crtUV - 0.5) * CA_AMT + 0.5).r;
    col.g = texture(u_Texture0, crtUV).g;
    col.b = texture(u_Texture0, (crtUV - 0.5) / CA_AMT + 0.5).b;
    col *= edge.x * edge.y;

    // Film grain (animated noise)
    float grainStrength = 0.4;
    float grain = rand(fragCoord.xy * 0.5 + u_Time * 10.0);
    col += grainStrength * (grain - 0.5); // center it around 0

    fragColor = vec4(col, 1.0) + vec4(texture(u_Texture1, v_UV).x * 5.0, 0.0, 0.0, 1.0);
}
