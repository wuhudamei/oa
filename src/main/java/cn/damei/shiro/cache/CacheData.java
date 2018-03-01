package cn.damei.shiro.cache;
import java.io.Serializable;
public class CacheData<T> implements Serializable {
    private long expiresTime;
    private long createTime;
    private T data;
    public long getExpiresTime() {
        return expiresTime;
    }
    public void setExpireTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }
    public long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public CacheData(Builder<T> builder) {
        this.expiresTime = builder.expireTime;
        this.createTime = builder.createTime;
        this.data = builder.data;
    }
    public static class Builder<T> {
        private long expireTime;
        private long createTime;
        private T data;
        public Builder expireTime(long expireTime) {
            this.expireTime = expireTime;
            return this;
        }
        public Builder ereateTime(long createTime) {
            this.createTime = createTime;
            return this;
        }
        public Builder<T> data(T data) {
            this.createTime = -1;
            this.createTime = System.currentTimeMillis();
            this.data = data;
            return this;
        }
        public Builder<T> data(T data, long expireTime) {
            this.expireTime = expireTime;
            this.createTime = System.currentTimeMillis();
            this.data = data;
            return this;
        }
        public CacheData builder() {
            return new CacheData<T>(this);
        }
    }
}
