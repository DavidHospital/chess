package game;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class Resources {
	
	//HashMap which contains the names of all images, which are stored in BufferedImages
	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	//HashMap which contains the names of all fonts, which are stored in Font Objects
	private static HashMap<String, Font> fonts = new HashMap<String, Font>();
	
	/**
	 * load all images from the "res" folder and store them in 'images'
	 */
	public static void loadImages () {
		File f = new File ("res/img");													//make a new File object, f,  from the folder "res"
		for (File image : f.listFiles()) {											//for all files within f
			try {								
				StringTokenizer st = new StringTokenizer (image.getName(), ".");	
				String name = st.nextToken();
				
				/*	put the name of the image (located before the '.' in the file extension)
				 * 	into 'images' along with the read file
				 */
				images.put (name, ImageIO.read(image));								
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * load all fonts
	 */
	public static void loadFonts () {
		File folder = new File ("res/font");
		for (File f : folder.listFiles()) {
			try {
			     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			     Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/" + f.getName()));
			     ge.registerFont(font);
			     
			     StringTokenizer st = new StringTokenizer(f.getName(), ".");
			     fonts.put (st.nextToken(), font);
			} catch (IOException|FontFormatException e) {
			     //Handle exception
			}
		}
	}
	
	
	/**
	 * get the image from 'images' with name 'name'
	 * @param name
	 * @return
	 */
	public static BufferedImage getImage (String name) {
		if (images.containsKey(name)) {
			return images.get(name);
		} 
		return null;
	}
	
	public static Font getFont (String name) {
		if (fonts.containsKey(name)) {
			return fonts.get(name);
		}
		return null;
	}
}
