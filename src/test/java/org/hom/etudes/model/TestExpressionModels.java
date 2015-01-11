package org.hom.etudes.model;


import org.home.etudes.engine.Expression;
import org.junit.Test;

/**
 * Created by roger on 1/10/15.
 */
public class TestExpressionModels {

    @Test
    public void testExpression() {
        Expression c1 = new Expression();
        c1.addConditionValue("c1.l");
        c1.addConditionValue("c1.r");
        c1.setOperator("==");

        Expression c2 = new Expression();
        c2.addConditionValue("c2.l");
        c2.addConditionValue("c2.r");
        c2.setOperator("!=");

        Expression c3 = new Expression();
        c3.addConditionValue("c3.l");
        c3.addConditionValue("c3.r");
        c3.setOperator("@@");

        Expression e1 = new Expression();
        e1.setOperator("||");
        e1.addExpression(c1);
        e1.addExpression(c2);

        Expression e2 = new Expression();
        e2.setOperator("&&");
        e2.addExpression(e1);
        e2.addExpression(c3);

        System.out.println(e2);


    }
}
