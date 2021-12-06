package xjt.id.lizhi;
public class GuidGenerator {

    private final GuidFactory factory;

    public GuidGenerator(int serverId) {
        factory = new GuidFactory(serverId);
    }

    public long extractRealTimeStampMillis(long id) {
        return factory.extractRealTimeStampMillis(id);
    }

    /**
     * 电台ID
     */
    public long genRadioId() {
        return factory.nextId(ModuleType.RADIO);
    }

    /**
     * 事件ID
     */
    public long genEventId() {
        return factory.nextId(ModuleType.EVENT);
    }

    /**
     * 头像ID
     */
    public long genAvatarId() {
        return factory.nextId(ModuleType.AVATAR);
    }

    /**
     * 头像缩略图ID
     */
    public long genAvatarThumbId() {
        return factory.nextId(ModuleType.AVATAR_THUMB);
    }

    /**
     * 电台封面ID
     */
    public long genRadioCoverId() {
        return factory.nextId(ModuleType.RADIO_COVER);
    }

    /**
     * 电台封面缩略图ID
     */
    public long genRadioCoverThumbId() {
        return factory.nextId(ModuleType.RADIO_COVER_THUMB);
    }

    /**
     * 节目ID
     */
    public long genProgramId() {
        return factory.nextId(ModuleType.PROGRAM);
    }

    /**
     * 节目封面ID
     */
    public long genProgCoverId() {
        return factory.nextId(ModuleType.PROGRAM_COVER);
    }

    /**
     * 节目音轨ID
     */
    public long genProgTrackId() {
        return factory.nextId(ModuleType.PROGRAM_TRACK);
    }

    /**
     * 反馈截图ID
     */
    public long genFeedbackId() {
        return factory.nextId(ModuleType.FEEDBACK);
    }

    /**
     * 节目封面缩略图ID
     */
    public long genProgCoverThumbId() {
        return factory.nextId(ModuleType.PROGRAM_COVER_THUMB);
    }

    /**
     * 设备消息ID
     */
    public long genDeviceEventId() {
        return factory.nextId(ModuleType.DEVICE_EVENT);
    }

    /**
     * 聊天消息ID
     */
    public long genChatMessageId() {
        return factory.nextId(ModuleType.CHAT_MESSAGE);
    }

    /**
     * 专题封面ID
     */
    public long genTopicCoverId() {
        return factory.nextId(ModuleType.TOPIC_COVER);
    }

    /**
     * 专题ID
     */
    public long genTopicId() {
        return factory.nextId(ModuleType.TOPIC);
    }

    /**
     * 专题元素ID
     */
    public long genTopicItemId() {
        return factory.nextId(ModuleType.TOPIC_ITEM);
    }

    /**
     * 标签ID
     */
    public long genTagId() {
        return factory.nextId(ModuleType.TAG);
    }

    /**
     * Banner ID
     */
    public long genBannerId() {
        return factory.nextId(ModuleType.BANNER);
    }

    /**
     * 页面ID
     */
    public long genPageId() {
        return factory.nextId(ModuleType.PAGE);
    }

    /**
     * 社区申请ID
     */
    public long genSnsPendingId() {
        return factory.nextId(ModuleType.SNS_PENDING);
    }

    /**
     * 社区信息ID
     */
    public long genSnsInfoId() {
        return factory.nextId(ModuleType.SNS_INFO);
    }

    /**
     * 社区帖子ID
     */
    public long genSnsFeedId() {
        return factory.nextId(ModuleType.SNS_FEED);
    }

    /**
     * 社区帖子评论ID
     */
    public long genSnsCommentId() {
        return factory.nextId(ModuleType.SNS_COMMENT);
    }

    /**
     * 社区封面ID
     */
    public long genSnsCoverId() {
        return factory.nextId(ModuleType.SNS_COVER);
    }

    /**
     * 收藏节目列表ID
     */
    public long genProgCollectionId() {
        return factory.nextId(ModuleType.PROGRAM_COLLECTION);
    }

    /**
     * 节目对应的收藏用户列表ID
     */
    public long genProgCollectorId() {
        return factory.nextId(ModuleType.PROGRAM_COLLECTOR);
    }

    /**
     * 好友ID
     */
    public long genFriendId() {
        return factory.nextId(ModuleType.FRIEND);
    }

    /**
     * 专辑ID
     */
    public long genAlbumId() {
        return factory.nextId(ModuleType.ALBUM);
    }

    /**
     * 专辑封面ID
     */
    public long genAlbumCoverId() {
        return factory.nextId(ModuleType.ALBUM_COVER);
    }

    /**
     * 资金账号ID
     */
    public long genAccountId() {
        return factory.nextId(ModuleType.ACCOUNT);
    }

    /**
     * 订单ID
     */
    public long genOrderId() {
        return factory.nextId(ModuleType.ORDER);
    }

    /**
     * 充值记录ID
     */
    public long genRechargeId() {
        return factory.nextId(ModuleType.RECHARGE);
    }

    /**
     * 商品ID
     */
    public long genProductId() {
        return factory.nextId(ModuleType.PRODUCT);
    }

    public long genWidgetTemplateId() {
        return factory.nextId(ModuleType.WIDGET_TEMPLATE);
    }

    public long genWidgetInstanceId() {
        return factory.nextId(ModuleType.WIDGET_INSTANCE);
    }

    /**
     * 交易记录ID
     */
    public long genTransactionId() {
        return factory.nextId(ModuleType.TRANSACTION);
    }

    /**
     * 动态ID
     */
    public long genMomentId() {
        return factory.nextId(ModuleType.MOMENT);
    }

    /**
     * 动态评论ID
     */
    public long genMomentCommentId() {
        return factory.nextId(ModuleType.MOMENT_COMMENT);
    }

    /**
     * 投稿ID
     */
    public long genManuscriptId() {
        return factory.nextId(ModuleType.MANUSCRIPT);
    }

    /**
     * 投稿音轨ID
     */
    public long genManuscriptTrackId() {
        return factory.nextId(ModuleType.MANUSCRIPT_TRACK);
    }

    /**
     * 加优申请ID
     */
    public long genQualityPendingId() {
        return factory.nextId(ModuleType.QUALITY_PENDING);
    }

    /**
     * 图片ID
     */
    public long genImageId() {
        return factory.nextId(ModuleType.IMAGE);
    }

    /**
     * 图片专辑ID
     */
    public long genImageAlbumId() {
        return factory.nextId(ModuleType.IMAGE_ALBUM);
    }

    /**
     * 电台认证ID
     */
    public long genRadioVerifyId() {
        return factory.nextId(ModuleType.RADIO_VERIFY);
    }

    /**
     * 用户ID
     */
    public long genUserId() {
        return factory.nextId(ModuleType.USER);
    }

    /**
     * 电台申请ID
     */
    public long genRadioPendingId() {
        return factory.nextId(ModuleType.RADIO_PENDING);
    }

    /**
     * 广告ID
     */
    public long genAdvertisingId() {
        return factory.nextId(ModuleType.ADVERTISING);
    }

    /**
     * 节目评论ID
     */
    public long genProgCommentId() {
        return factory.nextId(ModuleType.PROGRAM_COMMENT);
    }

    /**
     * 电台新分类ID
     */
    public long genRadioLabelId() {
        return factory.nextId(ModuleType.RADIO_LABEL);
    }

    /**
     * 主播津贴ID
     */
    public long genNjAllowanceId() {
        return factory.nextId(ModuleType.NJ_ALLOWANCE);
    }

    /**
     * 直播ID
     */
    public long genLiveId() {
        return factory.nextId(ModuleType.LIVE);
    }

    /**
     * 支付ID
     */
    public long genPaymentId() {
        return factory.nextId(ModuleType.PAYMENT);
    }

    /**
     * 运营后台
     */
    public long genOperationId() {
        return factory.nextId(ModuleType.OPERATION);
    }

    /**
     * 搜索
     */
    public long genSearchId() {
        return factory.nextId(ModuleType.SEARCH);
    }

    /**
     * 公开直播
     */
    public long genStudioId() {
        return factory.nextId(ModuleType.STUDIO);
    }

    /**
     * 抽奖
     */
    public long genLuckyDrawId() {
        return factory.nextId(ModuleType.LUCKY_DRAW);
    }
}

