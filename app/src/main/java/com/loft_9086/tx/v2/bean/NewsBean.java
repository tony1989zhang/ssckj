package com.loft_9086.tx.v2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class NewsBean  implements Serializable {

    public int status;
    public Object url;
    public List<NewsEntity> msg;
    public class NewsEntity {
        public String id;
        public String catid;
        public String title;
        public String summary;
        public String logofile;
        public String url;
        public String publishdate;
    }


}
