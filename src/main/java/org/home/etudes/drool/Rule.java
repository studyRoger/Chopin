package org.home.etudes.drool;

import org.home.etudes.engine.Expression;

import java.util.List;

/**
 * Created by roger on 1/11/15.
 */
public class Rule {

    private static final String DEFAULT_PACKAGE = "org.home.etudes";
    private static final String RULE_TEMPLATE =
            "package %s; \n" +
            "%s" +
            "rule \"%s\"\n" +
            "  when\n" +
            "    %s\n" +
            "  then\n" +
            "    %s\n" +
            "end";

    private String name;
    private int priority;
    private String packageName = DEFAULT_PACKAGE;
    private List<String> imports;
    private String when;
    private String then;

    public String createDroolRule() {
        String droolRule = String.format(RULE_TEMPLATE, name, when, then);
        return droolRule;
    }
}
