package sample.util;

import sample.entity.GrammerExpression;

import java.util.ArrayList;

public class StringHandler {

    public static ArrayList<GrammerExpression> getExpressionFromInput(String input){
        ArrayList grammerExpressions = new ArrayList();
        String[] lines = input.split("\n");
        for(int i = 0; i<lines.length; i++){
            String[] expression = lines[i].split("::=");
            if(expression[1].contains("|")){
                String[] suffixs = expression[1].split("\\|");
                for(int j = 0; j<suffixs.length; j++){
                    GrammerExpression grammerExpression = new GrammerExpression();
                    grammerExpression.prefix = expression[0];
                    grammerExpression.suffix = suffixs[j];
                    grammerExpressions.add(grammerExpression);
                }
            }
            else {
                GrammerExpression grammerExpression = new GrammerExpression();
                grammerExpression.prefix = expression[0];
                grammerExpression.suffix = expression[1];
                grammerExpressions.add(grammerExpression);
            }
        }
        return grammerExpressions;
    }

    public static char getGrammerNameFromInput(String input){
        return input.charAt(0);
    }

    public static char getStartCharFromInput(String input){
        return input.charAt(2);
    }

    public static ArrayList<Character> getNonTerminalSymbolFromInput(String input){
        String[] nonTerminalSymbolArray = input.split(",");
        ArrayList nonTerminalSymbols = new ArrayList();
        for(int i = 0; i<nonTerminalSymbolArray.length; i++){
            nonTerminalSymbols.add(nonTerminalSymbolArray[i].charAt(0));
        }
        return nonTerminalSymbols;
    }

}
