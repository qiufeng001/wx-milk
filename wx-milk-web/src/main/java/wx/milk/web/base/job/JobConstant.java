package wx.milk.web.base.job;


public interface JobConstant {

    /**
     * quartz定时任务与触发器名称常量 JOB=定时任务名称前缀 、TRIGGER=触发器名称前缀、JMSJOINSING=JMS消息队列Queue名称连接符号、DEFAULTCORPCODE=默认启动quartz公司
     */
    String JOB = "job";
    String TRIGGER = "trigger";
    String JMS_JOIN_SIGN = ".";
    String EXPIRED_ERROR = "Based on configured schedule, the given trigger will never fire.";
    String QUARTZ_EXPRESSION_FORMAT = "ss mm HH dd MM ? yyyy";
    String APP_CODE = "appCode";
    String LOG_SIGN = "-------------------------";

    /**
     * function code
     */
    String FUNCTION_CODE_END_EXAM = "FUNCTION_CODE_END_EXAM";
    String TIMING_PUBLISH_MARK ="TIMING_PUBLISH_MARK";
}
