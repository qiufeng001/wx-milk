package wx.milk.model.log;

/**
 * auther: kiven on 2018/8/30/030 18:29
 * try it bast!
 */
public class WxContant {

    public enum ReturnCode {
        RES_SUCCESS("成功", "success");

        private String description;

        private String text;

        private ReturnCode(String description, String text) {
            this.text = text;
            this.description = description;
        }

        public String getText() {
            return text;
        }

        public String getDescription() {
            return description;
        }
    }
}
