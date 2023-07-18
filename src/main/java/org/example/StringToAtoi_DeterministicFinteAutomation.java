package org.example;

public class StringToAtoi_DeterministicFinteAutomation {

    public static class StateMachine{
        enum State {q0,q1,q2, qd}
        private int result,sign ;
        private State currentState;


        public StateMachine(){
            this.result = 0;
             this.sign = 1;
             this.currentState = State.q0;

        }
        private  void  toStateQ1(char input){
            sign = ( input == '-' ) ? -1 : 1;
            currentState = State.q1;
        }

        private void toStateQ2(int digit){
            currentState = State.q2;
            appendDigit(digit);
        }
        private void toStateQd(){
            currentState = State.qd;
        }

        private void appendDigit(int digit) {

            if((result > Integer.MAX_VALUE/10 || (result == Integer.MAX_VALUE && digit > Integer.MAX_VALUE %10 ))){
                if(sign == 1){
                    result = Integer.MAX_VALUE;
                }else{
                    result = Integer.MIN_VALUE;
                    sign = 1;
                }
                toStateQd();
            }else{
                result = result * 10 + digit;
            }
        }

        public State getCurrentState(){
            return currentState;
        }
        public Integer getInteger(){
            return sign * result;
        }
        public  void transition( char input){
           if(currentState == State.q0){
               if( input == ' '){
                    return;
               } else if ( input == '+' || input == '-'){
                   toStateQ1(input);
                   currentState = State.q2;
               }else if (Character.isDigit(input) ){
                   toStateQ2(input);
               }else{
                   toStateQd();
               }
           }
           if(currentState == State.q1 || currentState == State.q2){
               if( Character.isDigit(input)){
                   toStateQ2(input);
               }else{
                   toStateQd();
               }
           }

        }


    }

    public static int solution(String input){
        StateMachine stateMachine = new StateMachine();
        for(int i = 0; i<input.length() && stateMachine.getCurrentState() != StateMachine.State.qd ; i++){
            stateMachine.transition(input.charAt(i));
        }
        return stateMachine.getInteger();
    }
    public static void main(String[] args){
        String input = "2147483648";
        System.out.println(solution(input));

    }
}
