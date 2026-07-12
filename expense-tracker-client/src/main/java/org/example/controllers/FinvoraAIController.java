package org.example.controllers;

import org.example.models.User;
import org.example.services.AIVoiceService;
import org.example.utils.SqlUtil;
import org.example.views.DashboardView;
import org.example.views.FinvoraAIView;

public class FinvoraAIController {

    private FinvoraAIView view;
    private User user;

    public FinvoraAIController(FinvoraAIView view) {
        this.view = view;
        initListeners();
        fetchUser();
    }

    private void fetchUser() {
        try {
            user = SqlUtil.getUserByEmail(view.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        AIVoiceService voiceService = new AIVoiceService();

        javafx.stage.Stage stage = (javafx.stage.Stage) view.getBackBtn().getScene().getWindow();
        stage.setOnCloseRequest(e -> {
            voiceService.stopAudio();
        });

        view.getBackBtn().setOnAction(e -> {
            voiceService.stopAudio();
            stage.close();
        });

        view.getSendBtn().setOnAction(e -> {
            String text = view.getChatInput().getText();
            if(!text.isEmpty()){
                view.getAiStatusLabel().setText("Finvora AI is processing your request...");
                String context = buildDeepContext();
                
                voiceService.processTextDirectly(text, context, "Professional",
                    status -> javafx.application.Platform.runLater(() -> view.getAiStatusLabel().setText(status)),
                    advice -> javafx.application.Platform.runLater(() -> view.getAiAdviceArea().setText(advice)),
                    intentJson -> handleIntent(intentJson)
                );
                view.getChatInput().clear();
            } else {
                view.getAiStatusLabel().setText("Please type a message first.");
            }
        });

        view.getVoiceBtn().setOnAction(e -> {
            if (view.getVoiceBtn().isSelected()) {
                voiceService.startRecording();
                view.getAiStatusLabel().setText("Listening... (Click Voice again to send)");
            } else {
                view.getAiStatusLabel().setText("Processing voice...");
                String context = buildDeepContext();
                voiceService.stopRecordingAndProcess(context, "Professional",
                    status -> javafx.application.Platform.runLater(() -> view.getAiStatusLabel().setText(status)),
                    advice -> javafx.application.Platform.runLater(() -> view.getAiAdviceArea().setText(advice)),
                    intentJson -> handleIntent(intentJson)
                );
            }
        });
    }

    private String buildDeepContext() {
        if (user == null) return "No user context available.";
        StringBuilder sb = new StringBuilder();
        try {
            // Aggregate Exact Balances for Current Year (to match Dashboard UI)
            int currentYear = java.time.LocalDate.now().getYear();
            java.math.BigDecimal totalIn = java.math.BigDecimal.ZERO;
            java.math.BigDecimal totalOut = java.math.BigDecimal.ZERO;
            java.util.List<org.example.models.Transaction> yearTx = org.example.utils.SqlUtil.getAllTransactionsByUserId(user.getId(), currentYear, null);
            if (yearTx != null) {
                for (org.example.models.Transaction t : yearTx) {
                    java.math.BigDecimal amt = java.math.BigDecimal.valueOf(t.getTransactionAmount());
                    if ("income".equalsIgnoreCase(t.getTransactionType())) totalIn = totalIn.add(amt);
                    else totalOut = totalOut.add(amt);
                }
            }
            java.math.BigDecimal currentBalance = totalIn.subtract(totalOut);
            
            sb.append("Total Income: ₹").append(totalIn.setScale(2, java.math.RoundingMode.HALF_UP)).append(". ");
            sb.append("Total Expenses: ₹").append(totalOut.setScale(2, java.math.RoundingMode.HALF_UP)).append(". ");
            sb.append("Current Balance: ₹").append(currentBalance.setScale(2, java.math.RoundingMode.HALF_UP)).append(". ");

            java.util.List<org.example.models.Transaction> recents = org.example.utils.SqlUtil.getRecentTransactionByUserId(user.getId(), 0, 0, 15);
            if (recents != null && !recents.isEmpty()) {
                sb.append("Recent Transactions: ");
                for (org.example.models.Transaction t : recents) {
                    sb.append(String.format("[%s, %s, %s, %s], ", t.getTransactionDate(), t.getTransactionType(), t.getTransactionAmount(), t.getTransactionCategory() != null ? t.getTransactionCategory().getCategoryName() : ""));
                }
            }
            java.util.List<org.example.models.Budget> budgets = org.example.utils.BudgetStore.getBudgets(user.getId());
            if (budgets != null && !budgets.isEmpty()) {
                sb.append(" Budgets: ");
                for (org.example.models.Budget b : budgets) {
                    sb.append(String.format("[%s: %s (Spent: %s)], ", b.getCategory(), b.getLimitAmount(), b.getSpentAmount()));
                }
            }
            java.util.List<org.example.models.SavingsGoal> goals = org.example.utils.GoalStore.getGoals(user.getId());
            if (goals != null && !goals.isEmpty()) {
                sb.append(" Savings Goals: ");
                for (org.example.models.SavingsGoal g : goals) {
                    sb.append(String.format("[%s: Target ₹%s, Saved ₹%s, Deadline %s], ", g.getName(), g.getTargetAmount(), g.getCurrentAmount(), g.getDeadline()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    private void handleIntent(String intentJson) {
        // Intent handling logic from Dashboard could be placed here if needed in AI view
        // For now, AI View is mostly conversational.
        System.out.println("Finvora AI View intent detected: " + intentJson);
    }
}
