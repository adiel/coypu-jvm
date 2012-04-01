package Coypu;

import Coypu.Queries.PredicateQuery;

///<summary>
/// A possible state for the current page
///</summary>
public abstract class State extends PredicateQuery {
    private boolean conditionWasMet;

    public State() {}
    public State(Options options) {
        super(options);
    }

    public boolean conditionWasMet() {
        return conditionWasMet;
    }

    public boolean checkCondition() {
        return conditionWasMet = this.predicate();
    }

}
