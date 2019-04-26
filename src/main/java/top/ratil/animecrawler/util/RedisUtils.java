package top.ratil.animecrawler.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        if (key == null || "".equals(key) || time < 0) {
            return false;
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        if (key == null || "".equals(key)) {
            return false;
        }

        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存，可批量删除
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object get(String key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        if (key == null || "".equals(key)) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置缓存以及过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {

        if (key == null || "".equals(key) || time < 0) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
