package tum.franca.graph.graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tum.franca.main.MainApp;
import tum.franca.view.tab.MenuBarTop;

/**
 * 
 * @author michaelschott
 *
 */
public class Grid {

	private static void setHGrid(Graph graph, int x) {
		if (graph != null) {
			for (int i = 0; i < 2000; i++) {
				Line line = new Line();
				line.setStartX(x);
				line.setStartY(-5000);
				line.setEndX(x);
				line.setEndY(10000);
				line.setStrokeWidth(0.5);
				line.setStroke(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()),
						((int) Color.GREY.getBlue()), 0.2));
				line.setFill(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()),
						((int) Color.GREY.getBlue()), 0.2));
				x += 50;
				graph.getCanvas().getChildren().add(line);
				line.toBack();
			}
		}
	}

	private static void setVGrid(Graph graph, int y) {
		if (graph != null) {
			for (int i = 0; i < 2000; i++) {
				Line line = new Line();
				line.setStartX(-5000);
				line.setStartY(y);
				line.setEndX(10000);
				line.setEndY(y);
				line.setStrokeWidth(0.5);
				line.setFill(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()),
						((int) Color.GREY.getBlue()), 0.2));
				line.setStroke(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()),
						((int) Color.GREY.getBlue()), 0.2));
				y += 50;
				graph.getCanvas().getChildren().add(line);
				line.toBack();
			}
		}
	}

	/**
	 * Adding grid to canvas.
	 */
	public static void add() {
		for (Graph graph : MainApp.graphList) {
			setHGrid(graph, -10000);
		}
		for (Graph graph : MainApp.graphList) {
			setVGrid(graph, -20000);
		}
	}

	/**
	 * Removing grid from canvas.
	 */
	public static void remove() {
		for (Graph graph : MainApp.graphList) {
			if (graph != null) {
				List<Node> removeList = new ArrayList<>();
				for (Node node : graph.getCanvas().getChildren()) {
					if (node instanceof Line) {
						removeList.add(node);
					}
				}
				graph.getCanvas().getChildren().removeAll(removeList);
			}
		}
	}

	public static void checkAndConfigureGrid() {
		if (MenuBarTop.isGridEnabled) {
			Grid.remove();
			Grid.add();
		}
	}
}
