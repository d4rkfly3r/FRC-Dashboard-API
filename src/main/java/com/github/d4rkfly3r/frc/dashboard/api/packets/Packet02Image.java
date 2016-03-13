/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Joshua Freedman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.d4rkfly3r.frc.dashboard.api.packets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by d4rkfly3r on 3/13/2016.
 * Project: FRC-Dashboard-API
 */
public class Packet02Image extends KeyedPacket<Packet02Image, byte[]> {

    int width, height;

    public Packet02Image(@Nonnull int width, @Nonnull int height, @Nonnull byte[] imageByteArray) {
        this(null, width, height, imageByteArray);
    }

    public Packet02Image(@Nonnull int width, @Nonnull int height, @Nonnull BufferedImage bi) throws IOException {
        this(null, width, height, bi);
    }

    public Packet02Image(@Nullable String key, @Nonnull int width, @Nonnull int height, @Nonnull BufferedImage image) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        this.key = key;
        this.width = width;
        this.height = height;
        this.innerData = bos.toByteArray();
    }

    public Packet02Image(@Nullable String key, @Nonnull int width, @Nonnull int height, @Nonnull byte[] imageByteArray) {
        this.key = key;
        this.width = width;
        this.height = height;
        this.innerData = imageByteArray;
    }

    @Nonnull
    public byte[] getImageByteArray() {
        return this.innerData;
    }

    @Nonnull
    public int getWidth() {
        return width;
    }

    @Nonnull
    public int getHeight() {
        return height;
    }

    @Nullable
    public BufferedImage getImage() {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(getImageByteArray()));
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
