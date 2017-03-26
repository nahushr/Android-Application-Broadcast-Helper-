package com.example.nahushraichura.broadcasthelper;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

class QRcodegenerator extends AsyncTask<Void, Void, Bitmap> {
    static public int MARGIN_AUTOMATIC = -1;
    static public int MARGIN_NONE = 0;
    final int colorQR = Color.BLACK;
    final int colorBackQR = Color.WHITE;
    final int width = 400;
    final int height = 400;
    public String mEncodeString=UserQr.mEncodeString;
    Bitmap bitmapQR;

    protected Bitmap doInBackground(Void... params) {

        try {
             bitmapQR = generateBitmap(mEncodeString, width, height,
                    MARGIN_AUTOMATIC, colorQR, colorBackQR);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bitmapQR;
    }
    static public Bitmap generateBitmap(@NonNull String contentsToEncode,
                                        int imageWidth, int imageHeight,
                                        int marginSize, int color, int colorBack)
            throws WriterException, IllegalStateException {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Should not be invoked from the UI thread");
        }

        Map<EncodeHintType, Object> hints = null;
        if (marginSize != MARGIN_AUTOMATIC) {
            hints = new EnumMap<>(EncodeHintType.class);
            // We want to generate with a custom margin size
            hints.put(EncodeHintType.MARGIN, marginSize);
        }

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(contentsToEncode, BarcodeFormat.QR_CODE, imageWidth, imageHeight, hints);

        final int width = result.getWidth();
        final int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? color : colorBack;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
