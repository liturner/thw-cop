package de.turnertech.ows.filter;

public class BinaryComparisonOperator extends ComparisonOperator {

    private BinaryComparisonName operatorType;

    private final Expression[] expression;

    private boolean matchCase;

    private MatchAction matchAction;

    public BinaryComparisonOperator() {
        this.expression = new Expression[2];
        this.matchCase = true;
        this.matchAction = MatchAction.ANY;
    }

    @Override
    public boolean getAsBoolean() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAsBoolean'");
    }
    
}
