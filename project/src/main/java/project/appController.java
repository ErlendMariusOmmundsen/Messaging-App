package project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class appController {
	
	@FXML private AnchorPane anchor;
	@FXML private Label header, player_label, player_label1, score_label, score_value, output, loss_label, leaderboard_label;
	@FXML private TextField player_input;
	@FXML private TextArea score_area;
	@FXML private Button green, red, yellow, blue, start_btn, check_btn, next_round_btn;

}
