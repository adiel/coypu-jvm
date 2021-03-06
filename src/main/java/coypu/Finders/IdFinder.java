package coypu.Finders;

import coypu.*;

public class IdFinder extends ElementFinder {
    public IdFinder(Driver driver, String locator, DriverScope scope) {
        super(driver, locator, scope);
    }

    public ElementFound find() {
        return Driver.findId(locator(), Scope);
    }
}
