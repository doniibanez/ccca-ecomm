package br.com.doni.ecomm.models;

import java.util.Arrays;

public class CPF {
    private String cpf;
    private static int CPF_LENGTH = 11;
    private static int CPF_FACTOR_FIRST_DIGIT = 10;
    private static int CPF_FACTOR_SECOND_DIGIT = 11;

    public CPF(String cpf) {
        this.cpf = cpf;
        if(!validate()) throw new IllegalArgumentException("CPF is invalid");
    }

    private boolean validate() {
        cpf = removeSymbols();
        if (!hasValidLength() || isBlockedCPF()) return false;
        var digitOneResult = calculateDigit(CPF_FACTOR_FIRST_DIGIT);
        var digitTwoResult = calculateDigit(CPF_FACTOR_SECOND_DIGIT);
        return getVerifyingDigit().equals(getVerifyingDigitResult(digitOneResult, digitTwoResult));
    }

    private String removeSymbols() {
        return cpf.replaceAll("\\p{Punct}", "");
    }

    private boolean hasValidLength() {
        return cpf.length() == CPF_LENGTH;
    }

    private boolean isBlockedCPF() {
        String firstDigit = cpf.substring(0, 1);
        return Arrays.stream(cpf.split("")).allMatch(digit -> digit.compareTo(firstDigit) == 0);
    }

    private Integer calculateDigit(int factor) {
        var total = 0;
        for (char digit : cpf.toCharArray()) {
            if (factor > 1) total += Character.getNumericValue(digit) * factor--;
        }
        var rest = total % 11;
        return (rest < 2) ? 0 : 11 - rest;
    }

    private String getVerifyingDigit() {
        return cpf.substring(9);
    }

    private String getVerifyingDigitResult(Integer digitOneResult, Integer digitTwoResult) {
        return String.format("%s%s", digitOneResult, digitTwoResult);
    }
}
