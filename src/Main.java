import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

	public static Main instance;

	private TextArea input;

	private ScrollPane outputPane;
	private HBox output;

	private Button computeButton;

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;

		BorderPane center = new BorderPane();
		Scene scene = new Scene(center);
		primaryStage.setScene(scene);

		input = new TextArea("Enter list of numbers between 0 and 255 separated by tabs!");
		output = new HBox();
		output.setSpacing(50);
		outputPane = new ScrollPane(output);
		outputPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		outputPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		computeButton = new Button("Compute");

		computeButton.setOnAction(event -> ImageCreator.processData(getInput()));

		center.setTop(input);
		center.setCenter(outputPane);
		center.setRight(computeButton);

		primaryStage.setMaximized(true);
		primaryStage.setTitle("Grey Scale Image Creator");
		primaryStage.show();
	}

	public void addImages(ArrayList<ImageView> images) {
		output.getChildren().clear();
		output.getChildren().addAll(images);
	}

	private int[][] getInput() {
		String in = input.getText();

		String[] rows = in.split("\\n");
		int[][] matrix = new int[rows.length][rows[0].split("\\t").length];

		for (int y = 0; y < rows.length; y++) {
			String row = rows[y];
			String[] columns = row.split("\\t");
			for (int x = 0; x < columns.length; x++) {
				String column = columns[x];
				matrix[y][x] = Integer.parseInt(column);
			}
		}

		return matrix;
	}

	public static void main(String[] args) {
		launch(args);
	}
}