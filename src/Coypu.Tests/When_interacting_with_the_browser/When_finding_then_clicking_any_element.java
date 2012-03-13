﻿using System;
using System.Linq;
using Coypu.Tests.TestDoubles;
using NUnit.Framework;

namespace Coypu.Tests.When_interacting_with_the_browser
{
    [TestFixture]
    public class When_finding_then_clicking_any_element : BrowserInteractionTests
    {
        [Test]
        public void It_makes_robust_call_to_find_then_clicks_element_on_underlying_driver()
        {
            var element = new StubElement();
            driver.StubCss("something.to click", element, browserSession);
            spyRobustWrapper.AlwaysReturnFromRobustly(element);

            var elementScope = browserSession.FindCss("something.to click");

            Assert.That(driver.FindCssRequests, Is.Empty, "Finder not called robustly");

            elementScope.Click();

            RunQueryAndCheckTiming();

            Assert.That(driver.FindCssRequests.Any(), Is.False, "Scope finder was not deferred");

            Assert.That(driver.ClickedElements, Has.Member(element));
        }
    }
}