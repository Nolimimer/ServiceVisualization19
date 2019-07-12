package tum.franca.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tum.franca.factory.GroupSetter;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.graph.ICell;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;
import tum.franca.view.listView.ListViewWrapper;
import tum.franca.view.treeView.TreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class MainAppController {
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private SplitPane splitPane2;
	
	@FXML
	private ListView<String> listView;
	@FXML 
	private ListView<String> listView2;
	@FXML
	private ListView<String> listView3;
	@FXML
	private ListView<String> listView4;
	
	private ListViewWrapper listViewWrapper;

	@FXML
	public void initialize() throws Exception {
		this.listViewWrapper = new ListViewWrapper(listView, listView2, listView3, listView4);
		listViewWrapper.createListViews();
	}
	
	@FXML
	public void applyGrouping() {
		if (StaticFidlReader.getFidlList() != null) {
		GroupSetter gS = new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
		gS.createCanvas();
		TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
		treeView.createTree();
		splitPane.setDividerPosition(1, 0.85);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No Fidl Files available");
			alert.setHeaderText(null);
			alert.setContentText("Import Fidl-Files to start grouping.");

			alert.showAndWait();
		}
	}

	@FXML
	public void makeNewGroup() {

		TextInputDialog dialog = new TextInputDialog("Group");
		dialog.setTitle("Rectangle Group Name");
		dialog.setHeaderText("How should the group be called?");
		dialog.setContentText("Please enter your name:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Your name: " + result.get());
			final ICell cellGroup = new ResizableRectangleCell(60, 120, result.get(), ResizableRectangleCell.FontStyle.BIG);
			MainApp.graph.addCell(cellGroup);
		}

	}
	
	String path;

	@FXML
	public void importFile() throws IOException {
		final FileChooser fileChooser = new FileChooser();
		List<File> list = fileChooser.showOpenMultipleDialog(MainApp.primaryStage);
		StaticFidlReader.newFidlList();
		for (File file : list) {
			URI uri = URI.createFileURI(file.getAbsolutePath());
			StaticFidlReader.getFidlList().add(new FidlReader(uri));
		}
		GroupSetter gS = new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
		gS.createCanvas();
		TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
		treeView.createTree();
		splitPane.setDividerPosition(1, 0.85);
		
	}
	
	@FXML
	public void timeSpecification() {
		TextInputDialog dialog = new TextInputDialog(GroupSetter.interval + "");
		dialog.setTitle("Time Specification Settings");
		dialog.setHeaderText("Set the interval for grouping the time specification.");
		dialog.setContentText("Set the interval in ns:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			try {
			GroupSetter.interval = Integer.valueOf(result.get());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No valid integer: " + result.get());
				alert.setContentText("Please provide a valid integer (ex.: 109).");

				alert.showAndWait();
				timeSpecification();
			}
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	
	public void alert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Your stupid");
		alert.setHeaderText("No valid integer: ");
		alert.setContentText("Please provide a valid integer (ex.: 109).");

		alert();
		alert.showAndWait();
	}

	@FXML
	public void about() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("About.fxml"));
			Stage stage = new Stage();
			stage.setTitle("About VisualFX Franca");
			stage.setScene(new Scene(root, 450, 450));
			stage.setAlwaysOnTop(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
