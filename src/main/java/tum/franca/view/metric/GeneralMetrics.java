
package tum.franca.view.metric;

import javafx.collections.ObservableList;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell.GroupType;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;
import tum.franca.util.RectangleUtil;

/**
 * 
 * @author michaelschott
 * 
 *         In this class the main metrics are calculated.
 *
 */
public class GeneralMetrics {

	/**
	 * Sets general metrics in the application. Use for updating.
	 */
	public static void setAll() {
		setServicesAndGroups();
		setRelations();
		setCoupling();
		setCohesion();
		setMostCommon();
		setLeastCommon();
		setAvgCouplingPerGroup();
		setAvgServicesPerGroup();
	}
	
	/**
	 * Average coupling per Group is set. Calculated: Every outgoing/incoming
	 * relation per lowest level group.
	 */
	public static void setAvgCouplingPerGroup() {
		MainAppController.staticAvgCoupling.setText(String.format("%.02f", getAvgCouplingPerGroup()));
	}

	/**
	 * Average Services per Group. All Services divided by low level group. #
	 * services / # low level group
	 */
	public static void setAvgServicesPerGroup() {
		MainAppController.staticAvgService.setText(String.format("%.02f", getAvgServicePerGroup()));
	}

	public static float getAvgCouplingPerGroup() {
		int counterOf = 0;
		int counter = 0;
		for (ICell cell : MainApp.graph.getModel().getAddedCells()) {
			if (cell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) cell).style.ordinal() == RectangleUtil.getLowestGroup()) {
					if (((ResizableRectangleCell) cell).containsRectangleCell().size() > 0) {
						counterOf++;
						for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
							RectangleCell source = (RectangleCell) edge.getSource();
							RectangleCell target = (RectangleCell) edge.getTarget();
							if (((ResizableRectangleCell) cell).containsRectangleCell().contains(source)
									&& !((ResizableRectangleCell) cell).containsRectangleCell().contains(target)
									|| !((ResizableRectangleCell) cell).containsRectangleCell().contains(source)
											&& ((ResizableRectangleCell) cell).containsRectangleCell()
													.contains(target)) {
								counter++;
							}
						}
					}
				}
			}
		}
		return (float) counter / counterOf;
	}

	public static float getAvgServicePerGroup() {
		int counterService = 0;
		int counterLowLevelGroup = 0;
		for (ICell cell : MainApp.graph.getModel().getAddedCells()) {
			if (cell instanceof RectangleCell) {
				counterService++;
			}
			if (cell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) cell).style.ordinal() == RectangleUtil.getLowestGroup()) {
					if (((ResizableRectangleCell) cell).containsRectangleCell().size() > 0) {
						counterLowLevelGroup++;
					}
				}
			}
		}
		return (float) counterService / counterLowLevelGroup;
	}

	/**
	 * Set the number of Groups and subgroups.
	 */
	public static void setServicesAndGroups() {
		int serviceCounter = 0, groupCounter = 0, subGroupCounter = 0, subSubGroupCounter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				serviceCounter++;
			}
			if (iCell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) iCell).style == GroupType.TOPLEVEL) {
					groupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == GroupType.SUBLEVEL) {
					if (((ResizableRectangleCell) iCell).containsRectangleCell().size() > 0) {
						subGroupCounter++;
					}
				}
				if (((ResizableRectangleCell) iCell).style == GroupType.SUBSUBLEVEL) {
					if (((ResizableRectangleCell) iCell).containsRectangleCell().size() > 0) {
						subSubGroupCounter++;
					}
				}
			}
		}
		MainAppController.staticServicesText.setText(String.valueOf(serviceCounter));
		MainAppController.staticGroupsText.setText(String.valueOf(groupCounter));
		MainAppController.staticSubGroupsText.setText(String.valueOf(subGroupCounter));
		MainAppController.staticSubSubGroupsText.setText(String.valueOf(subSubGroupCounter));
	}

	/**
	 * Returns the number of relations by counting the number of edges.
	 * 
	 * @return int - number of relations
	 */
	public static int getRelations() {
		ObservableList<IEdge> cells = MainApp.graph.getModel().getAddedEdges();
		int relation = 0;
		for (@SuppressWarnings("unused")
		IEdge edge : cells) {
			relation++;
		}
		return relation;
	}

	/**
	 * Sets the number of relations.
	 */
	public static void setRelations() {
		MainAppController.staticRelationsText.setText(String.valueOf(getRelations()));
	}

	/**
	 * Returns the coupling by counting the number of relations between top level group to top level group.
	 * @return int - # coupling
	 */
	public static int getCoupling() {
		int couplingCounter = 0;
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			ICell sourceCell = edge.getSource();
			ICell targetCell = edge.getTarget();
			for (ResizableRectangleCell groupCell1 : ((RectangleCell) sourceCell).getGroupRectangle()) {
				if (groupCell1.style.ordinal() == RectangleUtil.getHighestGroup()) {
					for (ResizableRectangleCell groupCell2 : ((RectangleCell) targetCell).getGroupRectangle()) {
						if (groupCell2.style.ordinal() == RectangleUtil.getHighestGroup()) {
							if (!groupCell1.equals(groupCell2)) {
								couplingCounter++;
							}
						}
					}
				}
			}
		}
		return couplingCounter;
	}

	/**
	 * Sets the coupling.
	 */
	public static void setCoupling() {
		MainAppController.staticCouplingText.setText(String.valueOf(getCoupling()));

	}

	/**
	 * Returns the cohesion by counter all relations and subtract the # of coupling through {@link #getCoupling()}.
	 * @return int - # cohesion
	 */
	public static int getCohesion() {
		return getRelations() - getCoupling();
	}

	/**
	 * Set the cohesion. 
	 */
	public static void setCohesion() {
		MainAppController.staticCohesionText.setText(String.valueOf(getCohesion()));
	}

	public static String getMostCommonProperty() {
		int i = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i < ((ResizableRectangleCell) iCell).containsRectangleCell().size()) {
					i = ((ResizableRectangleCell) iCell).containsRectangleCell().size();
				}
			}
		}
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i == ((ResizableRectangleCell) iCell).containsRectangleCell().size()) {
					return ((ResizableRectangleCell) iCell).getName();
				}
			}
		}
		return "Unknown";
	}

	/**
	 * Sets the most common property.
	 */
	public static void setMostCommon() {
		MainAppController.staticMostCommonText.setText(getMostCommonProperty());
	}

	public static String getLeastCommonProperty() {
		int i = 1000;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i > ((ResizableRectangleCell) iCell).containsRectangleCell().size()) {
					i = ((ResizableRectangleCell) iCell).containsRectangleCell().size();
				}
			}
		}
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i == ((ResizableRectangleCell) iCell).containsRectangleCell().size()) {
					return ((ResizableRectangleCell) iCell).getName();
				}
			}
		}
		return "Unknown";
	}

	/**
	 * Sets the least common property.
	 */
	public static void setLeastCommon() {
		MainAppController.staticLeastCommonText.setText(getLeastCommonProperty());
	}

	public static float getAverageCouplingOfService() {
		return (float) getCoupling()
				/ MainApp.graph.getModel().getAddedCells().stream().filter(e -> e instanceof RectangleCell).count();
	}

	public static float getAverageCohesionOfService() {
		return (float) getCohesion()
				/ MainApp.graph.getModel().getAddedCells().stream().filter(e -> e instanceof RectangleCell).count();
	}

}
