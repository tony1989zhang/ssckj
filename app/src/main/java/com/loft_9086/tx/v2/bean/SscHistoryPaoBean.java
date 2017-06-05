package com.loft_9086.tx.v2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public class SscHistoryPaoBean implements Serializable {

    public HeaderBean header;
    public List<DataBean> data;

    public static class HeaderBean {
        public PeriodBean period;
        public Num1Bean num1;
        public Num2Bean num2;
        public Num3Bean num3;
        public Num4Bean num4;
        public Num5Bean num5;
        public Num6Bean num6;
        public Num7Bean num7;
        public Num8Bean num8;
        public Num9Bean num9;
        public Num10Bean num10;

        public static class PeriodBean {
            public String name;
        }

        public static class Num1Bean {
            public String name;
        }

        public static class Num2Bean {
            public String name;
        }

        public static class Num3Bean {
            public String name;
        }

        public static class Num4Bean {
            public String name;
        }

        public static class Num5Bean {
            public String name;
        }

        public static class Num6Bean {
            public String name;
        }

        public static class Num7Bean {
            public String name;
        }

        public static class Num8Bean {
            public String name;
        }

        public static class Num9Bean {
            public String name;
        }

        public static class Num10Bean {
            public String name;
        }
    }

    public static class DataBean {
        public String period;
        public int num1;
        public int num2;
        public int num3;
        public int num4;
        public int num5;
        public int num6;
        public int num7;
        public int num8;
        public int num9;
        public int num10;
        public String open_date;
        public String source;
        public String source_date;
        public String rt;
        public String rt2;
    }
}
