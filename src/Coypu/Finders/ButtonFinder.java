package Coypu.Finders;

import Coypu.*;

public class ButtonFinder extends ElementFinder
{
    public ButtonFinder(Driver driver, String locator, DriverScope scope)
    {
        super(driver, locator, scope);
    }

    public ElementFound Find() throws MissingHtmlException, TimeoutException {
        return Driver.FindButton(Locator(), Scope);
    }
}