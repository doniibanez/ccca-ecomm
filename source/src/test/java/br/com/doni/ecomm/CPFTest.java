package br.com.doni.ecomm;

import br.com.doni.ecomm.models.CPF;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = EcommerceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class CPFTest {
    private final String CPF_VALID = "764.286.220-25";
    private final String CPF_INVALID = "123.123.123-222";
    private final String CPF_EMPTY = "";
    private final String CPF_INVALID_LENGTH = "123";
    private final String CPF_INVALID_CHAR = "abc.213.220-25";
    private final String CPF_SAME_CHAR = "111.111.111-11";

    @Test
    void testCPF_withValidCPF() {
        assertThatNoException().isThrownBy(() -> new CPF(CPF_VALID));
    }

    @Test
    void testCPF_withInvalidCPF() {
        assertThatThrownBy(() -> new CPF(CPF_INVALID));
    }

    @Test
    void testCPF_withEmptyCPF() {
        assertThatThrownBy(() -> new CPF(CPF_EMPTY));
    }

    @Test
    void testCPF_withInvalidLengthCPF() {
        assertThatThrownBy(() -> new CPF(CPF_INVALID_LENGTH));
    }

    @Test
    void testCPF_withInvalidCharCPF() {
        assertThatThrownBy(() -> new CPF(CPF_INVALID_CHAR));
    }

    @Test
    void testCPF_withSameChar() {
        assertThatThrownBy(() -> new CPF(CPF_SAME_CHAR));
    }
}
