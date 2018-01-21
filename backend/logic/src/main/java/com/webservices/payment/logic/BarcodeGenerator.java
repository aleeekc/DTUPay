package com.webservices.payment.logic;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Class used for generating a barcode from a random String
 *
 * @author Aleksandar, James
 * @version 1.0
 */
public class BarcodeGenerator {
	
	/**
	 * Constructor - it creates a .jpg barcode from a random String and
	 * saves it on the disk
	 *
	 * @param uuidString (required) used to generate the barcode from
	 * @return path + filename of the generated barcode
	 */
		public static String generate(String uuidString) {
		Code39Bean bean = new Code39Bean();
		
		final int dpi = 150;
		
		//Configure the barcode generator
		bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar, width exactly one pixel
		bean.setWideFactor(3);
		bean.doQuietZone(false);
		
		//Open output file
		String filename = "./target/storage/images/" + uuidString + ".jpg";
		File outputFile = new File(filename);
		try {
			outputFile.getParentFile().mkdirs();
			outputFile.deleteOnExit();
			
			OutputStream out = new FileOutputStream(outputFile);
			
			//Set up the canvas provider for monochrome PNG output
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(
					out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
			
			//Generate the barcode
			bean.generateBarcode(canvas, uuidString);
			
			//Signal end of generation
			canvas.finish();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filename;
	}
}
