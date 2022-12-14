package main.controller;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Static util class that is used for handling popup alerts.
 * @author Alexander Tan Ka Jin
 */
public class AlertPopup {
	/**
	 * Creates and shows an error popup alert.
	 * @param msg - The content displayed in the popup.
	 * @return The popup that was shown.
	 */
	public static Alert errorPopup(String msg) {
		Alert errorLoading = new Alert(AlertType.ERROR);
		errorLoading.setContentText(msg);
		errorLoading.showAndWait();
		return errorLoading;
	}
	
	/**
	 * Creates and shows a confirmation popup alert.
	 * @param msg - The content displayed in the popup.
	 * @return The choice selected by the user.
	 */
	public static Optional<ButtonType> confirmPopup(String msg){
		Alert saveConfirm = new Alert(AlertType.CONFIRMATION);
		saveConfirm.setContentText(msg);
		return saveConfirm.showAndWait();
	}
}