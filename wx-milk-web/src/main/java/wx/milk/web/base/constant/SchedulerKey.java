package wx.milk.web.base.constant;


public interface SchedulerKey {
    String JOB_QUEUE_NAME = "JOB_QUEUE_NAME";
    //存放考试中的学员ID 考试ID&学员ID
    String SCHEDULER_EXAMINATION = "SCHEDULER_EXAMINATION";
    //存放考试待评卷前缀_examId
    String SCHEDULER_EXAM_MARKING = "SCHEDULER_EXAM_MARKING";
    //待消费KEY
    String SCHEDULER_FUNCTION_CODE = "SCHEDULER_FUNCTION_CODE";
}