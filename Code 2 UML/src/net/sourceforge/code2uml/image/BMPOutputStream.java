/*
 * BMPOutputStream.java
 *
 * Created on August 23, 2007, 7:50 PM
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

import java.awt.image.BufferedImage;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is responsible for saving an image as .bmp file. Currently it
 * supports only 24 bit images. Example usage: <br/> <code>
 * BMPOutputStream out = new BMPOutputStream(...); <br/>
 * out.writeFileHeader(...); <br/>
 * out.writeBitmapInfoHeader(...); <br/>
 * out.writeImage(...); <br/>
 * out.close(); </code> <br/>
 * Methods' parameters are described below. The writeImage() method may be 
 * called any times - it is possible to save an image by dividing it into
 * parts and calling writeImage for each of them. However: <br/>
 * - all parts must have the same width and ot must be equal to width set
 * by writeBitmapInfoHeader method <br/>
 * - parts must be saved from bottom-most to top-most <br/>
 * - the sum of heights of all saved parts must be equal to height set by
 * writeBitmapInfoHeader method <br/>
 * If any of those conditions is not met, the behavoiur of this class
 * is unspecified. <br/> <br/>
 *
 * Note: size of 24 bit .bmp file is <br/>
 * headers_size + image_data_size <br/>
 * headers_size is always 54 <br/>
 * image_data_size = bytesPerRow * image_height <br/>
 * bytesPerRow = image_width * 3 + n (3 bytes per pixel plus a constant value)<br/>
 * Size of each row in bytes must be divisible by four and if image_width * 3
 * is not, then a minimum number of extra bytes is added so that <br/>
 * image_width * 3 + number_of_extra_bytes <br/>
 * is divisible by 4.
 *
 * @author Mateusz Wenus
 */
public class BMPOutputStream {
    
    private DataOutputStream out;
    
    /** 
     * Creates a new instance of BMPOutputStream. 
     *
     * @param os an OutputStream to which this BMPOutputStream will write data
     */
    public BMPOutputStream(OutputStream os) {
        this.out = new DataOutputStream(os);
    }
    
    /**
     * Writes a header of a .bmp file.
     *
     * @param size size of a file - see class description how the size should
     *        be calculated
     * @param offset offset from beginning of file to image data - for 24 bit
     *        bitmaps it is always 54
     * @throws IOException if I/O error occurs
     */
    public void writeFileHeader(int size, int offset) throws IOException {
        writeShort(19778);
        writeInt(size);
        writeShort(0); // reserved 
        writeShort(0); // reserved
        writeInt(offset);
    }
    
    /**
     * Writes BITMAPINFOHEADER structure.
     *
     * @param size size of the structure (for 24 bit images it is 40)
     * @param width width of the image 
     * @param height height of the image
     * @param planes number of planes on target device (always 1)
     * @param bpp bytes per pixel (for 24 bit images it is 24)
     * @param compression compression method (0 for no compression)
     * @param dataSize size of image data (may beset to 0 if compression is 0)
     * @param ppmh horizontal pixels per meter on target device (may be set to 0)
     * @param ppmv vertical pixels per meter on target device (may be set to 0)
     * @param colors number of colors used it bitmap (0 for 24 bit images)
     * @param importantColors important colors for bitmap (may be 0)
     *
     * @throws IOException if I/O error occurs
     */
    public void writeBitmapInfoHeader(int size, int width, int height,
            short planes, short bpp, int compression, int dataSize, int ppmh,
            int ppmv, int colors, int importantColors) throws IOException {
        writeInt(size);
        writeInt(width);
        writeInt(height);
        writeShort(planes);
        writeShort(bpp);
        writeInt(compression);
        writeInt(dataSize);
        writeInt(ppmh);
        writeInt(ppmv);
        writeInt(colors);
        writeInt(importantColors);
    }
    
    /**
     * Appends an image to the bitmap file. Images should be appended from 
     * bottom-most to top-most.
     *
     * @param image image to append
     *
     * @throws IOException if I/O error occurs
     */
    public void writeImage(BufferedImage image) throws IOException {
        int[] tab = new int[3];
        int zerosPadding = 0;
        int bytesPerRow = 3 * image.getWidth();
        if((bytesPerRow % 4) != 0)
            zerosPadding = 4 - (bytesPerRow % 4);
        
        for(int y = image.getHeight() - 1; y >= 0; y--) {
            for(int x = 0; x < image.getWidth(); x++) {
                tab = image.getRaster().getPixel(x, y, tab);
                // tab contains RGB, but in .bmp file it must be saved as BGR
                writeByte(tab[2]);
                writeByte(tab[1]);
                writeByte(tab[0]);
            }
            for(int j = 0; j < zerosPadding; j++) {
                writeByte(0);
            }
        }
    }
    
    /**
     * Writes to the output stream the eight low-order bits of the argument 
     * <code>v</code>. The 24 high-order bits of <code>v</code> are ignored. 
     *
     * @param v the byte value to be written.
     * @throws IOException if an I/O error occurs.
     */
    private void writeByte(int v) throws IOException {
        out.writeByte(v);
    }
    
    /**
     * Writes an <code>int</code> value, to the output stream. The bytes are 
     * written in little-endian order (as required by .bmp file).
     *
     * @param v the <code>int</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    private void writeInt(int v) throws IOException {
        out.writeInt(Integer.reverseBytes(v));
    }
    
    /**
     * Writes a <code>short</code> value, to the output stream. The bytes are 
     * written in little-endian order (as required by .bmp file).
     *
     * @param v the <code>short</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    private void writeShort(int v) throws IOException {
        out.writeShort(Short.reverseBytes((short) v));
    }
 
    /**
     * Flushes the underlying OutputStream.
     *
     * @throws IOException if an I/O error occurs
     */
    public void flush() throws IOException {
        out.flush();
    }
    
    /**
     * Closes the underlying OutputStream.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void close() throws IOException {
        out.close();
    }
}