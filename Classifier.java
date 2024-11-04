package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Pair;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Classifier {
    Context context;
    private static final String MODEL_NAME = "SKIN_DISEASE_DETAIL_50.tflite";
    Interpreter interpreter = null;

    int modelInputWidth, modelInputHeight, modelInputChanner;// 모델의 입력 크기 변수 선언
    int modelOutputClasses; // 모델 출력 클래스 수

    // 생성자 함수
    public Classifier(Context context){
        this.context = context;
    }

    // tflite 불러오는 함수
    private ByteBuffer loadModelFile(String modelName) throws IOException{
        AssetManager am = context.getAssets();
        AssetFileDescriptor afd = am.openFd(modelName);
        FileInputStream fis = new FileInputStream(afd.getFileDescriptor());
        FileChannel fc = fis.getChannel();
        long startoffset = afd.getStartOffset();
        long declaredLength = afd.getDeclaredLength();

        return fc.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength);
    }

    // 모델 초기화
    public void init() throws IOException {
        ByteBuffer model = loadModelFile(MODEL_NAME);
        model.order(ByteOrder.nativeOrder());
        interpreter = new Interpreter(model);

        initModelShape();
    }

    //모델의 입출력 크기 계산 함수
    private void initModelShape(){
        Tensor inputTensor = interpreter.getInputTensor(0);
        int[] inputShape = inputTensor.shape();
        modelInputChanner = inputShape[0];
        modelInputWidth = inputShape[1];
        modelInputHeight = inputShape[2];

        Tensor outputTensor = interpreter.getOutputTensor(0);
        int[] outputShape = outputTensor.shape();
        modelOutputClasses = outputShape[1];
    }


    //모델 추론
    public Pair<Integer, Float> classify(ByteBuffer inputImage) {
        float[][] result = new float[1][modelOutputClasses];
        interpreter.run(inputImage, result);
        return argmax(result[0]);
    }


    // 추론 결과 해석
    private Pair<Integer, Float> argmax(float[] array) {
        int argmax = 0;
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                argmax = i;
            }
        }
        return new Pair<>(argmax, max);
    }

    public String resultStr(Pair<Integer, Float> argmax){
        int index = argmax.first;
        float confidence = argmax.second;
        switch(index){
            case 0:
                return "습진";
            case 1:
                return "흑색종";
            case 2:
                return "아토피 피부염";
            case 3:
                return "기저 세포암";
            case 4:
                return "멜라닌 세포 모반";
            case 5:
                return "검버섯(지루 각화증)";
            case 6:
                return "편평 태선";
            case 7:
                return "검버섯(지루 각화증)";
            case 8:
                return "백선증";
            case 9:
                return "사마귀";
        }
        return null;
    }

}
