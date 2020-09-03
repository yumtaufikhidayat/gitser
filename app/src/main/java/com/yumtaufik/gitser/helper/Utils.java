package com.yumtaufik.gitser.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static byte[] getBytes(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getImage(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
