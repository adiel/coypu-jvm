package coypu;

import coypu.Actions.*;
import coypu.Finders.*;
import coypu.Queries.*;
import coypu.Robustness.RobustWrapper;
import coypu.Robustness.Waiter;

import java.util.List;
import java.util.regex.Pattern;

public class DriverScope implements coypu.Scope {
    protected SessionConfiguration sessionConfiguration;
    private ElementFinder elementFinder;
    protected DriverScope outerScope;
    protected Driver driver;
    protected RobustWrapper robustWrapper;
    protected Waiter waiter;
    protected UrlBuilder urlBuilder;
    protected StateFinder stateFinder;
    private ElementFound element;
    private Options options;

    public DriverScope(SessionConfiguration sessionConfiguration, ElementFinder elementFinder, Driver driver, RobustWrapper robustWrapper, Waiter waiter, UrlBuilder urlBuilder) {
        this.elementFinder = elementFinder == null ? new DocumentElementFinder(driver) : elementFinder;
        this.sessionConfiguration = sessionConfiguration;
        this.driver = driver;
        this.robustWrapper = robustWrapper;
        this.waiter = waiter;
        this.urlBuilder = urlBuilder;
        stateFinder = new StateFinder(robustWrapper);
    }

    protected DriverScope(ElementFinder elementFinder, DriverScope outer) {
        this.elementFinder = elementFinder;
        this.outerScope = outer;
        driver = outer.driver;
        robustWrapper = outer.robustWrapper;
        urlBuilder = outer.urlBuilder;
        stateFinder = outer.stateFinder;
        waiter = outer.waiter;
        options = outer.sessionConfiguration;
        sessionConfiguration = outer.sessionConfiguration;
    }

    public String location() {
        return driver.location();
    }

    public boolean considerInvisibleElements() {
        return defaultWhereNull(options).ConsiderInvisibleElements;
    }

    protected Options setOptions(Options options) {
        return this.options = defaultWhereNull(options);
    }

    private Options defaultWhereNull(Options options) {
        return options == null ? sessionConfiguration : options;
    }

    public void clickButton(String locator) {
        clickButton(locator, sessionConfiguration);
    }

    public void clickButton(String locator, Options options) {
        retryUntilTimeout(waitThenClickButton(locator, setOptions(options)));
    }

    public void clickLink(String locator) {
        clickLink(locator, sessionConfiguration);
    }

    public void clickLink(String locator, Options options) {
        retryUntilTimeout(waitThenClickLink(locator, setOptions(options)));
    }


    private WaitThenClick waitThenClickLink(String locator, Options options) {
        return new WaitThenClick(driver, setOptions(options), waiter, new LinkFinder(driver, locator, this));
    }

    private WaitThenClick waitThenClickButton(String locator, Options options) {
        return new WaitThenClick(driver, setOptions(options), waiter, new ButtonFinder(driver, locator, this));
    }

    public DriverScope clickButton(String locator, PredicateQuery until, TimeSpan waitBeforeRetry) {
        return clickButton(locator, until, waitBeforeRetry, sessionConfiguration);
    }

    public DriverScope clickButton(String locator, PredicateQuery until, TimeSpan waitBeforeRetry, Options options) {
        options = setOptions(options);
        tryUntil(waitThenClickButton(locator, options), until, waitBeforeRetry, options);
        return this;
    }

    public DriverScope clickLink(String locator, PredicateQuery until, TimeSpan waitBeforeRetry) {
        return clickLink(locator, until, waitBeforeRetry, sessionConfiguration);
    }

    public DriverScope clickLink(String locator, PredicateQuery until, TimeSpan waitBeforeRetry, Options options) {
        options = setOptions(options);
        tryUntil(waitThenClickLink(locator, options), until, waitBeforeRetry, options);
        return this;
    }

    public ElementScope findButton(String locator) {
        return findButton(locator, sessionConfiguration);
    }

    public ElementScope findButton(String locator, Options options) {
        return new RobustElementScope(new ButtonFinder(driver, locator, this), this, setOptions(options));
    }

    public ElementScope findLink(String locator) {
        return findLink(locator, sessionConfiguration);
    }

    public ElementScope findLink(String locator, Options options) {
        return new RobustElementScope(new LinkFinder(driver, locator, this), this, setOptions(options));
    }

    public ElementScope findField(String locator) {
        return findField(locator, sessionConfiguration);
    }

    public ElementScope findField(String locator, Options options) {
        return new RobustElementScope(new FieldFinder(driver, locator, this), this, setOptions(options));
    }

    public FillInWith fillIn(String locator) {
        return fillIn(locator, sessionConfiguration);
    }

    public FillInWith fillIn(String locator, Options options) {
        return new FillInWith(locator, driver, robustWrapper, this, setOptions(options));
    }

    public SelectFrom select(String option) {
        return select(option, sessionConfiguration);
    }

    public SelectFrom select(String option, Options options) {
        return new SelectFrom(option, driver, robustWrapper, this, setOptions(options));
    }

    public boolean hasContent(String text) {
        return hasContent(text, sessionConfiguration);
    }

    public boolean hasContent(String text, Options options) {
        return query(new HasContentQuery(driver, this, text, setOptions(options)));
    }

    public boolean hasContentMatch(Pattern pattern) {
        return hasContentMatch(pattern, sessionConfiguration);
    }

    public boolean hasContentMatch(Pattern pattern, Options options) {
        return query(new HasContentMatchQuery(driver, this, pattern, setOptions(options)));
    }

    public boolean hasNoContent(String text) {
        return hasNoContent(text, sessionConfiguration);
    }

    public boolean hasNoContent(String text, Options options) {
        return query(new HasNoContentQuery(driver, this, text, setOptions(options)));
    }

    public boolean hasNoContentMatch(Pattern pattern) {
        return hasNoContentMatch(pattern, sessionConfiguration);
    }

    public boolean hasNoContentMatch(Pattern pattern, Options options) {
        return query(new HasNoContentMatchQuery(driver, this, pattern, setOptions(options)));
    }

    public boolean hasCss(String cssSelector) {
        return hasCss(cssSelector, sessionConfiguration);
    }

    public boolean hasCss(String cssSelector, Options options) {
        return query(new HasCssQuery(driver, this, cssSelector, setOptions(options)));
    }

    public boolean hasNoCss(String cssSelector) {
        return hasNoCss(cssSelector, sessionConfiguration);
    }

    public boolean hasNoCss(String cssSelector, Options options) {
        return query(new HasNoCssQuery(driver, this, cssSelector, setOptions(options)));
    }

    public boolean hasXPath(String xpath) {
        return hasXPath(xpath, sessionConfiguration);
    }
    
    public boolean hasXPath(String xpath, Options options) {
        return query(new HasXPathQuery(driver, this, xpath, setOptions(options)));
    }

    public boolean hasNoXPath(String xpath) {
        return hasNoXPath(xpath, sessionConfiguration);
    }
    
    public boolean hasNoXPath(String xpath, Options options) {
        return query(new HasNoXPathQuery(driver, this, xpath, setOptions(options)));
    }

    public ElementScope findCss(String cssSelector, Options options) {
        return new RobustElementScope(new CssFinder(driver, cssSelector, this), this, setOptions(options));
    }

    public ElementScope findCss(String cssSelector) {
        return findCss(cssSelector, sessionConfiguration);
    }

    public ElementScope findXPath(String xpath) {
        return findXPath(xpath, sessionConfiguration);
    }
    
    public ElementScope findXPath(String xpath, Options options) {
        return new RobustElementScope(new XPathFinder(driver, xpath, this), this, setOptions(options));
    }

    public List<ElementFound> findAllCss(String cssSelector) {
        return findAllCss(cssSelector, sessionConfiguration);
    }
    
    public List<ElementFound> findAllCss(String cssSelector, Options options) {
        setOptions(options);
        return driver.findAllCss(cssSelector, this);
    }

    public List<ElementFound> findAllXPath(String xpath) {
        return findAllXPath(xpath, sessionConfiguration);
    }
    
    public List<ElementFound> findAllXPath(String xpath, Options options) {
        setOptions(options);
        return driver.findAllXPath(xpath, this);
    }

    public ElementScope findSection(String locator) {
        return findSection(locator, sessionConfiguration);
    }
    
    public ElementScope findSection(String locator, Options options) {
        return new RobustElementScope(new SectionFinder(driver, locator, this), this, setOptions(options));
    }

    public ElementScope findFieldset(String locator) {
        return findFieldset(locator, sessionConfiguration);
    }

    public ElementScope findFieldset(String locator, Options options) {
        return new RobustElementScope(new FieldsetFinder(driver, locator, this), this, setOptions(options));
    }

    public ElementScope findId(String id) {
        return findId(id, sessionConfiguration);
    }

    public ElementScope findId(String id, Options options) {
        return new RobustElementScope(new IdFinder(driver, id, this), this, setOptions(options));
    }

    public void check(String locator) {
        check(locator, sessionConfiguration);
    }    
    
    public void check(String locator, Options options) {
        retryUntilTimeout(new Check(driver, this, locator, setOptions(options)));
    }

    public void uncheck(String locator) {
        uncheck(locator, sessionConfiguration);
    }

    public void uncheck(String locator, Options options) {
        retryUntilTimeout(new Uncheck(driver, this, locator, setOptions(options)));
    }

    public void choose(String locator) {
        choose(locator, sessionConfiguration);
    }

    public void choose(String locator, Options options) {
        retryUntilTimeout(new Choose(driver, this, locator, setOptions(options)));
    }

    public String executeScript(String javascript) {
        return driver.executeScript(javascript, this);
    }

    public DriverScope hover(Options options) {
        retryUntilTimeout(new Hover(this, driver, setOptions(options)));
        return this;
    }

    public DriverScope hover() {
        return hover(sessionConfiguration);
    }

    public boolean has(ElementScope findElement) {
        return has(findElement, sessionConfiguration);
    }

    public boolean has(ElementScope findElement, Options options) {
        return findElement.exists(options);
    }

    public boolean hasNo(ElementScope findElement) {
        return hasNo(findElement, sessionConfiguration);
    }

    public boolean hasNo(ElementScope findElement, Options options) {
        return findElement.missing(options);
    }

    public void retryUntilTimeout(BrowserAction action) {
        query(action);
    }

    public IFrameElementScope findIFrame(String locator) {
        return findIFrame(locator, sessionConfiguration);
    }

    public IFrameElementScope findIFrame(String locator, Options options) {
        return new IFrameElementScope(new IFrameFinder(driver, locator, this), this, setOptions(options));
    }

    public void tryUntil(BrowserAction tryThis, PredicateQuery until, TimeSpan waitBeforeRetry) {
        tryUntil(tryThis,until,waitBeforeRetry, sessionConfiguration);
    }

    public void tryUntil(BrowserAction tryThis, PredicateQuery until, TimeSpan waitBeforeRetry, Options options) {
        robustWrapper.tryUntil(tryThis, until, setOptions(options).Timeout, waitBeforeRetry);
    }

    public State findState(State... states) {
        return findState(states,options);
    }

    public State findState(State[] states, Options options) {
        return stateFinder.findState(setOptions(options), states);
    }

   /**
    *  Try and find this scope now
    *
    *  @return
    */
    public ElementFound now() {
        return findElement();
    }

    public ElementFound findElement() {
        if (element == null || element.stale())
            element = elementFinder.find();

        return element;
    }

    public <T> T query(Query<T> query) {
        return robustWrapper.robustly(query);
    }
}
