package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import sample.entity.ChomskyGrammer;
import sample.util.Parser;
import sample.util.StringHandler;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UIController implements Initializable {
    @FXML
    private TextField grammerName;
    @FXML
    private TextField nonTerminalSymbol;
    @FXML
    private TextArea grammerExpression;
    @FXML
    private TextArea output;
    @FXML
    private Label grammerNameFalse;
    @FXML
    private Label nonTerminalFalse;
    @FXML
    private Label grammerExpressionFalse;

    @FXML
    private void onJudgeButtonClicked(ActionEvent event){
        if(grammerName.getText().equals("") || nonTerminalSymbol.getText().equals("") || grammerExpression.getText().equals("")){
            return;
        }
        if(!isAllLegal()){
            return;
        }
        ChomskyGrammer chomskyGrammer = new ChomskyGrammer();
        chomskyGrammer.setGrammerName(StringHandler.getGrammerNameFromInput(grammerName.getText()));
        chomskyGrammer.setStartChar(StringHandler.getStartCharFromInput(grammerName.getText()));
        chomskyGrammer.setGrammerExpressions(StringHandler.getExpressionFromInput(grammerExpression.getText()));
        chomskyGrammer.setNonTerminalSymbols(StringHandler.getNonTerminalSymbolFromInput(nonTerminalSymbol.getText()));
        chomskyGrammer.setTerminalSymbols();
        Parser.judgeGrammerClassification(chomskyGrammer);
        output.setText(chomskyGrammer.toString());
        System.out.println(chomskyGrammer);
    }

    @FXML
    private void onCopyButtonClicked(ActionEvent event){
        String input = null;
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null) {
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    input = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String[] lines = input.split("\n");
        grammerName.setText(lines[0]);
        nonTerminalSymbol.setText(lines[1]);
        StringBuilder expressions = new StringBuilder();
        for(int i = 2; i<lines.length; i++){
            expressions.append(lines[i]);
            expressions.append("\n");
        }
        grammerExpression.setText(expressions.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        grammerNameFalse.setVisible(false);
        nonTerminalFalse.setVisible(false);
        grammerExpressionFalse.setVisible(false);
        //label.setManaged(false);
        grammerName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                    ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if(!newValue){//if they loose focus
                    if(!isGrammerNameLegal()){
                        grammerNameFalse.setVisible(true);
                    }
                    //if they loose focus attach them to lastFocusedNode
                }
                else {
                    grammerNameFalse.setVisible(false);
                }
            }
        });
        nonTerminalSymbol.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                    ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if(!newValue){//if they loose focus
                    if(!isNonTerminalSymbolLegal()){
                        nonTerminalFalse.setVisible(true);
                    }

                    //if they loose focus attach them to lastFocusedNode
                }
                else {
                    nonTerminalFalse.setVisible(false);
                }
            }
        });
        grammerExpression.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                    ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if(!newValue){//if they loose focus
                    if(!isGrammerExpressionLegal()){
                        grammerExpressionFalse.setVisible(true);
                    }
                    //if they loose focus attach them to lastFocusedNode
                }
                else {
                    grammerExpressionFalse.setVisible(false);
                }
            }
        });
    }

    private boolean isGrammerNameLegal(){
        if(grammerName.getText().equals("")){
            return true;
        }
        String regEx = "[A-Z]\\[[A-Z]\\]";
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(grammerName.getText());
        return matcher.matches();
    }

    private boolean isNonTerminalSymbolLegal(){
        if(nonTerminalSymbol.getText().equals("")){
            return true;
        }
        String regEx = ".(,.)*";
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nonTerminalSymbol.getText());
        return matcher.matches();
    }

    private boolean isGrammerExpressionLegal(){
        if(grammerExpression.getText().equals("")){
            return true;
        }
        String[] expressions = grammerExpression.getText().split("\n");
        String regEx1 = "[^\\|]+::=[^\\|]+";
        String regEx2 = "[^\\|]+::=[^\\|]+((\\|)([^\\|])+)*";
        Pattern pattern1 = Pattern.compile(regEx1);
        Pattern pattern2 = Pattern.compile(regEx2);
        for(int i = 0; i<expressions.length; i++){
            if(!expressions[i].equals("") && (!pattern1.matcher(expressions[i]).matches() && !pattern2.matcher(expressions[i]).matches())){
                return false;
            }
        }
        return true;
    }

    private boolean isAllLegal(){
        if(!isGrammerNameLegal()){
            grammerNameFalse.setVisible(true);
        }
        if(!isGrammerExpressionLegal()){
            grammerExpressionFalse.setVisible(true);
        }
        if(!isNonTerminalSymbolLegal()){
            nonTerminalFalse.setVisible(true);
        }
        if(isGrammerExpressionLegal() && isGrammerNameLegal() && isNonTerminalSymbolLegal()){
            return true;
        }
        return false;
    }

}
