package tum.franca.util;

import java.awt.Point;

import javafx.scene.layout.Pane;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class Binding {
	
	/**
	 * 
	 * @param pane
	 * @param fontStyle
	 */
	public static void bind(Pane pane, int fontStyle) {
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				RectangleCell cell = (RectangleCell) iCell;
				Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
				Point point2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(),
						pane.getLayoutY(), pane.getWidth(), pane.getHeight());
				Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
				Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
						cell.pane.getLayoutY(), cell.pane.getWidth(), cell.pane.getHeight());

				if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
					cell.pane.layoutXProperty().bind(
							pane.layoutXProperty().subtract(pane.getLayoutX()).add(cell.pane.getLayoutX()));
					cell.pane.layoutYProperty().bind(
							pane.layoutYProperty().subtract(pane.getLayoutY()).add(cell.pane.getLayoutY()));
				}
			} else {
				if (iCell instanceof ResizableRectangleCell && ((ResizableRectangleCell) iCell).style.ordinal() < fontStyle) {
					ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
					Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
					Point point2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(),
							pane.getLayoutY(), pane.getWidth(), pane.getHeight());
					Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
					Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
							cell.pane.getLayoutY(), cell.pane.getWidth(), cell.pane.getHeight());

					if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
						cell.pane.layoutXProperty().bind(
								pane.layoutXProperty().subtract(pane.getLayoutX()).add(cell.pane.getLayoutX()));
						cell.pane.layoutYProperty().bind(
								pane.layoutYProperty().subtract(pane.getLayoutY()).add(cell.pane.getLayoutY()));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param pane
	 * @param fontStyle
	 */
	public static void unbind(Pane pane, int fontStyle) {
		pane.layoutXProperty().unbind();
		pane.layoutYProperty().unbind();
		for (ICell iCell: MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				((RectangleCell) iCell).pane.layoutXProperty().unbind();
				((RectangleCell) iCell).pane.layoutYProperty().unbind();
			}
			if (iCell instanceof ResizableRectangleCell) {
				((ResizableRectangleCell) iCell).pane.layoutXProperty().unbind();
				((ResizableRectangleCell) iCell).pane.layoutYProperty().unbind();
				
			}
		}
	}

}
