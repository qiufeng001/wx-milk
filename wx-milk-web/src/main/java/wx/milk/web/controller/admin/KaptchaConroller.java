package wx.milk.web.controller.admin;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wx.milk.web.utils.CookieUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * Created by Administrator on 2018/6/13/013.
 */
@RequestMapping(value = "/kaptcha")
@Controller
public class KaptchaConroller {

    @Autowired
    private Producer captchaProducer = null;


    @RequestMapping(value = "/image")
    public ModelAndView getKaptchaImage(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        //WxJedisCommands commonJedis  = WxRedisClient.getCommonJedis();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();

        // 验证验证码
        String sessionId = CookieUtils.getValue(request);
        try {
          //  commonJedis.set(sessionId + "_sessionId", capText);
        } catch (Exception e) {
            e.printStackTrace();
        }



        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }
}
