﻿


package Coypu.Drivers.Tests
{
    class When_finding_sections extends DriverSpecs
    {
        @Test
        public void Finds_by_h1_text()
        {
            Driver().FindSection("Section One h1", Root()).Id(), is(equalTo("sectionOne");
            Driver().FindSection("Section Two h1", Root()).Id(), is(equalTo("sectionTwo");
        }

        @Test
        public void Finds_by_h2_text()
        {
            Driver().FindSection("Section One h2", Root()).Id(), is(equalTo("sectionOne");
            Driver().FindSection("Section Two h2", Root()).Id(), is(equalTo("sectionTwo");
        }

        @Test
        public void Finds_by_h3_text()
        {
            Driver().FindSection("Section One h3", Root()).Id(), is(equalTo("sectionOne");
            Driver().FindSection("Section Two h3", Root()).Id(), is(equalTo("sectionTwo");
        }

        @Test
        public void Finds_by_h6_text()
        {
            Driver().FindSection("Section One h6", Root()).Id(), is(equalTo("sectionOne");
            Driver().FindSection("Section Two h6", Root()).Id(), is(equalTo("sectionTwo");
        }

        @Test
        public void Finds_section_by_id()
        {
            Driver().FindSection("sectionOne", Root()).Id(), is(equalTo("sectionOne");
            Driver().FindSection("sectionTwo", Root()).Id(), is(equalTo("sectionTwo");
        }


        @Test
        public void Only_finds_div_and_section()
        {
            Assert.Throws<MissingHtmlException>(() => Driver().FindSection("scope1TextInputFieldId", Root()));
            Assert.Throws<MissingHtmlException>(() => Driver().FindSection("fieldsetScope2", Root()));
        }
    }
}