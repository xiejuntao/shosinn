package xjt.id.lizhi;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局ID生成器第一版<br>
 * 位数分布：符号位（1位，不使用），版本位（2位，固定为0），时间标志（秒级，30位），serverId（9位, 0-511），计数器（12位,
 * 0-4095），类型（7位, 0-127）
 *
 */
public class GuidBuilderV1 {

    public static final int VERSION = 0;

    private final AtomicLong idCounter = new AtomicLong();

    private final long timeFlag;
    private final int serverId;
    private final int counterLimit;
    private final int typeLimit;

    public GuidBuilderV1(int serverId) {
        Date old = baseTime();
        timeFlag = old.getTime();
        idCounter.set(0);

        // int limit = (1 << 9) - 1;
        // if (serverId > limit) {
        // throw new RuntimeException("Server id must be in range of 0-" +
        // limit);
        // }

        this.serverId = serverId;
        this.counterLimit = (1 << 12) - 1;
        this.typeLimit = (1 << 7) - 1;
    }

    private Date baseTime() {
        Calendar c = Calendar.getInstance();
        // Calendar中月从0开始，所以1月是0
        c.set(2013, 0, 1, 0, 0, 0);
        return c.getTime();
    }

    private long counter() {
        return idCounter.getAndIncrement() % (counterLimit + 1);
    }

    private long timeFlag() {
        return (System.currentTimeMillis() - timeFlag) / 1000L;
    }

    private int serverFlag() {
        return serverId;
    }

    /**
     * 生产一个ID<br>
     * ID长度为64位，具体位分布为：<br>
     * 位数分布：符号位（1位，不使用），版本位（2位，固定为0），时间标志（秒级，30位），serverId（9位, 0-511），计数器（12位,
     * 0-4095），类型（7位, 0-127）
     *
     * @param type
     *            对象类型
     * @deprecated
     */
    protected long nextId(int type) {
        if (type > typeLimit) {
            throw new RuntimeException("Type must be in range of 0-" + typeLimit);
        }

        return ((timeFlag() << 9 | serverFlag()) << 12 | counter()) << 7 | type;
    }

    /**
     * 生产一个ID<br>
     * ID长度为64位，具体位分布为：<br>
     * 位数分布：符号位（1位，不使用），版本位（2位，固定为0），时间标志（秒级，30位），serverId（9位, 0-511），计数器（12位,
     * 0-4095），类型（7位, 0-127）
     *
     * @param type
     *            对象类型
     */
    protected long nextId(ModuleType type) {
        if (type.getRaw() > typeLimit) {
            throw new RuntimeException("Type must be in range of 0-" + typeLimit);
        }

        return ((timeFlag() << 9 | serverFlag()) << 12 | counter()) << 7 | type.getRaw();
    }

    /**
     * 从ID重解出版本
     *
     * @param id
     * @return
     */
    public int extractVersion(long id) {
        return (int) (id >>> 61);
    }

    /**
     * 从ID中解出时间标志
     *
     * @param id
     * @return
     */
    public long extractTime(long id) {
        return id >>> 28;
    }

    /**
     * 从ID中解出服务器ID
     *
     * @param id
     * @return
     */
    public int extractServerId(long id) {
        return (int) (id << 36 >>> 55);
    }

    /**
     * 从ID中解出对象类型
     *
     * @param id
     * @return
     */
    public int extractType(long id) {
        return (int) id & 0x7F;
    }

    /**
     * 从ID中解出计数器
     *
     * @param id
     * @return
     */
    public int extractCounter(long id) {
        return (int) (id << 45 >>> 52);
    }

    /**
     * 从ID中解出时间标志并转换成实际的时间戳。 <font color="red">此方法解出的数据只精确到秒级，需要到毫秒级的，请使用
     * {@link extractRealTimeStampMillis}</font>
     *
     * @deprecated
     * @param id
     * @return
     * @see extractRealTimeStampMillis
     */
    @Deprecated
    public long extractRealTimeStamp(long id) {
        return (extractTime(id) * 1000L + timeFlag) / 1000L;
    }

    /**
     * 从ID中解出时间标志并转换成实际的时间戳（毫秒）</font>
     *
     * @param id
     * @return
     */
    public long extractRealTimeStampMillis(long id) {
        return extractTime(id) * 1000L + timeFlag;
    }
}
