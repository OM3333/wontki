package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ImageAnalyze imageAnalyze = new ImageAnalyze();
        long start = System.currentTimeMillis();
        imageAnalyze.loadImage("a.png");
        imageAnalyze.brightenImageSync(-100);
        imageAnalyze.saveImage("b.png");
        long finish = System.currentTimeMillis();
        System.out.println(finish-start);
        /*
        start = System.currentTimeMillis();
        imageAnalyze.loadImage("a.png");
        imageAnalyze.brightenImageAsync(-100);
        imageAnalyze.saveImage("b.png");
        finish = System.currentTimeMillis();
        System.out.println(finish-start);*/
    }
}