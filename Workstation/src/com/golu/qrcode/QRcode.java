package com.golu.qrcode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRcode {

	public static void main(String[] args) throws WriterException, IOException,	NotFoundException 
	{
		String qrCodeData = "Priyanshu LAL";
		String filePath = "./output/harsh.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType,ErrorCorrectionLevel> hintMap = new HashMap<>();

		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		System.out.println("QR Code image created successfully!");

		System.out.println("Data read from QR Code: "+ readQRCode(filePath, charset, hintMap));

	}

	public static void createQRCode(
										String qrCodeData, 
										String filePath,
										String charset, 
										Map<EncodeHintType,ErrorCorrectionLevel> hintMap, 
										int qrCodeheight, 
										int qrCodewidth
										) throws WriterException, IOException 
	{
		BitMatrix matrix = new MultiFormatWriter().encode(
															new String(qrCodeData.getBytes(charset), charset),
															BarcodeFormat.QR_CODE, 
															qrCodewidth, 
															qrCodeheight, 
															hintMap
															);
		Path path = Paths.get(filePath);
		
		MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), path);
	}

	public static String readQRCode(
										String filePath, 
										String charset, 
										Map<EncodeHintType,ErrorCorrectionLevel> hintMap
									) throws FileNotFoundException, IOException, NotFoundException 
	{
		BinaryBitmap binaryBitmap = new BinaryBitmap(
												new HybridBinarizer(
																		new BufferedImageLuminanceSource( ImageIO.read(new FileInputStream(filePath)))
																	)
													);
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		
		return qrCodeResult.getText();
	}
}

