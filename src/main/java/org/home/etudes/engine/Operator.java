package org.home.etudes.engine;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roger on 1/10/15.
 */
public class Operator extends ExpressionElement {

    // the pattern to parse the accepted expression. == != @@ !@ || && ( )
    // it is used to split the operator and operate variable
    public static final String KEY_WORD_PATTERN = "in\\s*\\([^\\)]*\\)|not\\s*in\\s*\\([^\\)]*\\)|\\=\\=|\\!\\=|\\|\\||\\&\\&|\\(|\\)";

    public static final Operator EQ = new Operator("==", 10);
    public static final Operator NOT_EQ = new Operator("!=", 10);
    public static final Operator IN = new Operator("IN", 10);
    public static final Operator NOT_IN = new Operator("NOT_IN", 10);
    public static final Operator AND = new Operator("&&", 5);
    public static final Operator OR = new Operator("||", 4);
    public static final Operator LEFT_BRACKET = new Operator("(", -1);
    public static final Operator RIGHT_BRACKET = new Operator(")", -1);

    public static Operator getOperator(String operatorString) {
        switch (operatorString) {
            case "==":
                return Operator.EQ;
            case "!=":
                return Operator.NOT_EQ;
            case "&&":
                return Operator.AND;
            case "||":
                return Operator.OR;
            case "(":
                return Operator.LEFT_BRACKET;
            case ")":
                return Operator.RIGHT_BRACKET;
            default:
                Operator op = parseOperatorIn(operatorString);
                if(op != null) {
                    return op;
                } else {
                    throw new RuntimeException("unknown operator");
                }
        }
    }

    public static String parseOperatorInArgs(String line) {
        Matcher matcher = Pattern.compile("\\(.*\\)").matcher(line);
        matcher.find();
        return line.substring(matcher.start()+1, matcher.end()-1).trim();

    }

    private static Operator parseOperatorIn(String operatorString) {
        if(operatorString.matches("not\\s*in\\s*(.*)")){
            return Operator.NOT_IN;
        } else if(operatorString.matches("\\s*in\\s*(.*)")) {
            return Operator.IN;
        } else {
            return null;
        }
    }

    private int priority;

    public Operator(String text, int priority) {
        super(text);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String printWithArgs(Collection<String> args) {
        
    }
}
