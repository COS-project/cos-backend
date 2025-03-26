package com.cos.cercat.database.common.config;

import static org.hibernate.type.StandardBasicTypes.DOUBLE;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;

public class MySQLFunctionContributor implements FunctionContributor {

    private static final String FUNCTION_NAME = "match";
    private static final String FUNCTION_PATTERN = "match (?1, ?2) against (?3 in natural language mode)";

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
                .registerPattern(
                        FUNCTION_NAME,
                        FUNCTION_PATTERN,
                        functionContributions.getTypeConfiguration().getBasicTypeRegistry()
                                .resolve(DOUBLE)
                );
    }
}
