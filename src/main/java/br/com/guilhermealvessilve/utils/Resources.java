package br.com.guilhermealvessilve.utils;

import java.io.InputStream;

public class Resources {

    private Resources() {
        throw new IllegalArgumentException("No Resources!");
    }

    public static InputStream fromResources(final String path) {
        return Resources.class.getResourceAsStream(path);
    }
}
