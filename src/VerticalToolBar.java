import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

/**
 * Vertical Toolbar to easily add Buttons, ChoiceBoxes, (... and more?) to the GUI
 */
public class VerticalToolBar extends VBox {

	public VerticalToolBar() {
		setFillWidth(true);
	}

	public void addButton(Button button) {
		button.setMaxWidth(Double.MAX_VALUE);
		getChildren().add(button);
	}
	public void addChoiceBox (ChoiceBox box) {
		box.setMaxWidth(Double.MAX_VALUE);
		getChildren().add(box);
	}

}
