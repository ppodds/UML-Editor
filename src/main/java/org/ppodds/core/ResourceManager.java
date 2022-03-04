package org.ppodds.core;

import org.ppodds.App;

import java.net.URL;

public class ResourceManager {
    public static URL getResource(String path) {
        return App.class.getResource(path);
    }

}
