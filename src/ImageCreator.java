import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ImageCreator {
	private static ArrayList<ImageView> processedImages = new ArrayList<>();

	public static void processData(int[][] data) {
		processedImages = new ArrayList<>();
		createForSingleLine(data);

		Main.instance.addImages(processedImages);
	}

	private static void createForSingleLine(int[][] data) {
		int[] singleLineData = new int[data.length * data[0].length];

		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[y].length; x++) {
				singleLineData[y * data[y].length + x] = data[y][x];
			}
		}

		Integer[] divisors = getDivisors(singleLineData.length);

		final int PIXEL_SIZE = 16;

		for (int width : divisors) {
			WritableImage image = new WritableImage(width * PIXEL_SIZE, singleLineData.length / width * PIXEL_SIZE);
			PixelWriter pw = image.getPixelWriter();
			for (int y = 0; y < image.getHeight(); y += PIXEL_SIZE) {
				for (int x = 0; x < image.getWidth(); x += PIXEL_SIZE) {
					double grey = singleLineData[y / PIXEL_SIZE * width + x / PIXEL_SIZE] / 255.0;
					for (int xb = 0; xb < PIXEL_SIZE; xb++) {
						for (int yb = 0; yb < PIXEL_SIZE; yb++) {
							pw.setColor(x + xb, y + yb, Color.color(grey, grey, grey));
						}
					}
				}
			}
			processedImages.add(new ImageView(image));
		}
	}

	private static Integer[] getDivisors(int number) {
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 2; i < number / 2 + 1; i++) {
			if (number % i == 0) {
				numbers.add(i);
			}
		}

		return numbers.stream().toArray(Integer[]::new);
	}
}
