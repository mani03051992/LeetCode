package org.example;
/*

If N is the number of characters in the input string.

Time complexity: O(N)

We visit each character in the input at most once and for each character we spend a constant amount of time.

Space complexity: O(1)

We have used only constant space to store the sign and the result.
 */
public class StringToAtoi {

    public static  int myAtoi(String input){
        int sign = 1;
        int result = 0;
        boolean signAlreadyEncountered = false;
        int index = 0;

        while(index < input.length() && input.charAt(index) == ' '){
            index++;
        }
        if(input.charAt(index) == '-'){
            sign = sign * -1;
            index++;
        }else if(input.charAt(index) == '+'){
            index++;
        }


        for(; index<input.length();index++){

            if(!Character.isDigit(input.charAt(index))){
                break;
            }
            if(result > Integer.MAX_VALUE/10 || ( result == Integer.MAX_VALUE/10 && Character.getNumericValue(input.charAt(index)) > Integer.MAX_VALUE % 10 ) ){
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + Character.getNumericValue(input.charAt(index));
        }
        return sign * result;
    }




    public static void main(String[] Args){

        System.out.println(myAtoi("2147483648"));
    }
}

 /*   How to check overflows/underflows?

        If we were using a long, BigInteger, or any other numeric data types to store the integers, we could check if the integer exceeds the 32-bit range, stop building the output number, and return the clamped value.

        num = num * 10 + digit
        if num > 2^31 - 1 then return 2^31 - 1
        else if num < -2^31 then return -2^31
        However, here we will assume our environment doesn't allow us to use these data types which could be a constraint imposed by the interviewer. But we can't directly use a 32-bit integer to store the final result.

        For example, assume currently result is 1000000000 and digit is 1, we can't append the current digit to result as 10000000001 is more than 231−12^{31} - 12
        31
        −1. So, performing the result = result * 10 + digit operation will result in Runtime Error.

        Hence, first we need to check if appending the digit to the result is safe or not. If it is safe to append then update the result. Otherwise, handle the overflow/underflow.

        Let's first consider the case for overflow.

        We will denote the maximum 32-bit integer value 231−1 = 21474836472^{31} - 1 \space = \space 21474836472
        31
        −1 = 2147483647 with INT_MAX, and we will append the digits one by one to the final number.

        So there could be 3 cases:

        Case 1: If the current number is less than INT_MAX / 10 = 214748364, we can append any digit, and the new number will always be less than INT_MAX.
        '214748363' (less than INT_MAX / 10) + '0' = '2147483630' (less than INT_MAX)
        '214748363' (less than INT_MAX / 10) + '9' = '2147483639' (less than INT_MAX)
        '1' (less than INT_MAX / 10) + '9' = '19' (less than INT_MAX)
        Case 2: If the current number is more than INT_MAX / 10 = 214748364, appending any digit will result in a number greater than INT_MAX.
        '214748365' + '0' = '2147483650' (more than INT_MAX)
        '214748365' + '9' = '2147483659' (more than INT_MAX)
        '2147483646' + '8' = '21474836468' (more than INT_MAX)
        Case 3: If the current number is equal to INT_MAX / 10 = 214748364, we can only append digits from 0-7 such that the new number will always be less than or equal to INT_MAX.
        '214748364' + '0' = '2147483640' (which is less than INT_MAX)
        '214748364' + '7' = '2147483647' (which is equal to INT_MAX)
        '214748364' + '8' = '2147483648' (which is more than INT_MAX)

        Similarly for underflow.
        The minimum 32-bit integer value is −231 = −2147483648-2^{31} \space = \space -2147483648−2
        31
        = −2147483648 denote it with INT_MIN.


        We append the digits one by one to the final number.
        Just like before, there could be 3 cases:

        Case 1: If the current number is greater than INT_MIN / 10 = -214748364, then we can append any digit and the new number will always be greater than INT_MIN.

        Case 2: If the current number is less than INT_MIN / 10 = -214748364, appending any digit will result in a number less than INT_MIN.

        Case 3: If the current number is equal to INT_MIN / 10 = -214748364, then we can only append digits from 0-8, such that the new number will always be greater than or equal to INT_MIN.
        Notice that cases 1 and 2 are similar for overflow and underflow. The only difference is case 3: for overflow, we can append any digit between 0 and 7, but for underflow, we can append any digit between 0 and 8.

        So we can combine both the underflow and overflow checks as follows:

        Initially, store the sign for the final result and consider only the absolute values to build the integer and return the final result with a correct sign at the end.
        If the current number is less than 214748364 = (INT_MAX / 10), we can append the next digit.
        If the current number is greater than 214748364:
        And, the sign for the result is '+', then the result will overflow, so return INT_MAX;
        Or, the sign for the result is '-', then the result will underflow, so return INT_MIN.
        If the current number is equal to 214748364:
        If the next digit is between 0-7, the result will always be in range.
        If, next digit is 8:
        and the sign is '+' the result will overflow, so return INT_MAX;
        if the sign is '-', the result will not underflow but will still be equal to INT_MIN, so that we can return INT_MIN.
        But if, the next digit is greater than 8:
        and the sign is '+' the result will overflow, so return INT_MAX;
        if the sign is '-', the result will underflow, so return INT_MIN.
        Note: We do not need to handle 0-7 for positive and 0-8 for negative integers separately. If the sign is negative and the current number is 214748364, then appending the digit 8, which is more than 7, will also lead to the same result, i.e., INT_MIN.

        */
