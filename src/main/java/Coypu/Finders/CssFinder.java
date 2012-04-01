package Coypu.Finders;

import Coypu.*;

public class CssFinder extends ElementFinder {
    public CssFinder(Driver driver, String locator, DriverScope scope) {
        super(driver, locator, scope);
    }

    public ElementFound find() {
        return Driver.findCss(locator(), Scope);
    }
}
