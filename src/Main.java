import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
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

	private TextArea inputTextArea;

	private ScrollPane outputPane;
	private HBox imageOutputBox;

	private Button computeButton;
	private ChoiceBox inputBox;

	private TableView table;

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;

		BorderPane rootPane = new BorderPane();
		SplitPane centerPane = new SplitPane();
		VerticalToolBar toolbar = new VerticalToolBar();
		initTable();




		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		//Toolbar
		ObservableList<String> options =
				FXCollections.observableArrayList(
						"Tab",
						"Space",
						",",
						";"
				);
		inputBox = new ChoiceBox<>(options);
		inputBox.setValue("Tab");

		computeButton = new Button("Compute");
		computeButton.setOnAction(event -> ImageCreator.processData(getInputTextArea()));

		toolbar.addChoiceBox(inputBox);
		toolbar.addButton(computeButton);
		//SplitPane with inputTextArea and imageOutputBox
		inputTextArea = new TextArea();
		imageOutputBox = new HBox();
		imageOutputBox.setSpacing(50);
		outputPane = new ScrollPane(imageOutputBox);
		outputPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		outputPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		TabPane tabPane = new TabPane();
		Tab textTab = new Tab("InputTextArea");
		textTab.setContent(inputTextArea);
		Tab tableTab = new Tab("InputTable");
		tableTab.setContent(table);

		tabPane.getTabs().addAll(textTab,tableTab);

		centerPane.setOrientation(Orientation.VERTICAL);
		centerPane.getItems().addAll(tabPane,outputPane);
		centerPane.setDividerPosition(1,0.5);

		//putting things together
		rootPane.setTop(new Text("Insert a String of single Bytes (0-255) you want processed below. Choose the used separator from the ComboBox to the right.\n press <Compute> to process the images"));
		rootPane.setCenter(centerPane);
		rootPane.setRight(toolbar);

		primaryStage.setMaximized(true);
		primaryStage.setTitle("Grey Scale Image Creator");
		primaryStage.show();
	}

	private void initTable() {
		table = new TableView();



	}

	public void addImages(ArrayList<ImageView> images) {
		imageOutputBox.getChildren().clear();
		imageOutputBox.getChildren().addAll(images);
	}

	private int[][] getInputTextArea() {
		String in = inputTextArea.getText();

		String[] rows = in.split("\\n");
		//check for separator
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