﻿using System.Linq;
using Coypu.Tests.TestDoubles;
using NUnit.Framework;

namespace Coypu.Tests.When_interacting_with_the_browser
{
    [TestFixture]
    public class When_finding_then_hovering : BrowserInteractionTests
    {
        [Test]
        public void It_makes_robust_call_to_find_then_hover_element_on_underlying_driver()
        {
            var element = new StubElement();
            driver.StubCss("something.to hover", element, browserSession);
            spyRobustWrapper.AlwaysReturnFromRobustly(element);

            browserSession.FindCss("something.to hover").Hover();

            RunQueryAndCheckTiming();

            Assert.That(driver.FindCssRequests, Is.Empty, "Finder call not deferred");
            Assert.That(driver.HoveredElements, Has.Member(element));
        }
    }
}