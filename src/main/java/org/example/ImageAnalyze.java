package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageAnalyze {
    BufferedImage bufferedImage;
    int width, height;

    public void loadImage(String path) throws IOException {
        bufferedImage = ImageIO.read(new File(path));
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public void saveImage(String path) throws IOException {
        ImageIO.write(bufferedImage, "png",new File(path));
    }

    public void addBrigthnesForPixel(int x, int y, int brighteningLevel){
        int colorRead = bufferedImage.getRGB(x,y);
        Color color = new Color(colorRead);

        color = new Color(
                clamp(color.getRed()+brighteningLevel),
                clamp(color.getGreen()+brighteningLevel),
                clamp(color.getBlue()+brighteningLevel));
        bufferedImage.setRGB(x,y,color.getRGB());
    }

    public int clamp(int val){
        int start = 0;
        int end = 255;
        if(val > end){
            return end;
        }
        if(val < start){
            return start;
        }
        return val;
    }

    public void brightenImageAsync(int brighteningLevel){
        int n = Runtime.getRuntime().availableProcessors();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0;i<n;++i){
            int threadWorkEnd = (i+1)*height/n;
            int threadWorkStart = i*height/n;
            Thread thread = new Thread(() -> {
                int end = threadWorkEnd;
                if(threadWorkEnd+(height/n)>height){
                    end = height;
                }
                for(int x = 0;x<width;++x){
                    for(int y = threadWorkStart;y<end;++y){
                        addBrigthnesForPixel(x,y,brighteningLevel);
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void brightenImageSync(int brighteningLevel){
        for(int x = 0;x<width;++x){
            for(int y = 0;y<height;++y){
                addBrigthnesForPixel(x,y,brighteningLevel);
            }
        }
    }
}
