package wx.milk.web.base.scheduler;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;
import wx.milk.manager.IWxJobManager;
import wx.milk.model.WxJob;
import wx.milk.web.base.constant.RedisKey;
import wx.milk.web.base.inspect.ExecutionContext;
import wx.redis.WxJedisCommands;
import wx.redis.WxRedisClient;
import wx.util.LogUtil;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/6/14/014.
 */
@Component
public class WxScheduler {
    private static final Log LOG = LogUtil.getLog();
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private IWxJobManager wxJobManager;

    @PostConstruct
    public void init() {
        taskExecutor.submit((Runnable) () -> {
            LOG.info("PeJMS thread start ...");
            WxJedisCommands jedis = getJedis();
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);
            while (true) {
                try {
                    List<String> valueList = jedis.brpop(0, RedisKey.WAIT_CONSUME_FUNCTION_CODE,
                            RedisKey.CONSUMED_FUNCTION_CODE);
                    if (CollectionUtils.isEmpty(valueList)) {
                        continue;
                    }

                    executorService.submit((Runnable) () -> {
                        String key = valueList.get(0);
                        String json = valueList.get(1);
                        if (StringUtils.isEmpty(json)) {
                            return;
                        }

                        processJms(key, json);
                    });

                } catch (JedisConnectionException e) {
                    jedis = getJedis();
                } catch (Exception e) {
                    LOG.error("PeJMS failed with exception !", e);
                }
            }
        });
    }

    private void processJms(String key, String json) {
        switch (key) {
            case RedisKey.WAIT_CONSUME_FUNCTION_CODE:
                WxJob wxJob = JSON.parseObject(json, WxJob.class);
                LOG.info("Consume job functionCode [" + wxJob.getFunctionCode() + "] start...");
                ExecutionContext.setUserId(wxJob.getCreateUser());
                try {
                    wxJob.setExecuteStatus(WxJob.ExecuteStatus.SUCCESS);
                } catch (Exception e) {
                    LOG.error(e);
                    wxJob.setExecuteStatus(WxJob.ExecuteStatus.FAILED);
                    wxJob.setMsgDetail(e.getMessage());
                }

                wxJobManager.insert(wxJob);
                LOG.info("Consume job functionCode [" + wxJob.getFunctionCode() + "] end...");
                break;
            default:
        }
    }

    private WxJedisCommands getJedis() {
        WxJedisCommands jedis = null;
        while (jedis == null) {
            LOG.info("Get jedis from pool ...");
            jedis = WxRedisClient.getCommonJedis();
            if (jedis == null) {
                LOG.info("Get jedis from pool failed ! try again after 3 seconds ...");
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }

        LOG.info("Get jedis from pool success !");
        return jedis;
    }
}
