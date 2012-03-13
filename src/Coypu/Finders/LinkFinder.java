namespace Coypu.Finders
{
    internal class LinkFinder : ElementFinder
    {
        internal LinkFinder(Driver driver, string locator, DriverScope scope) : base(driver, locator, scope) { }

        internal override ElementFound Find()
        {
            return Driver.FindLink(Locator, Scope);
        }
    }
}