package org.home.etudes.engine;

import org.junit.Test;

import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roger on 1/10/15.
 */
public class TestEngine {


    @Test
    public void testParser() throws Exception {
        String line = "(aa==bb||cc!=dd)&&ee@@ff&&gg!@hh";
        System.out.println(line);
        Queue<ExpressionElement> npeElementQueue = Parser.parseRawToNPE(line);
        System.out.println(Parser.outputQueueToString(npeElementQueue));

        Expression expression = Parser.parseNPEtoExpression(npeElementQueue);
        System.out.println(expression);
    }



    @Test
    public void testRegex() {
        String raw = "aa||bb&&cc";
        Pattern pattern = Pattern.compile("\\|\\||\\&\\&");
        Matcher matcher = pattern.matcher(raw);
        int previousEnd = 0;
        while(matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String opString = raw.substring(start, end);
            String varString = raw.substring(previousEnd, start);

            System.out.println(varString+ " " + opString);

            previousEnd = end;
        }
    }
}
