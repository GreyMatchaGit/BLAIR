package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ColorSelectorService {
    private final ArrayList<String> colors = new ArrayList<>(Arrays.asList(
            "2A9096", "BE7676", "6F88C7", "94644A",
            "C884AF", "CE7146", "DCBD61", "AF505C"));
    private final Random random = new Random();

    public String getRandomColor() {
        int index = random.nextInt(colors.size());
        return colors.get(index);
    }
}
