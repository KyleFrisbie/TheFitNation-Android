package com.fitnation.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utils for handling files
 */
public class FileUtils {

    private FileUtils() {
    } //private because util class

    public static String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toString();
    }

    /**
     * Converts a raw json file into a json string
     *
     * @return - the json file turned into a string
     */
    public static String convertJsonFileToString(Context context, int fileResource) {
        String json = null;
        InputStream input = context.getResources().openRawResource(fileResource);
        json = FileUtils.readTextFile(input);
        return json;
    }
}
