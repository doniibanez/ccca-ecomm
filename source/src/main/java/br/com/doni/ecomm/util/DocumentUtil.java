package br.com.doni.ecomm.util;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentUtil.class);
    private static int CPF_MINIMUM_LENGTH = 11;
    private static int CPF_MAX_LENGTH = 14;

    public static boolean validate(String cpf) {
        if (Strings.isBlank(cpf) || !hasValidLength(cpf))
            return false;
        cpf = removeSymbols(cpf);
        try {
            var digitOneResult = calculateResultDigitOne(getFirstDigit(cpf));
            var digitTwoResult = calculateResultDigitTwo(getSecondDigit(cpf, digitOneResult));
            return getVerifyingDigit(cpf).equals(getVerifyingDigitResult(digitOneResult, digitTwoResult));
        } catch (Exception e) {
            LOG.error("Error occurred during cpf [{}] validation: {}", cpf, e.getMessage());
            return false;
        }
    }

    private static boolean hasValidLength(String cpf) {
        return cpf.length() >= CPF_MINIMUM_LENGTH || cpf.length() <= CPF_MAX_LENGTH;
    }

    private static String removeSymbols(String cpf) {
        return cpf.replace(".", "").replace(".", "").replace("-", "").replace(" ", "");
    }

    private static Integer parseDigitToInteger(String digit) {
        return Integer.parseInt(digit);
    }

    private static Integer getFirstDigit(String cpf) {
        var firstDigit = 0;
        for (int numberCount = 1; numberCount < cpf.length() - 1; numberCount++) {
            var digit = parseDigitToInteger(cpf.substring(numberCount - 1, numberCount));
            firstDigit += (11 - numberCount) * digit;
        }
        return firstDigit;
    }

    private static Integer getSecondDigit(String cpf, int digitOneResult) {
        var secondDigit = 0;
        for (int numberCount = 1; numberCount < cpf.length() - 1; numberCount++) {
            var digit = parseDigitToInteger(cpf.substring(numberCount - 1, numberCount));
            secondDigit += (12 - numberCount) * digit;
        }
        return secondDigit + 2 * digitOneResult;
    }

    private static Integer calculateResultDigitOne(Integer firstDigit){
        var rest = (firstDigit % 11);
        return rest < 2 ?  0 : 11 - rest;
    }

    private static Integer calculateResultDigitTwo(Integer secondDigit){
        var rest = (secondDigit % 11);
        return rest < 2? 0 : 11 - rest;
    }

    private static String getVerifyingDigit(String cpf){
        return cpf.substring(cpf.length() - 2);
    }

    private static String getVerifyingDigitResult(Integer digitOneResult, Integer digitTwoResult){
        return String.format("%s%s", digitOneResult, digitTwoResult);
    }
}
