package xjt.id.lizhi;

import java.util.Date;

/**
 * 全局ID生成器
 *
 */
public class GuidFactory {

    private final GuidBuilderV1 builderV1;
    private final GuidBuilderV2 builderV2;
    private final GuidBuilderV3 builderV3;

    public GuidFactory(int serverId) {
        builderV1 = new GuidBuilderV1(serverId);
        builderV2 = new GuidBuilderV2(serverId);
        builderV3 = new GuidBuilderV3(serverId);
    }

    /**
     * 生产一个ID
     *
     * @param type
     *            对象类型
     * @deprecated
     */
    public long nextId(int type) {
        return builderV3.nextId(type);
    }

    /**
     * 生产一个ID
     *
     * @param type
     *            对象类型
     */
    public long nextId(ModuleType type) {
        return builderV3.nextId(type);
    }

    /**
     * 生产一个ID
     *
     * @param type
     *            对象类型
     */
    public long nextId(ModuleType type, Date date) {
        return builderV3.nextId(type, date);
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
        if (extractVersion(id) == GuidBuilderV2.VERSION) {
            return builderV2.extractTime(id);
        } else if (extractVersion(id) == GuidBuilderV1.VERSION) {
            return builderV1.extractTime(id);
        } else {
            return builderV3.extractTime(id);
        }
    }

    /**
     * 从ID中解出服务器ID
     *
     * @param id
     * @return
     */
    public int extractServerId(long id) {
        if (extractVersion(id) == GuidBuilderV2.VERSION) {
            return builderV2.extractServerId(id);
        } else if (extractVersion(id) == GuidBuilderV1.VERSION) {
            return builderV1.extractServerId(id);
        } else {
            return builderV3.extractServerId(id);
        }
    }

    /**
     * 从ID中解出对象类型
     *
     * @param id
     * @return
     */
    public int extractType(long id) {
        if (extractVersion(id) == GuidBuilderV2.VERSION) {
            return builderV2.extractType(id);
        } else if (extractVersion(id) == GuidBuilderV1.VERSION) {
            return builderV1.extractType(id);
        } else {
            return builderV3.extractType(id);
        }
    }

    /**
     * 从ID中解出计数器
     *
     * @param id
     * @return
     */
    public int extractCounter(long id) {
        if (extractVersion(id) == GuidBuilderV2.VERSION) {
            return builderV2.extractCounter(id);
        } else if (extractVersion(id) == GuidBuilderV1.VERSION) {
            return builderV1.extractCounter(id);
        } else {
            return builderV3.extractCounter(id);
        }
    }

    /**
     * 从ID中解出时间标志并转换成实际的时间戳。 <font color="red">此方法解出的数据只精确到秒级，需要到毫秒级的，请使用
     * {@link #extractRealTimeStampMillis(long)} </font>
     *
     * @deprecated
     * @param id
     * @return
     * @see #extractRealTimeStampMillis(long)
     */
    @Deprecated
    public long extractRealTimeStamp(long id) {
        if (extractVersion(id) == GuidBuilderV2.VERSION) {
            return builderV2.extractRealTimeStamp(id);
        } else if (extractVersion(id) == GuidBuilderV1.VERSION) {
            return builderV1.extractRealTimeStamp(id);
        } else {
            return builderV3.extractRealTimeStamp(id);
        }
    }

    /**
     * 从ID中解出时间标志并转换成实际的时间戳（毫秒）</font>
     *
     * @param id
     * @return
     */
    public long extractRealTimeStampMillis(long id) {
        if (extractVersion(id) == GuidBuilderV2.VERSION) {
            return builderV2.extractRealTimeStampMillis(id);
        } else if (extractVersion(id) == GuidBuilderV1.VERSION) {
            return builderV1.extractRealTimeStampMillis(id);
        } else {
            return builderV3.extractRealTimeStampMillis(id);
        }
    }

}