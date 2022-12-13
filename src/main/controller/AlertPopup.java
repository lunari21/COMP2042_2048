package main.controller;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertPopup {
	public static Alert errorPopup(String msg) {
		Alert errorLoading = new Alert(AlertType.ERROR);
		errorLoading.setContentText(msg);
		errorLoading.showAndWait();
		return errorLoading;
	}
	
	public static Optional<ButtonType> confirmPopup(String msg){
		Alert saveConfirm = new Alert(AlertType.CONFIRMATION);
		saveConfirm.setContentText(msg);
		return saveConfirm.showAndWait();
	}
}