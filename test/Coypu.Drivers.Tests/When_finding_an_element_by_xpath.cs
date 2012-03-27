﻿using NSpec;
using NUnit.Framework;

namespace Coypu.Drivers.Tests
{
    internal class When_finding_an_element_by_xpath : DriverSpecs
    {
            
  [Test]
  public void Finds_present_examples()

            {
                var shouldFind = "//*[@id = 'inspectingContent']//p[@class='css-test']/span";
                Driver.FindXPath(shouldFind, Root).Text.should_be("This");

                shouldFind = "//ul[@id='cssTest']/li[3]";
                Driver.FindXPath(shouldFind, Root).Text.should_be("Me! Pick me!");
            }

            
    [Test]
    public void Does_not_find_missing_examples()
  
            {
                const string shouldNotFind = "//*[@id = 'inspectingContent']//p[@class='css-missing-test']";
                Assert.Throws<MissingHtmlException>(() => Driver.FindXPath(shouldNotFind, Root), "Expected not to find something at: " + shouldNotFind);
            }

            
    [Test]
    public void Only_finds_visible_elements()
  
            {
                const string shouldNotFind = "//*[@id = 'inspectingContent']//p[@class='css-test']/img";
                Assert.Throws<MissingHtmlException>(() => Driver.FindXPath(shouldNotFind, Root), "Expected not to find something at: " + shouldNotFind);
            }
        }
    
}