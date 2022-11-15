package lotto.check;

import lotto.Game;
import lotto.exception.Exception;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Validator {
    public static final int UNIT = 1000;
    public static final int LENGTH = 6;
    public static final String COMMA = ",";
    public static final String ONLY_NUMBER = "^[0-9]*$";
    public static final String EXCEPT_TEXT = "^[0-9A-Za-z,]*$";
    public static void validateCash(String cash){
        isInputNumber(cash);
        isCashUnit(cash);
    }
    public static void validateBonusNumber(String bonus){
        isInputNumber(bonus);
        isDuplicateBonusNumber(bonus);
    }
    public static void validateLuckyNumber(String luckyNumber){
        // 당첨번호가 ,가 아닌 다른 구분자가 있는지
        isSpliterComma(luckyNumber);
        // 당첨번호가 6개인지
        isLength(luckyNumber);
        // 구분한 입력값들이 숫자가 맞는지
        isSplitNumber(luckyNumber);
        // 1~45 숫자 범위내에 있는지
        isRangeNumber(luckyNumber);
        // 숫자가 중복되는지
        isDuplicateLuckyNumber(luckyNumber);
    }
    public static void isLength(String luckyNumber){
        String[] splitNumber = luckyNumber.split(COMMA);
        if(splitNumber.length != LENGTH){
            throw new IllegalArgumentException(Exception.NOT_SIX_NUMBER.getExceptionMessage());
        }
    }
    public static void isDuplicateLuckyNumber(String luckyNumber){
        String[] splitNumber = luckyNumber.split(COMMA);
        Stream<String> lucky = Arrays.stream(splitNumber);
        Set<String> luckyDuplicate = new HashSet<>();
        lucky.filter( n -> !luckyDuplicate.add(n))
                .collect(Collectors.toSet());
        if(luckyDuplicate.size() != LENGTH){
            throw new IllegalArgumentException(Exception.DUPLICATE_NUMBER.getExceptionMessage());
        }
    }
    public static void isRangeNumber(String luckyNumber){
        String[] splitNumber = luckyNumber.split(COMMA);
        for(String number : splitNumber){
            if(Integer.parseInt(number) < 1 || Integer.parseInt(number) > 45){
                throw new IllegalArgumentException(Exception.WRONG_RANGE.getExceptionMessage());
            }
        }
    }
    public static void isSplitNumber(String luckyNumber){
        String[] splitNumber = luckyNumber.split(COMMA);
        for(String number : splitNumber){
            if(!number.matches(ONLY_NUMBER)){
                throw new NumberFormatException(Exception.NOT_NUMBER.getExceptionMessage());
            }
        }
    }
    public static void isSpliterComma(String luckyNumber){
        if(!luckyNumber.matches(EXCEPT_TEXT)){
            throw new IllegalArgumentException(Exception.NOT_COMMA_INPUT.getExceptionMessage());
        }
    }

    public static void isDuplicateBonusNumber(String bonus){
        if(Game.luckyNumber.contains(Integer.parseInt(bonus))){
            throw new IllegalArgumentException(Exception.DUPLICATE_NUMBER.getExceptionMessage());
        }
    }
    public static void isCashUnit(String cash){
        if(Integer.parseInt(cash) % UNIT > 0){
            throw new IllegalArgumentException(Exception.WRONG_UNIT.getExceptionMessage());
        }
    }
    public static void isInputNumber(String cash){
        if(!cash.matches("[+-]?\\d*(\\.\\d+)?")){
            throw new IllegalArgumentException(Exception.NOT_NUMBER.getExceptionMessage());
        }
    }

}
