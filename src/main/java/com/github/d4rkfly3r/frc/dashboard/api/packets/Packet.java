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

import java.io.Serializable;

/**
 * Created by d4rkfly3r on 3/13/2016.
 * Project: FRC-Dashboard-API
 */
public abstract class Packet<T extends Packet, I> implements Serializable {

    protected I innerData;
    protected String senderName;
    protected Type dataType;
    protected long timeout = -1;

    @SuppressWarnings("unchecked")
    public T setDataType(Type dataType) {
        this.dataType = dataType;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTimeout(long timeout) {
        this.timeout = timeout;
        return (T) this;
    }

    public long getTimeout() {
        return timeout;
    }

    public Type getDataType() {
        return dataType;
    }

    public enum Type {
        MESSAGE, JSON, SERIALIZED, UNKNOWN
    }

    public void handle() {

    }
}
