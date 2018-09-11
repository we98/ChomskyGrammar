package sample.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChomskyGrammer {

    private char grammerName;
    private char startChar;

    public void setNonTerminalSymbols(ArrayList<Character> nonTerminalSymbols) {
        this.nonTerminalSymbols = nonTerminalSymbols;
    }

    public void setTerminalSymbols() {
        terminalSymbols = new ArrayList<>();
        Set allSymbols = getAllSymbols();
        for(Object o : allSymbols){
            if(!nonTerminalSymbols.contains(o) && !o.equals('ε')){
                terminalSymbols.add((Character) o);
            }
        }
    }

    public void setGrammerExpressions(ArrayList<GrammerExpression> grammerExpressions) {
        this.grammerExpressions = grammerExpressions;
    }

    private ArrayList<Character> nonTerminalSymbols;
    private ArrayList<Character> terminalSymbols;
    private ArrayList<GrammerExpression> grammerExpressions;

    public char getGrammerName() {
        return grammerName;
    }

    public char getStartChar() {
        return startChar;
    }

    public ArrayList<Character> getNonTerminalSymbols() {
        return nonTerminalSymbols;
    }

    public ArrayList<GrammerExpression> getGrammerExpressions() {
        return grammerExpressions;
    }

    public int getGrammerClassification() {
        return grammerClassification;
    }

    public void setGrammerName(char grammerName) {
        this.grammerName = grammerName;
    }

    public void setStartChar(char startChar) {
        this.startChar = startChar;
    }

    public void setGrammerClassification(int grammerClassification) {
        this.grammerClassification = grammerClassification;
    }

    private int grammerClassification;

    private Set getAllSymbols(){
        Set allSymbols = new HashSet<Character>();
        for(GrammerExpression g : grammerExpressions){
            for(int i = 0; i<g.prefix.length(); i++){
                allSymbols.add(g.prefix.charAt(i));
            }
            for(int i = 0; i<g.suffix.length(); i++){
                allSymbols.add(g.suffix.charAt(i));
            }
        }
        return allSymbols;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        StringBuilder nonTerminals = new StringBuilder();
        nonTerminals.append("{");
        for(int i = 0; i<nonTerminalSymbols.size(); i++){
            nonTerminals.append(nonTerminalSymbols.get(i));
            if(i == nonTerminalSymbols.size() - 1){
                nonTerminals.append("}");
            }
            else {
                nonTerminals.append(",");
            }
        }
        StringBuilder terminals = new StringBuilder();
        terminals.append("{");
        for(int i = 0; i<terminalSymbols.size(); i++){
            terminals.append(terminalSymbols.get(i));
            if(i == terminalSymbols.size() - 1){
                terminals.append("}");
            }
            else {
                terminals.append(",");
            }
        }
        s.append(grammerName);
        s.append("[" + startChar + "]=(");
        s.append(nonTerminals + ",");
        s.append(terminals + ",P," + startChar + ")\nP:\n");
        for(int i = 0; i<grammerExpressions.size(); i++){
            s.append(grammerExpressions.get(i).prefix + "::=" + grammerExpressions.get(i).suffix + "\n");
        }
        switch (grammerClassification){
            case 0:
            case 1:
            case 2:
            case 3:
                s.append("该文法是" + grammerClassification + "型文法");
                break;
            case 4:
                s.append("该文法是扩充的2型文法");
                break;
            case 5:
                s.append("该文法是扩充的3型文法");
                break;
            default:
                break;
        }
        return s.toString();
    }
}
