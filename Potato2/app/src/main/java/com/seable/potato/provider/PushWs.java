package com.seable.potato.provider;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.seable.potato.common.Constants;

/**
 * @author 王维�?
 * @ClassName: PushWs
 * @Description: provider使用时需要的常量
 * @date 2015-01-20 17:15
 */
public class PushWs {


    public static final String AUTHORITY = Constants.IM_AUTHORITY;

    private PushWs() {
    }

    /**
     * 对话�?br>
     * 对话是指发生的交互动�?比如与某人的对话，这整个过程就是�?��thread<br>
     */
    public interface ThreadColumns {


        String ACCOUNT_NAME = "account_name";

        /**
         * 消息id
         * <P>Type: INT</P>
         */
        String MSG_ID = "msg_id";

        /**
         * 好友ID /�?��组ID
         * <P>Type: INT</P>
         */
        String BUDDY_ID = "buddy_id";

        /**
         * 消息内容
         * <P>Type: TEXT</P>
         */
        String MSG_BODY = "msg_body";

        /**
         * 收到消息时间
         * <P>Type: INT</P>
         */
        String MSG_RECEIVED_TIME = "msg_receive_time";

        /**
         * 消息发�?的时�?
         * <P>Type: INT</P>
         */
        String MSG_SENT_TIME = "msg_sent_time";

        /**
         * 未阅读数
         * <P>Type: INT</P>
         */
        String UNREAD_COUNT = "unread_count";

        /**
         * 发出消息的状�?
         * <P>Type: INT</P>
         */
        String MSG_OUTBOUND_STATUS = "msg_outbound_status";

        /**
         * 接受的消息标�?
         * <P>Type: INT</P>
         */
        String MSG_IS_INBOUND = "msg_is_inbound";

        /**
         * 消息类型
         * <P>Type: INT</P>
         */
        String MSG_TYPE = "msg_type";

        /**
         * 用户id
         */
        String USER_ID = "user_id";

    }

    /**
     * 事件�?br>
     *
     * @author songpeng
     */
    public static final class ThreadTable implements BaseColumns, ThreadColumns {
        private ThreadTable() {
        }

        /**
         * 此表的uri
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Pushthreads");

        public static final Uri CONTENT_URI_BY_BUDDY_ID = Uri.parse("content://" + AUTHORITY + "/PushthreadByBuddyId");

        /**
         * MIME type
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/Pushimps-threads";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/Pushimps-threads";


    }

    /**
     * 消息类型
     *
     * @author songpeng
     */
    public interface MessageType {

        /**
         * 发�?消息
         */
        int OUTGOINT = 0;
        /**
         * 接收的消�?
         */
        int INCOMING = 1;

        /**
         * 文本消息
         */
        int TYPE_MSG_TEXT = 3;

        /**
         * 音频消息
         */
        int TYPE_MSG_AUDIO = 5;

        /**
         * 图片消息
         */
        int TYPE_MSG_PIC = 4;


        /**
         * 单图文消�?
         */
        int TYPE_MSG_SINGLE = 1;

        /**
         * 多图文消�?
         */
        int TYPE_MSG_LIST = 2;

        /**
         * 单视频消�?
         */
        int TYPE_MSG_VEDIO = 6;

        /**
         * 发�?失败状�?
         */
        int MSG_STATUS_ERROR = -1;

        /**
         * 待发送状�?，尚未发送成�?
         */
        int MSG_STATUS_WAITING = 0;

        /**
         * 已发送状�?，发送成�?
         */
        int MSG_STATUS_SENT = 1;

        /**
         * 已阅读状�?
         */
        int MSG_STATUS_ARRIVED = 2;

        /**
         * 已阅读状�?
         */
        int MSG_STATUS_READED = 3;


        String MSG_TYPE_All = "('1','2','3','4','5')";

        String MSG_TYPE_CHAT = "('1','2','3')";

        String MSG_TYPE_HOME_NOTICE = "('4','5')";


    }

    /**
     * 消息�?
     *
     * @author songpeng
     */
    public interface MessageColumns {

        String ACCOUNT_NAME = "account_name";

        /**
         * 好友ID /�?��组ID
         * <P>Type: INT</P>
         */
        String BUDDY_ID = "buddy_id";

        /**
         * 消息内容
         * <P>Type: TEXT</P>
         */
        String BODY = "body";

        /**
         * 接受的消息标�?
         * <P>Type: INT</P>
         */
        String IS_INBOUND = "is_inbound";

        /**
         * 是否阅读标志
         * <P>Type: INT</P>
         */
        String IS_READ = "is_read";

        /**
         * 发出的消息的状�? 是否发出 是否被读etc -1 发�?失， 0 未发送成�?�? 1成功未接受，  2已读
         * <P>Type: INT</P>
         */
        String OUTBOUND_STATUS = "outbound_status";

        /**
         * 收到时间
         * <P>Type: INT</P>
         */
        String RECEIVED_TIME = "received_time";

        /**
         * 发�?者ID
         * <P>Type: INT</P>
         */
        String SENDER_ID = "sender_id";

        /**
         * 发�?时间
         * <P>Type: INT</P>
         */
        String SENT_TIME = "sent_time";

        /**
         * 消息类型
         * <P>Type: INT</P>
         */
        String TYPE = "type";

        /**
         * 发�?者设备id
         * <P>Type: INT</P>
         */
        String SENDER_DEVICE_ID = "sender_device_id";


        /**
         * 发�?者name
         * <P>Type: INT</P>
         */
        String SENDER_NAME = "sender_name";

    }

    /**
     * 存储消息的表<br>
     *
     * @author songpeng
     */
    public static final class MessageTable implements BaseColumns, MessageColumns {

        private MessageTable() {
        }

        /**
         * 此表的uri
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Pushmessages");

        /**
         * Content URL
         */
        public static final Uri CONTENT_URI_MESSAGES_BY_BUDDY_ID = Uri
                .parse("content://" + AUTHORITY + "/PushmessagesByBuddyId");

        /**
         * Group Content URL
         */
        public static final Uri CONTENT_URI_MESSAGES_BY_GROUP_ID = Uri
                .parse("content://" + AUTHORITY + "/PushmessagesByGroupId");

        /**
         * MIME TYPE
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/Pushimps-messages";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/Pushimps-messages";

        /**
         * 获得通过好友的id获得聊天记录的uri
         *
         * @param buddyId
         * @return
         */
        public static final Uri getContentUriByBuddyId(long buddyId) {
            Uri.Builder builder = CONTENT_URI_MESSAGES_BY_BUDDY_ID.buildUpon();
            ContentUris.appendId(builder, buddyId);
            return builder.build();
        }
    }

    /**
     * 附件类型<br>
     *
     * @author songpeng
     */
    public interface AttachmentType {

        String MIME_TYPE_AUDIO = "audio/";

        String MIME_TYPE_IMAGE_PNG = "image/png";

        String MIME_TYPE_IMAGE_JPGE = "image/jpg";
    }

    /**
     * 附件表的�?br>
     *
     * @author songpeng
     */
    public interface AttachmentColumns {

        /**
         * 附件id
         * <P>Type: INT</P>
         */
        String ATTACHMENT_ID = "attachment_id";

        /**
         * 附件类型
         * <P>Type: INT</P>
         */
        String MIME_TYPE = "mime_type";

        /**
         * 资源id 不知道小米�?么生成的?
         * <P>Type: INT</P>
         */
        String RESOURCE_ID = "resource_id";

        /**
         * 文件名称
         * <P>Type: TEXT</P>
         */
        String FILE_NAME = "filename";

        /**
         * 本地路径
         * <P>Type: INT</P>
         */
        String LOCAL_PATH = "local_path";

        /**
         * 文件大小
         * <P>Type: INT</P>
         */
        String FILE_SIZE = "file_size";

        /**
         * 状�?
         * <P>Type: INT</P>
         */
        String STATUS = "status";

        /**
         * 音频时间长度 �?
         * <P>Type: INT</P>
         */
        String AUDIO_LEN = "audio_len";

        /**
         * �?��的消息的id
         * <P>Type: INT</P>
         */
        String EXT_ID = "ext_id";

        /**
         * ??小米的发送json格式
         * <P>Type: TEXT</P>
         */
        String EXTENSION = "extension";
    }

    /**
     * 附件�?br>
     *
     * @author songpeng
     */
    public static final class AttachmentTable implements BaseColumns, AttachmentColumns {
        private AttachmentTable() {
        }

        /**
         * 此表的uri
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Pushattachments");

        /**
         * MIME TYPE
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/Pushimps-attachments";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/Pushimps-attachments";
    }

    public interface ContactColumns {
        String ACCOUNT_NAME = "account_name";
        String ID = "id";
        String NAME = "name";
        String TYPE = "type";
        String GROUP_ID = "group_id";
        String GROUP_NAME = "group_name";
        String AVATAR_URL = "avatar_url";
        String STATUS = "status";
        String CONTACT_TYPE = "contact_type";
    }

    public static final class ContactTable implements BaseColumns, ContactColumns {
        private ContactTable() {
        }

        public static final int TYPE_FRIEND = 1;
        public static final int TYPE_CLASS = 2;

        public static final String PATH = "Pushcontacts";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/Pushimps-contacts";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/Pushimps-contacts";

        public static final String TABLE_NAME = "Pushcontacts";
        public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "("
                + ACCOUNT_NAME + " TEXT,"
                + CONTACT_TYPE + " INTEGER,"
                + ID + " INTEGER,"
                + NAME + " TEXT,"
                + TYPE + " INTEGER,"
                + GROUP_ID + " INTEGER,"
                + GROUP_NAME + " TEXT,"
                + AVATAR_URL + " TEXT,"
                + STATUS + " TEXT"
                + ");";
    }

    public interface SettingColumns {
        String ACCOUNT_NAME = "account_name";
        String MESSAGE_NOTIFY = "message_notify";
        String MESSAGE_VOICE = "message_voice";
        String MESSAGE_VIBRATE = "message_vibrate";
        String STATUS_NOTIFY = "status_notify";
        String STATUS_VOICE = "status_voice";
        String STATUS_VIBRATE = "status_vibrate";
    }

    public static class SettingTable implements BaseColumns, SettingColumns {
        public static final String PATH = "Pushsettings";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/Pushimps-settings";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/Pushimps-settings";

        public static final String TABLE_NAME = "Pushsettings";
        public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "("
                + ACCOUNT_NAME + " TEXT UNIQUE,"
                + MESSAGE_NOTIFY + " integer DEFAULT 1,"
                + MESSAGE_VOICE + " INTEGER DEFAULT 1,"
                + MESSAGE_VIBRATE + " INTEGER DEFAULT 1,"
                + STATUS_NOTIFY + " integer DEFAULT 1,"
                + STATUS_VOICE + " INTEGER DEFAULT 1,"
                + STATUS_VIBRATE + " INTEGER DEFAULT 1"
                + ");";

    }


}
