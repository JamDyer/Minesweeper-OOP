package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPlayer {

    @Test
    public void isValidReturnsTrueForNonFlagInput(){
        final var input = "42";
        final var result = Player.isValid(input);
        Assertions.assertTrue(result,
                "Player: isValid should return True for correct non-flag input");
    }

     @Test
    public void isValidReturnsTrueForFlagInput(){
        final var input = "42f";
        final var result = Player.isValid(input);
        Assertions.assertTrue(result,
                "Player: isValid should return True for correct flag input");
    }

    @Test
    public void isValidReturnsFalseForNoInput(){
        final var input = " ";
        final var result = Player.isValid(input);
        Assertions.assertFalse(result,
                "Player: isValid should return False for no input");
    }

    @Test
    public void isValidReturnsFalseForInvalidInputFormat(){
        final var input = "4f";
        final var result = Player.isValid(input);
        Assertions.assertFalse(result,
                "Player: isValid should return False for invalid input format");
    }
    @Test
    public void isValidReturnsTrueForWrongSymbolInput(){
        final var input = "{]$>";
        final var result = Player.isValid(input);
        Assertions.assertFalse(result,
                "Player: isValid should return True for valid large input");
    }

    // base tests

    @Test
    public void isBase36ReturnsTrueForValidDigit(){
        final var test = 'A';
        final var result = Player.isBase36(test);
        Assertions.assertTrue(result,
                "Player: isBase36 should return True for valid digit");
    }

    @Test
    public void isBase36ReturnsFalseForInvalidDigit(){
        final var test = '!';
        final var result = Player.isBase36(test);
        Assertions.assertFalse(result,
                "Player: isBase36 should return False for invalid digit");
    }

    @Test
    public void fromBase36ReturnsCorrectValueForDigit(){
        final var num = 'A';
        final var expected = 10;
        final var result = Player.fromBase36(num);
        Assertions.assertEquals(expected, result,
                "Player: fromBase36 should return correct value for digit");
    }

    @Test
    public void toBase36ReturnsCorrectCharacterForValue(){
        final var num = 10;
        final var expected = 'A';
        final var result = Player.toBase36(num);
        Assertions.assertEquals(expected, result,
                "Player: toBase36 should return correct character for value");
    }
}
