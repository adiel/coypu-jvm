package coypu.Finders;

import coypu.MissingHtmlException;
import coypu.Options;
import coypu.Queries.PredicateQuery;
import coypu.Robustness.RobustWrapper;
import coypu.State;

public class StateFinder {
    private RobustWrapper robustWrapper;

    public StateFinder(RobustWrapper robustWrapper) {
        this.robustWrapper = robustWrapper;
    }

    public State findState(Options options, final State[] states) {
        PredicateQuery query = new PredicateQuery(options) {
            @Override
            public Boolean predicate() {
                boolean was = robustWrapper.getZeroTimeout();
                robustWrapper.setZeroTimeout(true);
                try {
                    for (State state : states) {
                        if (state.checkCondition())
                            return true;
                    }
                    return false;
                } finally {
                    robustWrapper.setZeroTimeout(was);
                }
            }
        };

        robustWrapper.robustly(query);

        for (State state : states) {
            if (state.conditionWasMet())
                return state;
        }

        throw new MissingHtmlException("None of the given states was reached within the configured getTimeout.");

    }
}
