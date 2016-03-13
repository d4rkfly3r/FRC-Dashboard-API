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

package com.github.d4rkfly3r.frc.dashboard.api.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by d4rkfly3r on 3/13/2016.
 * Project: FRC-Dashboard-API
 */
@SuppressWarnings({"PointlessBooleanExpression", "ConstantConditions", "unused"})
public class Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final ArrayList<String> log;
    private Calendar time;
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat logSDF = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss");
    private final String prefix;
    private final String suffix;
    private final String cleanupCode = ANSI_RESET;

    private static final String ERROR = ANSI_RED + "NS ERROR: " + ANSI_RESET;
    private static final String ERROR_SUFFIX = "!!";
    private static final String CLEAR_LOG = ANSI_CYAN + "Log has been cleared!" + ANSI_RESET;

    private static final int logLevel = 0; // 0 -> most details 10 -> least details

    public Logger() {
        this(new ArrayList<>(), ANSI_PURPLE + "[NS-FRC] " + ANSI_RESET, "");
    }

    public Logger(@Nonnull Class clazz) {
        this(ANSI_PURPLE + "[" + clazz.getSimpleName() + "] " + ANSI_RESET);
    }

    public Logger(@Nonnull ArrayList<String> pastLog) {

        this(pastLog, ANSI_PURPLE + "[NS-FRC] " + ANSI_RESET, "");
    }

    public Logger(@Nonnull String prefix) {
        this(new ArrayList<>(), prefix, "");
    }

    public Logger(@Nonnull String prefix, @Nonnull String suffix) {
        this(new ArrayList<>(), prefix, suffix);
    }

    public Logger(@Nonnull ArrayList<String> pastLog, @Nonnull String prefix) {
        this(pastLog, prefix, "");
    }

    public Logger(@Nonnull ArrayList<String> pastLog, @Nonnull String prefix, @Nonnull String suffix) {
        this.time = Calendar.getInstance();
        this.log = pastLog;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Nonnull
    public String getPrefix() {
        return prefix;
    }

    @Nonnull
    public String getSuffix() {
        return suffix;
    }

    public void debug(@Nullable Object message) {
        this.time = Calendar.getInstance();
        if (message != null && logLevel < 5) {
            String msg = "[" + sdf.format(this.time.getTime()) + "]" + ANSI_YELLOW + " DEBUG  " + prefix + message.toString() + suffix + cleanupCode;
            log.add(msg.replaceAll("(\\[[0-99]+m)", "") + "\n");
            System.out.println(msg);
        }
    }

    public void debugQuiet(@Nullable Object message) {
        this.time = Calendar.getInstance();
        if (message != null && logLevel < 5) {
            String msg = message.toString() + cleanupCode;
            log.add(msg.replaceAll("(\\[[0-99]+m)", "") + "\n");
            System.out.println(msg);
        }
    }

    public void debugError(@Nullable Object message) {
        this.time = Calendar.getInstance();
        if (message != null && logLevel < 5) {
            String msg = "[" + sdf.format(this.time.getTime()) + "]" + ANSI_YELLOW + " DEBUG  " + ERROR + message.toString() + ERROR_SUFFIX + cleanupCode;
            log.add(msg.replaceAll("(\\[[0-99]+m)", "") + "\n");
            System.out.println(ANSI_RED + msg);
        }
    }

    public void log(@Nullable Object message) {
        this.time = Calendar.getInstance();
        if (message != null) {
            String msg = "[" + sdf.format(this.time.getTime()) + "]" + ANSI_BLUE + " NORMAL " + prefix + message.toString() + suffix + cleanupCode;
            log.add(msg.replaceAll("(\\[[0-99]+m)", "") + "\n");
            System.out.println(msg);
        }
    }

    public void logQuiet(@Nullable Object message) {
        this.time = Calendar.getInstance();
        if (message != null) {
            String msg = message.toString() + cleanupCode;
            log.add(msg.replaceAll("(\\[[0-99]+m)", "") + "\n");
            System.out.println(msg);
        }
    }

    public void logError(@Nullable Objects message) {
        this.time = Calendar.getInstance();
        if (message != null) {
            String msg = "[" + sdf.format(this.time.getTime()) + "]" + ANSI_RED + " ERROR " + message.toString() + ERROR_SUFFIX + cleanupCode;
            log.add(msg.replaceAll("(\\[[0-99]+m)", "") + "\n");
            System.out.println(ANSI_RED + msg);
        }
    }

    public void logClear() {
        log.add(CLEAR_LOG.replaceAll("(\\[[0-99]+m)", "") + "\n");
        for (int i = 1; i < 50; i++)
            System.out.print("\n");
        System.out.println(CLEAR_LOG);
    }

    public void logClear(@Nonnull int lines) {
        log.add(CLEAR_LOG.replaceAll("(\\[[0-99]+m)", "") + "\n");
        for (int i = 1; i < lines; i++)
            System.out.print("\n");
        System.out.println(CLEAR_LOG);
    }

    public boolean saveLog() {
        this.time = Calendar.getInstance();
        File rootLogDir = new File("logs");

        if (!rootLogDir.exists())
            //noinspection ResultOfMethodCallIgnored
            rootLogDir.mkdirs();

        File logFile = new File(rootLogDir, logSDF.format(this.time.getTime()) + ".nslog");
        try (FileOutputStream fos = new FileOutputStream(logFile); OutputStreamWriter osw = new OutputStreamWriter(fos)) {

            log.forEach(s -> {
                try {
                    osw.write(s);
                    osw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Nonnull
    public String getLog() {
        return log.toString();
    }
}
