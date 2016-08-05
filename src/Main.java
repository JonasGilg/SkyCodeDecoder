import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

	public static Main instance;

	private TextArea input;

	private ScrollPane outputPane;
	private HBox output;

	private Button computeButton;
	private ChoiceBox inputBox;

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;

		BorderPane center = new BorderPane();
		Scene scene = new Scene(center);
		primaryStage.setScene(scene);
		BorderPane inputArea = new BorderPane();

		ObservableList<String> options =
				FXCollections.observableArrayList(
						"Tab",
						"Space",
						",",
						";"
				);


		inputBox = new ChoiceBox<>(options);
		inputBox.setValue("Tab");
		input = new TextArea();

		output = new HBox();
		output.setSpacing(50);
		outputPane = new ScrollPane(output);
		outputPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		outputPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		computeButton = new Button("Compute");

		computeButton.setOnAction(event -> ImageCreator.processData(getInput()));
		inputArea.setTop(new Text("Insert a String of single Bytes (0-255) you want processed below. Choose the used separator from the ComboBox to the right.\n press <Compute> to process the images"));
		inputArea.setCenter(input);
		inputArea.setRight(inputBox);
		center.setTop(inputArea);
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
		String separator = (String)inputBox.getValue();
		switch(separator){
			case "Tab":
				separator = "\\t";
				break;
			case "Space":
				separator = " ";
				break;
			default:
				break;
		}
		int[][] matrix = new int[rows.length][rows[0].split(separator).length];

		for (int y = 0; y < rows.length; y++) {
			String row = rows[y];
			String[] columns = row.split(separator);
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