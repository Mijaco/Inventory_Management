package com.ibcs.desco.util;

import java.util.Random;

public class RandomNumber {

    public RandomNumber(){
        Construct();
    }

    public String generateRandom(){
       return randomID();

    }
    private char[] chars;
    private char[] numbers;
    private Random random;

    private void Construct(){
        numbers = new char[10];
        numbers[0]= '0';
        numbers[1]= '1';
        numbers[2]= '2';
        numbers[3]= '3';
        numbers[4]= '4';
        numbers[5]= '5';
        numbers[6]= '6';
        numbers[7]= '7';
        numbers[8]= '8';
        numbers[9]= '9';

        chars = new char[26];
        for (int i = 0; i < 26; i ++) {
            chars[i + 0] = (char) (65 + i);
        }
        random = new Random();
    }

    public String randomID () {
        return randomString (4) + randomNumber(6);
    }

    public String randomPassword () {
        return randomPasswordString (4) + randomNumber(6);
    }


    public String randomString (int length) {
        //length = 2;
        char[] array = new char[length];
        for (int i = 0; i < length; i ++) {
            array[i] = chars[random.nextInt (chars.length)];
        }
        return new String(array);
    }

    private String randomPasswordString (int length) {
        char[] passwordChars = new char[52];
        char[] array = new char[length];
        for (int i = 0; i < 26; i ++) {
            passwordChars[i + 26] = (char) (97 + i);
            passwordChars[i + 0] = (char) (65 + i);
        }
        for (int i = 0; i < length; i ++) {
            array[i] = passwordChars[random.nextInt (passwordChars.length)];
        }
        return new String(array);
    }
    private String randomNumber (int length) {
        char[] array = new char[length];
        for (int i = 0; i < length; i ++) {
            array[i] = numbers[random.nextInt(numbers.length)];
        }
        return new String(array);
    }
}
