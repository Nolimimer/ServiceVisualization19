package tum.franca.factory.creator;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author michaelschott
 *
 */
public class ServiceCreation extends Application {
	
	public static Stage stage;
	private static int X = 350;
	private static int Y = 140;

	@Override
	public void start(Stage stage) {
		if (ServiceCreation.stage == null) {
			ServiceCreation.stage = stage;
		} else {
			ServiceCreation.stage.close();
			ServiceCreation.stage = stage;
		}
	}

	public static void initServiceCreation() {
		if (ServiceCreation.stage == null) {
			ServiceCreation.stage = new Stage();
		} else {
			ServiceCreation.stage.close();
			ServiceCreation.stage = new Stage();
		}
		Pane root;
	
		try {
			root = FXMLLoader.load(ServiceCreation.class.getResource("/ServiceCreation.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Create Service");
			stage.setResizable(false);
			stage.setX(X);
			stage.setY(Y);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void initServiceCreationWithLocation(int x, int y) {
		if (ServiceCreation.stage == null) {
			ServiceCreation.stage = new Stage();
		} else {
			ServiceCreation.stage.close();
			ServiceCreation.stage = new Stage();
		}
		Pane root;
	
		try {
			root = FXMLLoader.load(ServiceCreation.class.getResource("/fxml/ServiceCreation.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Create Service");
			stage.setResizable(false);
			stage.setX(x);
			stage.setY(y);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}



	}


	public static void main(String[] args) {
		launch(args);
	}

}
