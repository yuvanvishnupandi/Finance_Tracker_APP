package org.example.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.controllers.FinvoraAIController;
import org.example.utils.Utilitie;

public class FinvoraAIView {

    private String email;
    private TextField chatInput;
    private Button sendBtn;
    private ToggleButton voiceBtn;
    private Label aiStatusLabel;
    private TextArea aiAdviceArea;
    private Button backBtn;

    public FinvoraAIView(String email) {
        this.email = email;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        // Header
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        backBtn = new Button("✖ Close AI");
        backBtn.getStyleClass().add("secondary-button");
        
        Label titleLabel = new Label("Finvora AI Assistant");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #4F46E5;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        headerBox.getChildren().addAll(backBtn, spacer, titleLabel, new Region());

        // AI Chat Area
        aiAdviceArea = new TextArea();
        aiAdviceArea.setPromptText("Finvora AI Strategy will appear here...");
        aiAdviceArea.setWrapText(true);
        aiAdviceArea.setEditable(false);
        aiAdviceArea.getStyleClass().add("input-field");
        aiAdviceArea.setStyle("-fx-font-size: 16px; -fx-padding: 15px;");
        VBox.setVgrow(aiAdviceArea, Priority.ALWAYS);

        // Status
        aiStatusLabel = new Label("Ready to assist with your financial goals.");
        aiStatusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B7280;");

        // Input Area
        HBox inputBox = new HBox(15);
        inputBox.setAlignment(Pos.CENTER);
        
        chatInput = new TextField();
        chatInput.setPromptText("Ask about your budget, hypothetical investments, or say hello...");
        chatInput.getStyleClass().add("input-field");
        chatInput.setStyle("-fx-font-size: 16px; -fx-pref-height: 50px;");
        HBox.setHgrow(chatInput, Priority.ALWAYS);
        
        sendBtn = new Button("Send");
        sendBtn.getStyleClass().add("primary-button");
        sendBtn.setStyle("-fx-font-size: 16px; -fx-pref-height: 50px; -fx-pref-width: 100px;");
        
        voiceBtn = new ToggleButton("🎤 Voice");
        voiceBtn.getStyleClass().add("toggle-button");
        voiceBtn.setStyle("-fx-font-size: 16px; -fx-pref-height: 50px; -fx-pref-width: 120px;");
        
        inputBox.getChildren().addAll(chatInput, sendBtn, voiceBtn);

        root.getChildren().addAll(headerBox, aiAdviceArea, aiStatusLabel, inputBox);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        org.example.utils.ThemeManager.apply(scene);

        javafx.stage.Stage aiStage = new javafx.stage.Stage();
        aiStage.setTitle("Finvora AI");
        aiStage.setScene(scene);
        aiStage.show();

        // Bind Controller
        new FinvoraAIController(this);
    }

    public String getEmail() { return email; }
    public TextField getChatInput() { return chatInput; }
    public Button getSendBtn() { return sendBtn; }
    public ToggleButton getVoiceBtn() { return voiceBtn; }
    public Label getAiStatusLabel() { return aiStatusLabel; }
    public TextArea getAiAdviceArea() { return aiAdviceArea; }
    public Button getBackBtn() { return backBtn; }
}
