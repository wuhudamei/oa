package cn.damei.shiro;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;
public class JedisManager {
	private Pool<Jedis> jedisPool;
	private int dbIndex;
	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
		} catch (Exception e) {
			throw new JedisConnectionException(e);
		}
		return jedis;
	}
	public void returnResource(Jedis jedis, boolean isBroken) {
		if (jedis == null)
			return;
		if (isBroken)
			getJedisPool().returnBrokenResource(jedis);
		else
			getJedisPool().returnResource(jedis);
	}
	public byte[] getValueByKey(byte[] key) throws Exception {
		Jedis jedis = null;
		byte[] result = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			result = jedis.get(key);
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
		return result;
	}
	public void deleteByKey(byte[] key) throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
	}
	public void saveValueByKey(byte[] key, byte[] value, int expireTime) throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			if (expireTime > 0)
				jedis.expire(key, expireTime);
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
	}
	public Pool<Jedis> getJedisPool() {
		return jedisPool;
	}
	public void setJedisPool(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}
	public int getDatabase() {
		return dbIndex;
	}
	public void setDatabase(int index) {
		Assert.isTrue(index >= 0, "invalid DB index (a positive index required)");
		this.dbIndex = index;
	}
}