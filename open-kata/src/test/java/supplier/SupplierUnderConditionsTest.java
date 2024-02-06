package supplier;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class SupplierUnderConditionsTest {
    @Test
    void shouldReturnNotNull() {
        // Given

        // When
        String result = SupplierUnderConditions.from(() -> "1").orDefault(() -> "default");

        // Then
        Assertions.assertThat(result).isEqualTo("1");
    }

    @Test
    void shouldReturnFirst() {
        // Given

        // When
        String result =
                SupplierUnderConditions.from(() -> "1").or(() -> "2").orDefault(() -> "default");

        // Then
        Assertions.assertThat(result).isEqualTo("1");
    }

    @Test
    void shouldReturnFirstNotNull() {
        // Given

        // When
        String result =
                SupplierUnderConditions.from(() -> (String) null)
                        .or(() -> "2")
                        .or(() -> null)
                        .or(() -> "3")
                        .orDefault(() -> "default");

        // Then
        Assertions.assertThat(result).isEqualTo("2");
    }

    @Test
    void shouldReturnDefaultIfAllNull() {
        // Given

        // When
        String result =
                SupplierUnderConditions.from(() -> (String) null)
                        .or(() -> (String) null)
                        .orDefault(() -> "default");

        // Then
        Assertions.assertThat(result).isEqualTo("default");
    }

}