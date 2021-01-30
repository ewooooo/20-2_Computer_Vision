package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import static java.lang.Math.min;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6;
    private Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6;
    private Bitmap Gsbitmap1, Gsbitmap2, Gsbitmap3, Gsbitmap4, Gsbitmap5, Gsbitmap6;
    private String[] label = {"Thumb down", "palm horizontal", "L", "fist horizontal", "fist vertical", "thumb up", "index", "OK", "palm vertical", "C curve"};



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // 손 이미지 하나를 이용하기 위한 코드. 총 6개의 코드 필요(직접 작성할 것 5개 필요).
        // xml파일의 id와 맞춰주는 작업.
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        textView1 = (TextView) findViewById(R.id.textView1);
        // image 파일의 경로를 지정
        Uri imageuri1 = Uri.parse("android.resource://"+getPackageName()+"/raw/test1");
        imageView1.setImageURI(imageuri1);
        // 이미지를 비트맵 형태로 수정하고, GrayScale(흑백)로 수정
        bitmap1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
        Gsbitmap1 = toGrayscale(bitmap1);
        // 모델의 입력 크기에 맞춰주기 위해, 데이터의 형태를 조정
        Bitmap resized1 = Bitmap.createScaledBitmap(Gsbitmap1, 320, 120, false);
        ByteBuffer input1 = getByteBuffer(resized1);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        textView2 = (TextView) findViewById(R.id.textView2);
        // image 파일의 경로를 지정
        Uri imageuri2 = Uri.parse("android.resource://"+getPackageName()+"/raw/test2");
        imageView2.setImageURI(imageuri2);
        // 이미지를 비트맵 형태로 수정하고, GrayScale(흑백)로 수정
        bitmap2 = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
        Gsbitmap2 = toGrayscale(bitmap2);
        // 모델의 입력 크기에 맞춰주기 위해, 데이터의 형태를 조정
        Bitmap resized2 = Bitmap.createScaledBitmap(Gsbitmap2, 320, 120, false);
        ByteBuffer input2 = getByteBuffer(resized2);

        imageView3 = (ImageView) findViewById(R.id.imageView3);
        textView3 = (TextView) findViewById(R.id.textView3);
        // image 파일의 경로를 지정
        Uri imageuri3 = Uri.parse("android.resource://"+getPackageName()+"/raw/test3");
        imageView3.setImageURI(imageuri3);
        // 이미지를 비트맵 형태로 수정하고, GrayScale(흑백)로 수정
        bitmap3 = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
        Gsbitmap3 = toGrayscale(bitmap3);
        // 모델의 입력 크기에 맞춰주기 위해, 데이터의 형태를 조정
        Bitmap resized3 = Bitmap.createScaledBitmap(Gsbitmap3, 320, 120, false);
        ByteBuffer input3 = getByteBuffer(resized3);

        imageView4 = (ImageView) findViewById(R.id.imageView4);
        textView4 = (TextView) findViewById(R.id.textView4);
        // image 파일의 경로를 지정
        Uri imageuri4 = Uri.parse("android.resource://"+getPackageName()+"/raw/test4");
        imageView4.setImageURI(imageuri4);
        // 이미지를 비트맵 형태로 수정하고, GrayScale(흑백)로 수정
        bitmap4 = ((BitmapDrawable)imageView4.getDrawable()).getBitmap();
        Gsbitmap4 = toGrayscale(bitmap4);
        // 모델의 입력 크기에 맞춰주기 위해, 데이터의 형태를 조정
        Bitmap resized4 = Bitmap.createScaledBitmap(Gsbitmap4, 320, 120, false);
        ByteBuffer input4 = getByteBuffer(resized4);

        imageView5 = (ImageView) findViewById(R.id.imageView5);
        textView5 = (TextView) findViewById(R.id.textView5);
        // image 파일의 경로를 지정
        Uri imageuri5 = Uri.parse("android.resource://"+getPackageName()+"/raw/test5");
        imageView5.setImageURI(imageuri5);
        // 이미지를 비트맵 형태로 수정하고, GrayScale(흑백)로 수정
        bitmap5 = ((BitmapDrawable)imageView5.getDrawable()).getBitmap();
        Gsbitmap5 = toGrayscale(bitmap5);
        // 모델의 입력 크기에 맞춰주기 위해, 데이터의 형태를 조정
        Bitmap resized5 = Bitmap.createScaledBitmap(Gsbitmap5, 320, 120, false);
        ByteBuffer input5 = getByteBuffer(resized5);

        imageView6 = (ImageView) findViewById(R.id.imageView6);
        textView6 = (TextView) findViewById(R.id.textView6);
        // image 파일의 경로를 지정
        Uri imageuri6 = Uri.parse("android.resource://"+getPackageName()+"/raw/test6");
        imageView6.setImageURI(imageuri6);
        // 이미지를 비트맵 형태로 수정하고, GrayScale(흑백)로 수정
        bitmap6 = ((BitmapDrawable)imageView6.getDrawable()).getBitmap();
        Gsbitmap6 = toGrayscale(bitmap6);
        // 모델의 입력 크기에 맞춰주기 위해, 데이터의 형태를 조정
        Bitmap resized6 = Bitmap.createScaledBitmap(Gsbitmap6, 320, 120, false);
        ByteBuffer input6 = getByteBuffer(resized6);








        //get model
        Interpreter tflite = getTfliteInterpreter("model.tflite");

        //총 6개의 output
        float[][] output1 = new float[1][10];

        float[][] output2 = new float[1][10];
        float[][] output3 = new float[1][10];
        float[][] output4 = new float[1][10];
        float[][] output5 = new float[1][10];
        float[][] output6 = new float[1][10];


        // run the model
        // 모델의 input, output 형태를 맞춰주어야 함.
        tflite.run(input1, output1);
        tflite.run(input2, output2);
        tflite.run(input3, output3);
        tflite.run(input4, output4);
        tflite.run(input5, output5);
        tflite.run(input6, output6);

        // 각 예시 별로 가장 점수가 높은 손 동작의 index를 얻음(총 10종류)
        // "Thumb down", "palm horizontal", "L", "fist horizontal", "fist vertical", "thumb up", "index", "OK", "palm vertical", "C curve"
        int[] index = new int[6];

        index[0] = getMaxScoreIndex(output1);

        index[1] = getMaxScoreIndex(output2);
        index[2] = getMaxScoreIndex(output3);
        index[3] = getMaxScoreIndex(output4);
        index[4] = getMaxScoreIndex(output5);
        index[5] = getMaxScoreIndex(output6);



        textView1.setText(label[index[0]]);
        textView2.setText(label[index[1]]);
        textView3.setText(label[index[2]]);
        textView4.setText(label[index[3]]);
        textView5.setText(label[index[4]]);
        textView6.setText(label[index[5]]);
    }


    private int getMaxScoreIndex(float[][] output){
        int index = 0;
        for (int i = 0 ; i < output[0].length; i++ ) {
            Log.d("output score", String.valueOf(output[0][i]));
            if (output[0][index] < output[0][i])
                index = i;
        }

        Log.d("index", String.valueOf(index));
        return index;
    }


    // tensorflow-lite를 사용하기 위해 필수적으로 정의해주어야 하는 부분
    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(MainActivity.this, modelPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }






    private ByteBuffer getByteBuffer(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        ByteBuffer mImgData = ByteBuffer
                .allocateDirect(4 * height * width);
        mImgData.order(ByteOrder.nativeOrder());
        mImgData.asFloatBuffer();
        int[] pixels = new int[width*height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int pixel : pixels) {
            float value = (float) Color.red(pixel); //
            mImgData.putFloat(value);

        }


        return mImgData;
    }


    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

}