package Coypu.Finders;

import Coypu.*;

public class XPathFinder extends ElementFinder
{
    public XPathFinder(Driver driver, String locator, DriverScope scope)
    {
        super(driver, locator, scope);
    }

    public ElementFound Find() throws MissingHtmlException, TimeoutException {
        return Driver.FindXPath(Locator(), Scope);
    }
}