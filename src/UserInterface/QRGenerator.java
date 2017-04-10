package UserInterface;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.*;


/**
 * Created by andylan on 4/5/17.
 */


public class QRGenerator{
    /* This class contains everything necessary to generate QR Codes */
    public int     Size;
    public int     Redundancy;
    public String  Encoding;
    public String  Text;
    public String  FilePath;

    public QRGenerator(int size, int redundancy, String encoding, String text, String filepath){
        /*  Do error handling in the constructor */
        try {
            this.Size = size;
            this.Redundancy = redundancy;
            this.Encoding = encoding;
            this.Text = text;
            this.FilePath = filepath;
        }
        catch(IllegalArgumentException e){
            System.err.println("Invalid Input");
        }

    }

    static public void run(int size, int redundancy, String encoding, String text, String filepath){
        try {

            ErrorCorrectionLevel level = ErrorCorrectionLevel.H; // Add something to parse 
            ByteArrayOutputStream img = QRCode.from(text).withSize(size, size)
                                                                     .withErrorCorrection(level)
                                                                     .to(ImageType.PNG)
                                                                     .stream();
            OutputStream output = new FileOutputStream(filepath);
            img.writeTo(output);
            output.flush();
            output.close();
        } catch(IllegalArgumentException e) {
            System.err.println("Invalid");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
