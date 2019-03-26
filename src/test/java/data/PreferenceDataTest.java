package data;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreferenceDataTest {


    @Test
    public void validateTruePreferenceData() {
        PreferenceData data = new PreferenceData("1", "titulo",1, 45.5F, "test@test.com");
        List<String> validation = data.validate();
        assertEquals(true, validation.isEmpty());
    }

    @Test
    public void emptyPreferenceData() {
        PreferenceData data = new PreferenceData();
        List<String> validation = data.validate();
        assertEquals(false, validation.isEmpty());
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("id")));
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("title")));
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("quantity")));
        assertEquals(true, validation.stream().anyMatch(i -> i.contains("unitPrice")));
    }

}
