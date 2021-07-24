import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Extract {

	public static void main(String[] args) {

		String srcFolderPath = "C:\\Users\\GuannanLyu\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
		String desFolderPath = "C:\\Users\\GuannanLyu\\Pictures";
		int containerSize = 1024 * 1024;
		int imageNumber = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int len = 0;
		int idealImgWidth = 1920;
		int idealImgHeight = 1080;

		File src = new File(srcFolderPath);
		File des = new File(desFolderPath);
		des.mkdir();

		File[] rawImgs = src.listFiles();
		byte[] bytes = new byte[containerSize];

		for (int i = 0; i < rawImgs.length; i++) {
			try {
				BufferedImage bi = ImageIO.read(rawImgs[i]);
				int imgWidth = bi.getWidth();
				int imgHeight = bi.getHeight();

				if (imgWidth != idealImgWidth || imgHeight != idealImgHeight) {
					continue;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			String rawImgName = rawImgs[i].getName();
			String rawImgPath = srcFolderPath + "\\" + rawImgName;

			String processedImgPath = desFolderPath + "\\image" + imageNumber + ".png";
			imageNumber++;

			try {
				fis = new FileInputStream(rawImgPath);
				fos = new FileOutputStream(processedImgPath);

				while (true) {
					len = fis.read(bytes);
					if (len == -1) {
						len = 0;
						break;
					}
					fos.write(bytes, 0, len);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		try {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Wallpaper is extracted to: \"" + desFolderPath + "\"");
	}

}
