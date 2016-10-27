package net.chiragaggarwal.contactsapp.common;

import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetUtils {
    public static String readAsset(String file) throws RuntimeException {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream json = InstrumentationRegistry.getContext().getAssets().open(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            in.close();
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
