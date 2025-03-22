package meteorologicalmonitoring.Utils;

/*
统一管理常用数据常量
文件路径，jwt令牌时间，密钥等
 */
public class SystemConstants {

    //阈值存储在json文件中
    public static final String MONITOR_DATA_FILE_PATH = "data/monitorData.json";

    //温室气体数据存放路径：D:\LGR Date Download V2.6\LGR\2025-01-16
    public static final String GAS_DATA_PATH = "D:/LGR Date Download V2.6/LGR/2025-01-16";

    //监测调用的文件路径，为D:\GaseMonitor\yyyy\mm\dd
    public static final String GASE_MONITOR_PATH = "data/";

    //监测数据文件路径
    //固定前后缀
    public static final String DATA_FILE_PATH_FROMER = "Z_CAWN_I_";
    public static final String DATA_FILE_PATH_AFTER = "_O_GHG-FLD-PF-XXXX-1013.txt";
    //站台号和时间并非固定，此处用作测试
    public static final String station = "54826_20250116220000";



    /*
    jwt令牌signKey,太短的话周周那里会报错，太长的话会导致token过长，影响性能
     */
    public static final String LOGIN_USER_KEY = "cbc5b166f64b3e50bfeb2f8d833381735c74219de6e91e964aab948f706ca473";

    /*
    存储登录用户信息jwt令牌和过期时间，七天
     */
    public static final Long LOGIN_USER_TTL = 7 * 24 * 60 * 60 * 1000L;
}


