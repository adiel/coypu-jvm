package Coypu.Queries;

import Coypu.*;

import java.util.regex.Pattern;

public class HasNoContentMatchQuery extends DriverScopeQuery<Boolean>
{
    private final Driver driver;
    private final Pattern text;
    public Object ExpectedResult() { return true; }

    public HasNoContentMatchQuery(Driver driver, DriverScope scope, Pattern text, Options options)
    {
        super(scope, options);
        this.driver = driver;
        this.text = text;
    }

    public void Run() throws MissingHtmlException, TimeoutException {
        SetResult(!driver.HasContentMatch(text, DriverScope()));
    }    
}