package wx.milk.web.base.constant;

/**
 * 主要存放redis的所有key值及对应过期时间
 * <p>
 * 每个key值必须注明用途及是否会自动过期及过期的时间
 * </p>
 *
 * @author TaoFaDeng@HF
 * @since 2016年09月30日10:15:03
 */
public interface RedisKey {
    //待发送消息队列Redis key
    String PE_IM_SMS_QUEUE_QUICK = "PE_IM_SMS_QUEUE_QUICK";
    String PE_IM_SMS_QUEUE_SLOW = "PE_IM_SMS_QUEUE_SLOW";
    String PE_IM_SMS_QUEUE_FAILED = "PE_IM_SMS_QUEUE_FAILED";
    String PE_IM_EMAIL_QUEUE_QUICK = "PE_IM_EMAIL_QUEUE_QUICK";
    String PE_IM_EMAIL_QUEUE = "PE_IM_EMAIL_QUEUE";
    String PE_IM_EMAIL_QUEUE_FAILED = "PE_IM_EMAIL_QUEUE_FAILED";
    //流水号
    String ITEM_SN = "ITEM_SN";
    String PAPER_SN = "PAPER_SN";
    String EXAM_SN = "EXAM_SN";
    String EXERCISE_SN = "EXERCISE_SN";
    //用户互踢用
    String UC_ALREADY_LOGIN = "UC" + PeConstant.REDIS_DIVISION
            + "ALREADY" + PeConstant.REDIS_DIVISION
            + "LOGIN";
    //绑定邮箱的redis key
    String UC_BIND_Email = "UC" + PeConstant.REDIS_DIVISION
            + "BIND" + PeConstant.REDIS_DIVISION
            + "Email";
    //忘记密码图形验证码的redis key
    String UC_LOGIN_FORGET_VERIFY_CODE = "UC" + PeConstant.REDIS_DIVISION
            + "LOGIN" + PeConstant.REDIS_DIVISION
            + "FROGET" + PeConstant.REDIS_DIVISION
            + "VERIFY" + PeConstant.REDIS_DIVISION
            + "CODE";
    String UC_BING_MOBILE_VERIFY_CODE = "UC_BING_MOBILE_VERIFY_CODE_";
    //session存放的redis key
    String UC_LOGIN_SESSION = "UC" + PeConstant.REDIS_DIVISION
            + "LOGIN" + PeConstant.REDIS_DIVISION
            + "SESSION";
    //存放练习添加的人员
    String EXERCISE_USER = "EXERCISE_USER_";
    //存放练习添加的组织
    String EXERCISE_ORGANIZE = "EXERCISE_ORGANIZE_";
    //记住密码redis key
    String UC_REMEMBER_PWD = "UC_REMEMBER_PWD_";
    //防止重复登录的锁
    String LOGIN_LOCK = "LOGIN_LOCK";
    //模拟考试的模拟学员信息
    String SIMULATIONEXAM_USER = "SIMULATION_USER";
    //存放考试中学员提交答题记录 前缀_examId
    String EXAM_USER_SUBMIT = "EXAM_USER_SUBMIT";
    //存放考试待评卷前缀_examId
    String EXAM_MARKING = "EXAM_MARKING";
    //存放考试中的学员ID 考试ID&学员ID
    String IN_EXAMINATION = "IN_EXAMINATION";
    //存放考试待评卷前缀_examId
    String EXAM_MARKING_ERROR_COUNT = "EXAM_MARKING_ERROR_COUNT";
    //存放学员存放待评卷的试题
    String EXAM_USER_MARKING = "EXAM_USER_MARKING";
    // 考试基本信息 redis 前缀
    String EXAM_INFO = "EXAM_INFO";
    //存放批次消息的redis前缀
    String ARRANGE_MESSAGE = "ARRANGE_MESSAGE";
    //考试关联人员ID
    String EXAM_USER = "EXAM_USER";
    //手机验证码
    String EXAM_USER_VERIFY_CODE = "EXAM_USER_VERIFY_CODE";
    //评卷中 失效时间30分钟
    String EXAM_MARKING_USER = "EXAM_MARKING_USER";
    //待消费KEY
    String WAIT_CONSUME_FUNCTION_CODE = "WAIT_CONSUME_FUNCTION_CODE";
    //已消费KEY
    String CONSUMED_FUNCTION_CODE = "CONSUMED_FUNCTION_CODE";
    /**
     * 公司域名对应的corpCode
     */
    String CORP_INFO_DOMAIN = "CORP_INFO_DOMAIN_";
    /**
     * 储存ELN人员数据信息
     */
    String ELN_CORP_CODE = "ELN_";

    /**
     * corpCode对应的公司域名
     */
    String CORP_INFO_CODE = "CORP_INFO_CODE_";

    /**
     * 强制提交 队列
     */
    String FORCE_USER_SUBMIT = "FORCE_USER_SUBMIT_";

    /**
     * redis保存试卷信息
     */
    String EXAM_PAPER = "EXAM_PAPER_";

    /**
     * 准考证号 一级key examId 二级key 准考证号 三级 userId
     */
    String EXAM_USER_TICKET = "EXAM_USER_TICKET_";

    /**
     * 存放学员成绩，针对自动评卷以及自动发布成绩 key examId_userId value examResult 失效时间 10分钟
     */
    String EXAM_SHOW_RESULT = "EXAM_SHOW_RESULT_";
    /**
     * 官网短信图片验证码
     */
    String WEBSITE_SMS_IMAGE_CODE = "WEBSITE_SMS_IMAGE_CODE_";

    /**
     * 官网短信验证码
     */
    String WEBSITE_SMS_CODE = "WEBSITE_SMS_CODE_";
    /**
     * 静态资源版本号
     */
    String STATIC_RESOURCE_VERSION = "STATIC_RESOURCE_VERSION";

    /**
     * 存放监考老师实时图片ID EXAM_MONITOR_IMAGE_arrangeId_userId imageId 30S
     */
    String EXAM_MONITOR_IMAGE = "EXAM_MONITOR_IMAGE_";

    /**
     * 存放总控制台发送短信存储 EXAM_MONITOR_MESSAGE_arrangeId_userId message
     */
    String EXAM_MONITOR_MESSAGE = "EXAM_MONITOR_MESSAGE_";

    /**
     * 存放监考老师实时图片ID EXAM_MONITOR_IMAGE_LOCK_arrangeId_userId true 15s
     */
    String EXAM_MONITOR_IMAGE_LOCK = "EXAM_MONITOR_IMAGE_LOCK_";

    /**
     * 验证是否能够上传的key
     */
    String UPLOAD_LOCK = "UPLOAD_LOCK";

    /**
     * 将ppt转换成pptx格式的队列
     */
    String PPT_CONVERT_QUEUE = "PPT_CONVERT_QUEUE";

    /**
     * 将 word 转成 pdf 队列
     */
    String WORD_TO_PDF_QUEUE = "WORD_TO_PDF_QUEUE";

    /**
     * 需要将ppt中的shape转换成图片保存的队列
     */
    String CONVERT_FAIL_QUEUE = "convertfailqueue";

    /**
     * 转换错误的名称
     */
    String SHAPE_CONVERT_QUEUE = "SHAPE_CONVERT_QUEUE";

    String PE_SCENE_GENERATING_ITEM_COUNT_ = "PE_SCENE_GENERATING_ITEM_COUNT_";
    String PE_PPT_IMPORT_STATUS_ = "PE_PPT_IMPORT_STATUS_";
}