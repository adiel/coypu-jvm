package Coypu.Queries;

import Coypu.*;

public class HasNoXPathQuery extends DriverScopeQuery<Boolean>
{
    private final Driver driver;
    private final String xpath;
    public Object ExpectedResult(){ return true; }


    public HasNoXPathQuery(Driver driver, DriverScope scope, String xpath, Options options)
    {
        super(scope, options);

        this.driver = driver;
        this.xpath = xpath;
    }

    public void Run() throws MissingHtmlException, TimeoutException {
        SetResult(!driver.HasXPath(xpath, DriverScope()));
    }
}
