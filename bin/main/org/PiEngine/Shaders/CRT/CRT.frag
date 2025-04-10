#version 330 core

#define CURVATURE 5.4
#define BLUR 0.001
#define CA_AMT 1.015


uniform sampler2D u_Texture0;
uniform vec2 u_Resolution;

in vec2 v_UV; // make sure you're passing this from the vertex shader
out vec4 fragColor;

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

    //Scanlines and pixel lines
    if (mod(fragCoord.y, 2.0) < 0.7)
        col *= 0.9;
    else if (mod(fragCoord.x, 3.0) < 0.8)
        col *= 0.9;
    else
        col *= 1.2;

    fragColor = vec4(col, 1.0);
}
