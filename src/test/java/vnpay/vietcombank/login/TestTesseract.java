package vnpay.vietcombank.login;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class TestTesseract {

	public static void main(String[] args) {
		String TESSERACT_DIR = LoadLibs.extractTessResources("tessdata").getAbsolutePath();
		System.out.println(TESSERACT_DIR);
		Tesseract tesseract = new Tesseract();
		try {

			tesseract.setDatapath(TESSERACT_DIR);

			// the path of your tess data folder
			// inside the extracted file
			String text = tesseract.doOCR(new File("C:\\Users\\anhtt\\Documents\\VIETCOMBANK_OMNI_MOBILE\\image\\image.png"));

			// path of your image file
			System.out.print(text);
		} catch (TesseractException e) {
			e.printStackTrace();
		}

	}

}
