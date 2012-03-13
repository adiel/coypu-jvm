﻿using System;
using System.Diagnostics;
using Coypu.Robustness;
using NUnit.Framework;

namespace Coypu.Tests.When_making_browser_interactions_robust
{
    [TestFixture]
    public class When_trying_an_action_until_a_given_state_is_reached
    {
        private RetryUntilTimeoutRobustWrapper retryUntilTimeoutRobustWrapper;
        private Options options;

        [SetUp]
        public void SetUp()
        {
            options = new Options {Timeout = TimeSpan.FromMilliseconds(200), RetryInterval = TimeSpan.FromMilliseconds(10)};
            retryUntilTimeoutRobustWrapper = new RetryUntilTimeoutRobustWrapper();
        }

        [Test]
        public void When_state_exists_It_returns_immediately()
        {
            var toTry = new CountTriesAction(options);
            var retryInterval1 = TimeSpan.FromMilliseconds(10);
            var until = new AlwaysSucceedsQuery<bool>(true,TimeSpan.Zero,retryInterval1);
            
            retryUntilTimeoutRobustWrapper.TryUntil(toTry, until,TimeSpan.FromMilliseconds(20), retryInterval1);

            Assert.That(toTry.Tries, Is.EqualTo(1));
        }

        [Test]
        public void When_state_exists_after_three_tries_It_tries_three_times()
        {
            options.RetryInterval = TimeSpan.FromMilliseconds(10);
            var toTry = new CountTriesAction(options);
            var until = new ThrowsThenSubsequentlySucceedsQuery<bool>(true, true, 2, TimeSpan.FromMilliseconds(1000), options.RetryInterval);

            retryUntilTimeoutRobustWrapper.TryUntil(toTry, until, TimeSpan.FromMilliseconds(100), options.RetryInterval);

            Assert.That(toTry.Tries, Is.EqualTo(3));
        }

        [Test]
        public void When_state_never_exists_It_fails_after_timeout()
        {
            var toTry = new CountTriesAction(options);
            var until = new AlwaysSucceedsQuery<bool>(false, false, TimeSpan.Zero, options.RetryInterval);

            var stopwatch = Stopwatch.StartNew();
            var timeout1 = TimeSpan.FromMilliseconds(200);
            Assert.Throws<MissingHtmlException>(() => retryUntilTimeoutRobustWrapper.TryUntil(toTry, until, timeout1, options.RetryInterval));

            stopwatch.Stop();
            var elapsedMilliseconds = stopwatch.ElapsedMilliseconds;

            Assert.That(elapsedMilliseconds, Is.InRange(timeout1.TotalMilliseconds - (options.RetryInterval.Milliseconds + When_waiting.AccuracyMilliseconds),
                                                        timeout1.TotalMilliseconds + (options.RetryInterval.Milliseconds + When_waiting.AccuracyMilliseconds)));
        }


        [Test]
        public void It_applies_the_retryAfter_timeout_within_until()
        {
            var toTry = new CountTriesAction(options);
            var retryAfter = TimeSpan.FromMilliseconds(20);
            var until = new AlwaysThrowsQuery<bool, TestException>(options.Timeout, retryAfter);

            Assert.Throws<TestException>(() => retryUntilTimeoutRobustWrapper.TryUntil(toTry, until, options.Timeout, options.RetryInterval));

            Assert.That(toTry.Tries, Is.GreaterThan(1));
            Assert.That(toTry.Tries, Is.LessThan(12));
        }
    }
}