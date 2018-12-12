package wx.contants;

/**
 * SC常量
 */
public interface WxConstant {
    String MENU_SEQUENCE = "MENU_SEQUENCE";
    String UPDATE = "UPDATE";
    String SET = "SET";
    String WHERE = "WHERE";
    String EQUAL = "=";
    String QUESTION_MARK = "?";
    String COMMA = ",";
    String CHINA_COMMA = "，";
    String IN = "IN";
    String LEFT_BRACKET = "(";
    String RIGHT_BRACKET = ")";
    String COLON = ":";
    String AND = "AND";
    String FALSE = "false";
    String TRUE = "true";
    String PLUS = "+";
    String DELETE = "DELETE";
    String SELECT = "SELECT";
    String FROM = "FROM";
    String COALESCE = "coalesce";
    String ZERO = "0";
    String ONE = "1";
    String POINT = ".";
    String REDIS_DIVISION = "_";
    String SUCCESS = "SUCCESS";
    String FAILED = "FAILED";
    String PROCESS = "PROCESS";
    String BACKSLASH = "/";
    String STAR = "*";
    String PERCENT = "%";
    String ALL = "ALL";
    String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String[] ITEM_TYPES = {"SINGLE_SELECTION", "MULTI_SELECTION", "INDEFINITE_SELECTION", "JUDGMENT", "FILL", "QUESTIONS"};
    String[] ITEM_ATTRIBUTE = {"EXERCISE", "EXAM"};
    String EMPTY = "EMPTY";
    String HTML = "html";
    String FTL = "ftl";
    String UTF8 = "UTF-8";
    String SOCKET_ANSWER_KEY = "ANSWER";
    String UNDERLINE = "_";
    String ALSO = "&";
    String ADMIN = "admin";
    String DEFAULT_CORP_CODE = "default";
    String MANAGE_ACTION = "manage";
    Integer MAX_ERROR_COUNT = 4;
    String ERROR = "error";
    String HTTP_PREFIX = "http://";
    String DIVIDING_LINE = "|";
    String T = "t";
    String F = "f";
    String DEFAULT_DOMAIN_SUFFIX = ".demo.veln.cn";
    Long MAX_PAPER_COUNT = 100L;
    String LOGIN_REMEMBER_PWD_PREFIX = "pe_login_remember_";
    String WORD_SUFFIX_DOCX = "docx";
    String IMAGE_SUFFIX_PNG = "png";
    String VIDEO_SUFFIX_WEBM = "webm";
    String BRACKET_LEFT = "[";
    String BRACKET_RIGHT = "]";
    int EXPIRE_TIME_EXAM_SHOW_RESULT = 600;
    String SEPARATOR_WAVE = "~";
    int MAX_GROUP_PAGE_SIZE = 800;
    String PDF = "pdf";
    /**
     * 转换成功
     */
    String CONVERTSUCCESS = "convertsuccess";

    /**
     * doc
     */
    String DOC = "doc";

    /**
     * docx
     */
    String DOCX = "docx";

    /**
     * ppt
     */
    String PPT = "ppt";
    /**
     * pptx
     */
    String PPTX = "pptx";
    /**
     * 空串
     */
    String EMPTYSTRING = "";
    /**
     * 文档类课程
     */
    String DOC_COURSE = "文档类课程";
    /**
     * 视频类课程
     */
    String VIDEO_COURSE = "视频类课程";
    /**
     * H5课程
     */
    String H5_COURSE = "H5课程";

    /**
     * 已经转换了
     */
    String CONVERTED = "CONVERTED";
    /**
     * 往前台传递消息的key
     */
    String MSG = "msg";
    /**
     * 错误码
     */
    String ERROR_CODE = "error_code";
    /**
     * 课程被禁止状态
     */
    String FORBIDDEN = "forbidden";
    /**
     * 课程的草稿状态
     */
    String DRAFT = "draft";
    /**
     * 课程的封面
     */
    String COVER = "cover";
    /**
     * 课程的音频
     */
    String AUDIO = "audio";
    /**
     * 课程图片
     */
    String IMAGE = "image";

    /**
     * 视频课程封面
     */
    String COURSE_THUMB = "course_thumb";
    /**
     * 视频课程讲义
     */
    String HANDOUT = "handout";
    /**
     * 视频课程的视频
     */
    String COURSE = "course";
    /**
     * 课程的背景音乐
     */
    String BGM = "bgm";
    /**
     * 课程的页面浏览量
     */
    String PV_VIEW = "pv_view";
    /**
     * 课程的unique浏览量
     */
    String UV_VIEW = "uv_view";
    /**
     * 时间
     */
    String DATE = "date";
    /**
     * 总数
     */
    String TOTAL = "total";
    /**
     * 浏览的来源的总数
     */
    String VIEWSOURCETOTAL = "viewSource_total";
    /**
     * 课程的分享
     */
    String SHARE = "share";
    /**
     * 课程的分享的总数
     */
    String SHARETOTAL = "share_total";
    /**
     * 页面请求所带的token令牌
     */
    String TOKEN = "token";

    String EMPTY_H5_ITEM_CONTENT = "{\"items\":[],\"pageInfo\":{\"backgroundColor\":\"rgba(255, 255, 255, 0)\"}}";
}

