﻿using System;
using System.Diagnostics;

namespace Coypu.Robustness
{
    internal class ThreadSleepWaiter : Waiter
    {
        public void Wait(TimeSpan duration)
        {
            var stopWatch = Stopwatch.StartNew();
            while(stopWatch.ElapsedMilliseconds < duration.TotalMilliseconds)
            {
            }
            stopWatch.Stop();
        }
    }
}