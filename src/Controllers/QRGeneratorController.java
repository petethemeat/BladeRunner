package Controllers;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;


import java.io.*;


/**
 * Created by andylan on 4/5/17.
 */

public class QRGeneratorController {
    /* This class contains everything necessary to generate QR Codes */

    static public InputStream run(int size,  String text) throws IllegalArgumentException, IOException{
        ErrorCorrectionLevel level = ErrorCorrectionLevel.H; // Highest Error Correction
        File img = QRCode.from(text).withSize(size, size).withErrorCorrection(level)
                                                         .to(ImageType.PNG)
                                                         .file();
        return new FileInputStream(img);
    }
}
