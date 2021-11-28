package br.com.doni.ecomm;

import br.com.doni.ecomm.util.DocumentUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    void testCPF_withValidCPF() {
        Assertions.assertTrue(DocumentUtil.validate(CPF_VALID));
    }

    @Test
    void testCPF_withInvalidCPF() {
        Assertions.assertFalse(DocumentUtil.validate(CPF_INVALID));
    }

    @Test
    void testCPF_withEmptyCPF() {
        Assertions.assertFalse(DocumentUtil.validate(CPF_EMPTY));
    }

    @Test
    void testCPF_withInvalidLengthCPF() {
        Assertions.assertFalse(DocumentUtil.validate(CPF_INVALID_LENGTH));
    }

    @Test
    void testCPF_withInvalidCharCPF() {
        Assertions.assertFalse(DocumentUtil.validate(CPF_INVALID_CHAR));
    }
}
