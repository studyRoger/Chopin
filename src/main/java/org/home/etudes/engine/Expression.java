package org.home.etudes.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by roger on 1/10/15.
 */
public class Expression {
    private Operator operator;
    private Collection<Expression> expressions = new ArrayList<>();
    private Collection<String> conditionValues = new ArrayList<>();

    public Collection<Expression> getExpressions() {
        return Collections.unmodifiableCollection(expressions);
    }

    public void setExpression(Collection<Expression> expressions) {
        this.expressions = expressions;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Collection<String> getConditionValues() {
        return Collections.unmodifiableCollection(conditionValues);
    }

    public void setConditionValues(Collection<String> conditionValues) {
        this.conditionValues = conditionValues;
    }

    public void addConditionValue(String conditionValue) {
        this.conditionValues.add(conditionValue);
    }

    public void addConditionAllValue(Collection<String> conditionValues) {
        this.conditionValues.addAll(conditionValues);
    }

    public void addExpression(Expression expression) {
        this.expressions.add(expression);
    }

    public void addAllExpression(Collection<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    public String toString() {
        String plain = toPlainString(null);
        String json = toJson();
        return plain + "\n" + json;
        //return plain;
    }

    public String toPlainString(Operator outerOperator) {
        boolean needBracket = false;
        if(outerOperator!= null) {
             needBracket = outerOperator.getPriority() >operator.getPriority();
        }

        StringBuilder output = new StringBuilder();
        Iterator<Expression> itExpression = expressions.iterator();
        if(needBracket) {
            output.append("(");
        }
        if(itExpression.hasNext()) {
            output.append(itExpression.next().toPlainString(operator));
        }
        while(itExpression.hasNext()) {
            output.append(" " + operator.getText() +" ");
            output.append(itExpression.next().toPlainString(operator));
        }
        Iterator<String> itCondition = conditionValues.iterator();
        if(itCondition.hasNext()) {

            output.append(itCondition.next());
        }
        while (itCondition.hasNext()) {
            output.append(" " + operator.getText() +" ");
            output.append(itCondition.next());
        }

        if(needBracket) {
            output.append(")");
        }

        return output.toString();
    }

    public String toJson() {
        Gson output = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        return output.toJson(this);

    }
}
