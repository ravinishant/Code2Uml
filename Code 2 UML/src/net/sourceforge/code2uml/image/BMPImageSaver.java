/*
 * BMPImageSaver.java
 *
 * Created on August 26, 2007, 2:25 PM
 *
 * Copyright 2007 Mateusz Wenus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sourceforge.code2uml.image;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import net.sourceforge.code2uml.util.ProgressData;

/**
 * This class is responsible for saving contents of a java.awt.Component (an
 * image that can be created by calling that Component's paint method) as a 
 * 24 bit bitmap.
 *
 * @author Mateusz Wenus
 */
public class BMPImageSaver extends Observable implements ImageSaver {
    
    private static final int imagePixels = 10000;
    
    /** 
     * Creates a new instance of BMPImageSaver. 
     */
    public BMPImageSaver() {
    }

    /**
     * Calculates size of 24 bit bitmap file needed to save contents of 
     * Component <code>comp</code>.
     *
     * @param comp Component which size will be calculated
     * @return size in bytes of 24 bit bitmap file needed ti save contents of
     *         given component
     */
    private int calculateBMPSize(Component comp) {
        int bytesPerRow = comp.getWidth() * 3;
        
        /*
         * Size of each row must be divisiable by four; if it's not, some
         * extra bytes are added.
         */
        int zerosPadding = 0;
        if(bytesPerRow % 4 != 0)
            zerosPadding = 4 - (bytesPerRow % 4);
        bytesPerRow += zerosPadding;
        
        /*
         * 54 bytes is a constant size of headers of 24 bit bitmap file.
         */
        return 54 + bytesPerRow * comp.getHeight();
    }
    
    /**
     * Saves contents of a Component <code>comp</code> to file <code>filePath
     * </code>. 
     *
     * @param comp Component which will be saved
     * @param filePath file to save the Component to
     * @throws IOException if an I/O error occurs
     */
    public void saveImage(Component comp, String filePath) throws IOException {
        
        BMPOutputStream out = new BMPOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
        int size = calculateBMPSize(comp);
        
        out.writeFileHeader(size, 54);
        out.writeBitmapInfoHeader(40, comp.getWidth(), comp.getHeight(), (short)1, (short)24, 0, 0, 0, 0, 0, 0);
        
        /*
         * The image is saved part by part starting from the bottom so that
         * it uses less memory because the whole image is never held in it.
         */
        int imageHeight = Math.min(Math.max(imagePixels / comp.getWidth(), 1), comp.getHeight());
        int numberOfParts = (int) Math.ceil((double) comp.getHeight() / (double) imageHeight);
        int partsSavedSoFar = 0;
        int lastImageHeight = comp.getHeight() % imageHeight;
        BufferedImage image = new BufferedImage(comp.getWidth(), imageHeight, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage lastImage;
        if(lastImageHeight == 0)
            lastImage = image;
        else
            lastImage = new BufferedImage(comp.getWidth(), lastImageHeight, BufferedImage.TYPE_3BYTE_BGR);
        
        for(int i = comp.getHeight() - image.getHeight(); i >= lastImage.getHeight(); i -= image.getHeight()) {
            Graphics g = image.getGraphics();
            g.translate(0, -i);
            comp.paint(g);
            out.writeImage(image);
            g.dispose();
            partsSavedSoFar++;
            double progress = 100.0 * (double) partsSavedSoFar / (double)numberOfParts;
            setChanged();
            notifyObservers(new ProgressData(progress));
            clearChanged();
        }
        
        Graphics g = lastImage.createGraphics();
        comp.paint(g);
        out.writeImage(lastImage);
        setChanged();
        notifyObservers(new ProgressData(100.0));
        clearChanged();
        g.dispose();
        out.close();
    }
    
}
