package sample.util;

import sample.entity.ChomskyGrammer;
import sample.entity.GrammerExpression;


public class Parser {

    public static void judgeGrammerClassification(final ChomskyGrammer chomskyGrammer){
        boolean isOne = false;
        boolean isTwo = false;
        boolean isExpand = false;
        for(GrammerExpression g : chomskyGrammer.getGrammerExpressions()){
            if(g.prefix.length() > 1){
                if(g.suffix.length() < g.prefix.length() || g.suffix.equals("ε")){
                    chomskyGrammer.setGrammerClassification(0);
                    return;
                }
                else {
                    isOne = true;
                }
            }
            else {
                if(g.suffix.equals("ε")){
                    isExpand = true;
                }
                else if(!isTheSuffixOfThree(chomskyGrammer, g.suffix)){
                    isTwo = true;
                }
            }
        }
        if(isOne){
            chomskyGrammer.setGrammerClassification(1);
        }
        else if(isTwo & isExpand){
            chomskyGrammer.setGrammerClassification(4);
        }
        else if(isTwo){
            chomskyGrammer.setGrammerClassification(2);
        }
        else if(isExpand){
            chomskyGrammer.setGrammerClassification(5);
        }
        else {
            chomskyGrammer.setGrammerClassification(3);
        }
    }

    private static boolean isTheSuffixOfThree(final ChomskyGrammer chomskyGrammer, final String suffix){
        if(suffix.length() == 1 && suffix.charAt(0) != 'ε' && !chomskyGrammer.getNonTerminalSymbols().contains(suffix.charAt(0))){
            return true;
        }
        else if(suffix.length() == 2 && !chomskyGrammer.getNonTerminalSymbols().contains(suffix.charAt(0)) && chomskyGrammer.getNonTerminalSymbols().contains(suffix.charAt(1))){
            return true;
        }
        else {
            return false;
        }
    }

}
