package com.mukhi.bobaorbust;

import java.time.Duration;
import java.time.temporal.Temporal;

/**
 * Add Instance after creation and after end game so that they can be passed here to do the fun math
 */
public class TimeCalculator {

    // This is our messy stuff that we are going to add later
    private long timeElapsed;

    TimeCalculator(Temporal start, Temporal end) {
      Duration duration = Duration.between(end,start);
      timeElapsed = duration.getSeconds();
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }


}
