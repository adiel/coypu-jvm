﻿using NSpec;
using NUnit.Framework;

namespace Coypu.Drivers.Tests
{
    public class When_clicking : DriverSpecs
    {
        [Test]
        public void Clicks_the_underlying_element()

        {
            var element = Driver.FindButton("clickMeTest", Root);
            Driver.FindButton("clickMeTest", Root).Value.should_be("Click me");
            Driver.Click(element);
            Driver.FindButton("clickMeTest", Root).Value.should_be("Click me - clicked");
        }
    }
}