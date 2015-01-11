package org.home.etudes.engine;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roger on 1/10/15.
 */
public class Parser {

    public static Expression parse(String rawExpression) throws Exception {

        Queue<ExpressionElement> npeElementQueue = parseRawToNPE(rawExpression);

        Expression rootExpression = parseNPEtoExpression(npeElementQueue);

        return rootExpression;


    }

    protected static Queue<ExpressionElement> parseRawToNPE(String rawExpression) throws Exception {
        Queue<ExpressionElement> outputQueue = new LinkedList<>();
        Deque<Operator> operatorStack = new LinkedList<>();

        Matcher matcher = Pattern.compile(Operator.KEY_WORD_PATTERN).matcher(rawExpression);
        //int previousStart = 0;
        int previousEnd = 0;
        while(matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            //handle the operate value before the current operator(if there is)
            String valString = rawExpression.substring(previousEnd, start);
            if(valString.length() > 0) {
                Value value = createValue(valString);
                outputQueue.add(value);
            }

            //handle the matched operator
            String opString = rawExpression.substring(start, end);
            Operator operator = Operator.getOperator(opString);
            if(operator.getText().equals("(")) {
                operatorStack.push(operator);
            } else if (operator.getText().equals(")")) {
                boolean bracketInPair = false;
                while(!operatorStack.isEmpty()) {
                    Operator opOnTop = operatorStack.pollFirst();
                    if(opOnTop.getText().equals("(")) {
                        bracketInPair = true;
                    } else {
                        outputQueue.add(opOnTop);
                    }
                }
                if(!bracketInPair) {
                    throw new Exception("unclosed bracket");
                }
            } else {
                while(!operatorStack.isEmpty() && !operatorStack.peekFirst().getText().equals("(") && operator.getPriority() <= operatorStack.peekFirst().getPriority()) {
                    outputQueue.add(operatorStack.poll());
                }
                operatorStack.push(operator);
            }

            previousEnd = end;
        }

        // there can be a value after the last operator
        if(previousEnd < rawExpression.length()) {
            String valString = rawExpression.substring(previousEnd);
            Value value = createValue(valString);
            outputQueue.add(value);
        }

        Operator opOnTop = operatorStack.peekFirst();
        if(opOnTop!=null && (opOnTop.getText().equals("(") || opOnTop.getText().equals(")"))) {
            throw new Exception("unclosed bracket");
        }

        while(!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pollFirst());
        }

        return outputQueue;
    }

    protected static Expression parseNPEtoExpression(Queue<ExpressionElement> npeElementQueue) throws Exception {
        List<Expression> expressionStack = new LinkedList<>();
        List<String> valueStack = new LinkedList<>();

        while(!npeElementQueue.isEmpty()) {
            ExpressionElement ee = npeElementQueue.poll();

            if(ee instanceof Value) {
                valueStack.add(ee.getText());
            } else if(ee instanceof Operator) {
                Expression expression = new Expression();
                if(ee.getText().equals("&&") || ee.getText().equals("||")) {
                    expression.addAllExpression(expressionStack);
                    expressionStack.clear();
                } else {
                    expression.addConditionAllValue(valueStack);
                    valueStack.clear();
                }
                expression.setOperator(ee.getText());
                expressionStack.add(expression);
            } else {
                throw new Exception("misuse of operator");
            }
        }

        if(expressionStack.size() != 1 || valueStack.size() != 0) {
            throw new Exception("misuse of operator");
        }
        Expression root = expressionStack.get(0);

        return root;
    }

    protected static String outputQueueToString(Queue outputQueue) {
        StringBuilder sb = new StringBuilder();
        Iterator<ExpressionElement> it = outputQueue.iterator();
        while(it.hasNext()) {
            sb.append(it.next().getText() + ",");

        }
        return sb.toString();
    }

    private static Value createValue(String valueString) {
        if(valueString==null || valueString.length()==0) {
            throw new NullPointerException("empty string");
        } else {
            return new Value(valueString);
        }

    }

}
