using NSpec;
using NUnit.Framework;

namespace Coypu.Drivers.Tests
{
    internal class When_finding_fields_by_placeholder : DriverSpecs
    {
        [Test]
        public void Finds_text_field_by_placeholder()
        {
            Driver.FindField("text input field with a placeholder", Root).Id.should_be("textInputFieldWithPlaceholder");
            Driver.FindField("textarea field with a placeholder", Root).Id.should_be("textareaFieldWithPlaceholder");
        }
    }
}