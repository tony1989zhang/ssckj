package com.loft_9086.tx.v2.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/3 0003.
 */

public class PaoMaBean implements Serializable {

    public int time;
    public CurrentBean current;
    public NextBean next;

    public static class CurrentBean {
        public long periodNumber;
        public long periodDate;
        public String awardTime;
        public String awardNumbers;

        @Override
        public String toString() {
            return "CurrentBean{" +
                    "periodNumber=" + periodNumber +
                    ", periodDate=" + periodDate +
                    ", awardTime='" + awardTime + '\'' +
                    ", awardNumbers='" + awardNumbers + '\'' +
                    '}';
        }
    }

    public static class NextBean {
        public String periodNumber;
        public String periodDate;
        public String awardTime;
        public int awardTimeInterval;
        public String delayTimeInterval;

        @Override
        public String toString() {
            return "NextBean{" +
                    "periodNumber='" + periodNumber + '\'' +
                    ", periodDate='" + periodDate + '\'' +
                    ", awardTime='" + awardTime + '\'' +
                    ", awardTimeInterval=" + awardTimeInterval +
                    ", delayTimeInterval='" + delayTimeInterval + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PaoMaBean{" +
                "time=" + time +
                ", current=" + current +
                ", next=" + next +
                '}';
    }
}
