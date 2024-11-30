package Services;

public class ButtonSelectionService {
    private static ButtonSelectionService instance;
    private String selectedButtonId;

    private ButtonSelectionService() {}

    public static ButtonSelectionService getInstance() {
        if (instance == null) {
            instance = new ButtonSelectionService();
        }
        return instance;
    }

    public String getSelectedButtonId() {
        return selectedButtonId;
    }

    public void setSelectedButtonId(String selectedButtonId) {
        this.selectedButtonId = selectedButtonId;
    }
}