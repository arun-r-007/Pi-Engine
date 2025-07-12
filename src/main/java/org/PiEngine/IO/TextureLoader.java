package org.PiEngine.IO;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.PiEngine.Engine.Console;
import org.PiEngine.Render.Texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureLoader
{
    public static Texture loadTexture(String path, int minFilter, int magFilter)
    {
        // Let STB flip the image for you (so origin ends up bottom-left)
        STBImage.stbi_set_flip_vertically_on_load(true);

        try (MemoryStack stack = MemoryStack.stackPush())
        {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer ch = stack.mallocInt(1);

            // Ask STB to give us RGBA (4 channels)
            ByteBuffer image = STBImage.stbi_load(path, w, h, ch, 4);
            if (image == null)
            {
                Console.error("Failed to load image: " + STBImage.stbi_failure_reason());
                return null;
            }

            int width  = w.get(0);
            int height = h.get(0);

            // Extract each pixel and pack into an ARGB int[] for your Texture class
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    int i = (y * width + x) * 4;
                    int r = image.get(i)     & 0xFF;
                    int g = image.get(i + 1) & 0xFF;
                    int b = image.get(i + 2) & 0xFF;
                    int a = image.get(i + 3) & 0xFF;

                    pixels[y * width + x] = (a << 24) | (r << 16) | (g << 8) | b;
                }
            }

            STBImage.stbi_image_free(image);
            return new Texture(pixels, width, height, minFilter, magFilter);
        }
    }
}
