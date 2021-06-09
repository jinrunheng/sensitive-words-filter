package com.duby.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileReader {

    public static BufferedReader readFile(String path) {
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)), StandardCharsets.UTF_8));
    }
}
