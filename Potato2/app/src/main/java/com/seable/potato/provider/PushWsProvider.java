package com.seable.potato.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.seable.potato.provider.PushWs.AttachmentTable;
import com.seable.potato.provider.PushWs.ContactTable;
import com.seable.potato.provider.PushWs.MessageColumns;
import com.seable.potato.provider.PushWs.MessageTable;
import com.seable.potato.provider.PushWs.MessageType;
import com.seable.potato.provider.PushWs.SettingTable;
import com.seable.potato.provider.PushWs.ThreadColumns;
import com.seable.potato.provider.PushWs.ThreadTable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author 王维�?
 * @ClassName: PushWsProvider
 * @Description: Provider for push
 * @date 2015-01-20 17:20
 */
public class PushWsProvider extends ContentProvider {

    private static final String LOG_TAG = "PushWsProvider";
    private static final boolean DBG = true;

    private static final String TABLE_MESSAGES = "Pushmessages";
    private static final String TABLE_ATTACHMENTS = "Pushattachments";
    private static final String TABLE_THREADS = "Pushthreads";

    private static final String DATABASE_NAME = "Pushim.db";
    private static final int DATABASE_VERSION = 1;

    private static final int MATCH_MESSAGES = 1;
    private static final int MATCH_MESSAGES_BY_BUDDY_ID = 3;
    private static final int MATCH_MESSAGE = 4;
    private static final int MATCH_MESSAGES_BY_GROUP_ID = 5;
    private static final int MATCH_MESSAGES_BY_ID = 6;

    private static final int MATCH_THREADS = 9;
    private static final int MATCH_THREADS_BY_ID = 10;
    // private static final int MATCH_THREAD = 12;
    private static final int MATCH_THREAD_BY_BUDDY_ID = 13;

    private static final int MATCH_ATTACHMENTS = 20;
    private static final int MATCH_ATTACHMENTS_BY_ID = 21;

    private static final int MATCH_CONTACT = 30;

    private static final int MATCH_SETTINGS = 40;

    protected final UriMatcher mUrlMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // private static final HashMap<String, String> sMessagesProjectionMap;
    // private static final HashMap<String, String> sThreadsProjectionMap;
    // private static final HashMap<String, String> sAttachmentsProjectionMap;

    private DatabaseHelper mOpenHelper;
    private String mDatabaseName;
    private int mDatabaseVersion;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, mDatabaseName, null, mDatabaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_THREADS + "("
                    + "_id INTEGER PRIMARY KEY," + ThreadColumns.ACCOUNT_NAME
                    + " text," + ThreadColumns.MSG_ID + " INTEGER,"
                    + ThreadColumns.BUDDY_ID + " INTEGER,"
                    + ThreadColumns.MSG_BODY + " TEXT,"
                    + ThreadColumns.MSG_RECEIVED_TIME + " INTEGER DEFAULT 0,"
                    + ThreadColumns.MSG_SENT_TIME + " INTEGER DEFAULT 0,"
                    + ThreadColumns.UNREAD_COUNT + " INTEGER DEFAULT 0,"
                    + ThreadColumns.MSG_OUTBOUND_STATUS + " INTEGER DEFAULT 0,"
                    + ThreadColumns.MSG_IS_INBOUND + " INTEGER,"
                    + ThreadColumns.MSG_TYPE + " INTEGER,"
                    + ThreadColumns.USER_ID + " INTEGER DEFAULT 0" + ");");

            db.execSQL("CREATE TABLE " + TABLE_ATTACHMENTS + "("
                    + "_id INTEGER PRIMARY KEY," + "attachment_id INTEGER,"
                    + "mime_type TEXT," + "resource_id INTEGER,"
                    + "filename TEXT," + "local_path TEXT,"
                    + "file_size INTEGER," + "status INTEGER,"
                    + "audio_len INTEGER," + "ext_id INTEGER,"
                    + "extension TEXT" + ");");

            db.execSQL("CREATE TABLE " + TABLE_MESSAGES + "("
                    + "_id INTEGER PRIMARY KEY," + MessageColumns.ACCOUNT_NAME
                    + " text," + MessageColumns.BUDDY_ID + " INTEGER,"
                    + MessageColumns.BODY + " TEXT,"
                    + MessageColumns.IS_INBOUND + " INTEGER,"
                    + MessageColumns.IS_READ + " INTEGER,"
                    + MessageColumns.OUTBOUND_STATUS + " INTEGER,"
                    + MessageColumns.RECEIVED_TIME + " INTEGER DEFAULT 0,"
                    + MessageColumns.SENDER_ID + " INTEGER,"
                    + MessageColumns.SENT_TIME + " INTEGER,"
                    + MessageColumns.TYPE + " INTEGER,"
                    + MessageColumns.SENDER_NAME + " TEXT" + ");");
            db.execSQL(ContactTable.CREATE_TABLE_SQL);
            db.execSQL(SettingTable.CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            destroyOldTables(db);
            onCreate(db);
        }

        private void destroyOldTables(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTACHMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THREADS);
            db.execSQL("DROP TABLE IF EXISTS " + ContactTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SettingTable.TABLE_NAME);
        }

    }

    public PushWsProvider() {
        this(DATABASE_NAME, DATABASE_VERSION);
        setupImUrlMatchers(PushWs.AUTHORITY);
    }

    protected PushWsProvider(String databaseName, int databaseVersion) {
        this.mDatabaseName = databaseName;
        this.mDatabaseVersion = databaseVersion;
    }

    private void setupImUrlMatchers(String authority) {
        mUrlMatcher.addURI(authority, "Pushthreads", MATCH_THREADS);
        mUrlMatcher.addURI(authority, "Pushthreads/#", MATCH_THREADS_BY_ID);
        mUrlMatcher.addURI(authority, "PushthreadByBuddyId/#",
                MATCH_THREAD_BY_BUDDY_ID);

        mUrlMatcher.addURI(authority, "Pushmessages", MATCH_MESSAGES);
        mUrlMatcher.addURI(authority, "PushmessagesByBuddyId/#",
                MATCH_MESSAGES_BY_BUDDY_ID);
        mUrlMatcher.addURI(authority, "PushmessagesByGroupId/#",
                MATCH_MESSAGES_BY_GROUP_ID);
        mUrlMatcher.addURI(authority, "Pushmessages/#", MATCH_MESSAGES_BY_ID);

        mUrlMatcher.addURI(authority, "Pushattachments", MATCH_ATTACHMENTS);
        mUrlMatcher.addURI(authority, "Pushattachments/#",
                MATCH_ATTACHMENTS_BY_ID);

        mUrlMatcher.addURI(authority, ContactTable.PATH, MATCH_CONTACT);

        mUrlMatcher.addURI(authority, SettingTable.PATH, MATCH_SETTINGS);
    }

    @Override
    public String getType(Uri uri) {
        int match = mUrlMatcher.match(uri);
        switch (match) {
            case MATCH_THREADS:
                return ThreadTable.CONTENT_TYPE;
            case MATCH_THREADS_BY_ID:
                return ThreadTable.CONTENT_ITEM_TYPE;
            case MATCH_MESSAGES:
                return MessageTable.CONTENT_TYPE;
            case MATCH_ATTACHMENTS:
                return AttachmentTable.CONTENT_TYPE;
            case MATCH_ATTACHMENTS_BY_ID:
                return AttachmentTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknow URL");
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection,
                        final String selection, final String[] selectionArgs,
                        final String sortOrder) {
        return queryInternal(uri, projection, selection, selectionArgs,
                sortOrder);
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        Uri result;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            result = insertInternal(uri, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if (result != null) {
            getContext().getContentResolver().notifyChange(uri, null, false);
        }
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DBG) {
            log("url " + uri.toString());
        }
        int result;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            result = deleteInternal(uri, selection, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if (result > 0) {
            getContext().getContentResolver().notifyChange(uri, null, false);
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int result = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            result = updateInternal(uri, values, selection, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return result;
    }

    private int updateInternal(Uri url, ContentValues values, String selection,
                               String[] selectionArgs) {
        String tableToChanged = null;
        String changeItemId = null;
        String idColumnName = null;
        long buddyId = -1;
        long msgId = -1;

        boolean notifyThreadsContentUri = false;
        boolean notifyMessagesContentUri = false;

        StringBuilder whereClause = new StringBuilder();
        if (selection != null) {
            whereClause.append(selection);
        }

        int match = mUrlMatcher.match(url);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (match) {
            case MATCH_THREADS:
                tableToChanged = TABLE_THREADS;
                notifyThreadsContentUri = true;
                break;
            case MATCH_MESSAGES_BY_BUDDY_ID:
                tableToChanged = TABLE_THREADS;
                try {
                    buddyId = Long.parseLong(url.getPathSegments().get(1));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }

                appendWhere(whereClause, ThreadTable.BUDDY_ID, "=", buddyId);
                notifyThreadsContentUri = true;
                break;
            case MATCH_MESSAGES:
                tableToChanged = TABLE_MESSAGES;
                notifyMessagesContentUri = true;
                break;
            case MATCH_MESSAGES_BY_ID:
                tableToChanged = TABLE_MESSAGES;
                try {
                    msgId = Long.parseLong(url.getPathSegments().get(1));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }

                appendWhere(whereClause, MessageTable._ID, "=", msgId);
                notifyMessagesContentUri = true;
                break;
            case MATCH_CONTACT:
                tableToChanged = ContactTable.TABLE_NAME;
                break;
            case MATCH_SETTINGS:
                tableToChanged = SettingTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("can't update the url" + url);
        }

        if (idColumnName == null) {
            idColumnName = "_id";
        }

        int count = db.update(tableToChanged, values, whereClause.toString(),
                selectionArgs);

        if (count > 0) {
            ContentResolver cr = getContext().getContentResolver();
            if (notifyThreadsContentUri) {
                cr.notifyChange(ThreadTable.CONTENT_URI, null, false);
            }

            if (notifyMessagesContentUri) {
                cr.notifyChange(MessageTable.CONTENT_URI_MESSAGES_BY_BUDDY_ID,
                        null, false);
            }
        }

        return 0;
    }

    public Uri insertInternal(Uri uri, ContentValues values) {
        Uri resultUri = null;
        long rowId = 0;

        Uri notifyMessagesByBuddyIdUri = null;
        boolean notifyMessagesContentUri = false;
        boolean notifyMessagesByBuddyIdContentUri = false;
        boolean notifyThreadsContentUri = false;
        boolean notifyMessagesByGroupIdContentUri = false;

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = mUrlMatcher.match(uri);

        switch (match) {
            case MATCH_MESSAGES_BY_BUDDY_ID:
                appendValuesFromUrl(values, uri, MessageTable.BUDDY_ID);

            case MATCH_MESSAGES:
                notifyMessagesContentUri = true;
                notifyMessagesByBuddyIdContentUri = true;
                notifyMessagesByGroupIdContentUri = true;
//			int type = values.getAsInteger(MessageTable.TYPE);
                // if(type == MessageType.TYPE_MSG_HOMEWORK || type==
                // MessageType.TYPE_MSG_NOTICE )
                // {
                // ContentValues cv = createThreadContentValues(values,-1);
                // cv.put(ThreadTable.UNREAD_COUNT, 1);
                // db.insert(TABLE_THREADS, ThreadTable.BUDDY_ID, cv);
                // ContentResolver cr = getContext().getContentResolver();
                // cr.notifyChange(ThreadTable.CONTENT_URI, null);
                // return null;
                // }

                rowId = db.insert(TABLE_MESSAGES, null, values);
                if (rowId > 0) {
                    resultUri = Uri.parse(MessageTable.CONTENT_URI + "/" + rowId);
                    notifyThreadsContentUri = true;

                    String accountname = values
                            .getAsString(MessageTable.ACCOUNT_NAME);
                    long buddyid = values.getAsLong(MessageTable.BUDDY_ID);
                    String msgtype = "(" + MessageType.TYPE_MSG_TEXT + ","
                            + MessageType.TYPE_MSG_PIC + ","
                            + MessageType.TYPE_MSG_AUDIO + ","
                            + MessageType.TYPE_MSG_SINGLE + ","
                            + MessageType.TYPE_MSG_LIST + ","
                            + MessageType.TYPE_MSG_VEDIO + ")";
                    Cursor c = db.query(TABLE_THREADS, null,
                            ThreadTable.ACCOUNT_NAME + "=? and "
                                    + ThreadTable.BUDDY_ID + "=? and "
                                    + ThreadTable.MSG_TYPE + " in " + msgtype,
                            new String[]{accountname, String.valueOf(buddyid)},
                            null, null, null);
                    if (c != null && c.getCount() > 0) {
                        c.moveToFirst();
                        long id = c.getLong(c.getColumnIndex(ThreadTable._ID));
                        int un_read_count = c.getInt(c
                                .getColumnIndex(ThreadTable.UNREAD_COUNT));
                        db.update(TABLE_THREADS,
                                createThreadContentValues(values, ++un_read_count),
                                ThreadTable._ID + "=?",
                                new String[]{String.valueOf(id)});

                    } else {
                        db.insert(TABLE_THREADS, null,
                                createThreadContentValues(values, 1));
                    }
                    // db.replace(TABLE_THREADS, ThreadTable.BUDDY_ID,
                    // createThreadContentValues(values));
                }
                break;
            case MATCH_CONTACT:
                rowId = db.insert(ContactTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    resultUri = Uri.parse(ContactTable.CONTENT_URI + "/" + rowId);
                }
                break;
            case MATCH_SETTINGS:
                rowId = db.replace(SettingTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    resultUri = Uri.parse(ContactTable.CONTENT_URI + "/" + rowId);
                }
                break;
            default:
                throw new UnsupportedOperationException("Cannot insert into URL: "
                        + uri);
        }

        if (resultUri != null) {
            ContentResolver cr = getContext().getContentResolver();

            if (notifyMessagesContentUri) {
                cr.notifyChange(MessageTable.CONTENT_URI, null);
            }

            if (notifyThreadsContentUri) {
                cr.notifyChange(ThreadTable.CONTENT_URI, null);
            }

            if (notifyMessagesByBuddyIdContentUri) {
                cr.notifyChange(MessageTable.CONTENT_URI_MESSAGES_BY_BUDDY_ID,
                        null);
            }

            if (notifyMessagesByGroupIdContentUri) {
                cr.notifyChange(MessageTable.CONTENT_URI_MESSAGES_BY_GROUP_ID,
                        null);
            }
        }

        return resultUri;
    }

    private ContentValues createThreadContentValues(ContentValues values,
                                                    int unread_count) {
        ContentValues cv = new ContentValues();

        int is_inbound = values.getAsInteger(MessageTable.IS_INBOUND);

        cv.put(ThreadTable.ACCOUNT_NAME,
                values.getAsString(MessageTable.ACCOUNT_NAME));
        // ThreadTable ��buddyid �� MessageTable ��buddyid һ��
        cv.put(ThreadTable.BUDDY_ID, values.getAsLong(MessageTable.BUDDY_ID));
        cv.put(ThreadTable.MSG_BODY, values.getAsString(MessageTable.BODY));
        cv.put(ThreadTable.MSG_IS_INBOUND,
                values.getAsInteger(MessageTable.IS_INBOUND));
        cv.put(ThreadTable.MSG_TYPE, values.getAsInteger(MessageTable.TYPE));
        cv.put(ThreadTable.MSG_SENT_TIME,
                values.getAsLong(MessageTable.SENT_TIME));
        cv.put(ThreadTable.MSG_RECEIVED_TIME,
                values.getAsLong(MessageTable.RECEIVED_TIME));
        cv.put(ThreadTable.USER_ID, values.getAsLong(MessageTable.SENDER_ID));
        // δ����
        if (unread_count >= 0 && is_inbound == MessageType.INCOMING) {
            cv.put(ThreadTable.UNREAD_COUNT, unread_count);
        }

        return cv;
    }

    public Cursor queryInternal(Uri uri, String[] projection, String selection,
                                String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        StringBuilder whereClause = new StringBuilder();
        if (selection != null) {
            whereClause.append(selection);
        }

        String groupBy = null;
        String limit = null;

        int match = mUrlMatcher.match(uri);
        if (DBG) {
            log("query url " + uri + ", match " + match + ", where "
                    + selection);
            if (selectionArgs != null) {
                for (String arg : selectionArgs) {
                    log(" selectionArg:" + arg);
                }
            }
        }

        switch (match) {
            case MATCH_THREADS_BY_ID:
                appendWhere(whereClause, ThreadTable._ID, "=", uri
                        .getPathSegments().get(1));
            case MATCH_THREADS:
                qb.setTables(TABLE_THREADS);
                sortOrder = ThreadTable.MSG_SENT_TIME + " DESC";
                break;
            case MATCH_MESSAGES_BY_BUDDY_ID:
                appendWhere(whereClause, MessageTable.BUDDY_ID, "=", uri
                        .getPathSegments().get(1));
            case MATCH_MESSAGES:
                qb.setTables(TABLE_MESSAGES);
                break;
            case MATCH_MESSAGE:
                qb.setTables(TABLE_MESSAGES);
                appendWhere(whereClause, MessageTable._ID, "=", uri
                        .getPathSegments().get(1));
                break;
            case MATCH_CONTACT:
                qb.setTables(ContactTable.TABLE_NAME);
                break;
            case MATCH_SETTINGS:
                qb.setTables(SettingTable.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknow URL");
        }

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = null;

        try {
            c = qb.query(db, projection, whereClause.toString(), selectionArgs,
                    groupBy, null, sortOrder, limit);
            if (c != null) {
                c.setNotificationUri(getContext().getContentResolver(), uri);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "query db caugh ", e);
        }

        return c;
    }

    private int deleteInternal(Uri url, String userWhere, String[] whereArgs) {
        String tableToChange;

        String idColumnName = null;
        String changedItemId = null;

        boolean notifyMessagesByBuddyId = false;
        boolean notifyThreadsContentUri = false;
        boolean notifyMessagesByGroupId = false;

        StringBuilder whereClause = new StringBuilder();
        if (userWhere != null) {
            whereClause.append(userWhere);
        }

        int match = mUrlMatcher.match(url);

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (match) {
            case MATCH_THREADS:
                tableToChange = TABLE_THREADS;
                break;
            case MATCH_THREADS_BY_ID:
                tableToChange = TABLE_THREADS;
                changedItemId = url.getPathSegments().get(1);
                idColumnName = ThreadTable._ID;
                break;
            case MATCH_THREAD_BY_BUDDY_ID:
                tableToChange = TABLE_THREADS;
                changedItemId = url.getPathSegments().get(1);
                idColumnName = ThreadTable.BUDDY_ID;
                notifyThreadsContentUri = true;
                break;
            case MATCH_MESSAGES_BY_BUDDY_ID:
                tableToChange = TABLE_MESSAGES;
                changedItemId = url.getPathSegments().get(1);
                idColumnName = MessageTable.BUDDY_ID;
                notifyMessagesByBuddyId = true;
                break;
            case MATCH_MESSAGE:
                tableToChange = TABLE_MESSAGES;
                changedItemId = url.getPathSegments().get(1);
                notifyMessagesByBuddyId = true;
                notifyThreadsContentUri = true;
                break;
            case MATCH_MESSAGES:
                tableToChange = TABLE_MESSAGES;
                break;
            case MATCH_ATTACHMENTS:
                tableToChange = TABLE_ATTACHMENTS;
                break;
            case MATCH_CONTACT:
                tableToChange = ContactTable.TABLE_NAME;
                break;
            case MATCH_SETTINGS:
                tableToChange = SettingTable.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Can't delete the url");
        }

        if (idColumnName == null) {
            idColumnName = "_id";
        }

        if (changedItemId != null) {
            appendWhere(whereClause, idColumnName, "=", changedItemId);
        }

        if (DBG)
            log("delete from " + url + " WHERE  " + whereClause);

        int count = db.delete(tableToChange, whereClause.toString(), whereArgs);

        if (count > 0) {
            ContentResolver cr = getContext().getContentResolver();

            if (notifyMessagesByBuddyId) {
                cr.notifyChange(MessageTable.CONTENT_URI_MESSAGES_BY_BUDDY_ID,
                        null);
            }

            if (notifyThreadsContentUri) {
                cr.notifyChange(ThreadTable.CONTENT_URI, null, false);
            }

            if (notifyMessagesByGroupId) {
                cr.notifyChange(MessageTable.CONTENT_URI_MESSAGES_BY_GROUP_ID,
                        null, false);
            }
        }

        return count;
    }

    private void appendValuesFromUrl(ContentValues values, Uri url,
                                     String... columns) {
        if (url.getPathSegments().size() <= columns.length) {
            throw new IllegalArgumentException("Not enough values in url");
        }
        for (int i = 0; i < columns.length; i++) {
            if (values.containsKey(columns[i])) {
                throw new UnsupportedOperationException(
                        "Cannot override the value for " + columns[i]);
            }
            values.put(columns[i],
                    decodeURLSegment(url.getPathSegments().get(i + 1)));
        }
    }

    private static String decodeURLSegment(String segment) {
        try {
            return URLDecoder.decode(segment, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // impossible
            return segment;
        }
    }

    private static void appendWhere(StringBuilder where, String columnName,
                                    String condition, Object value) {
        if (where.length() > 0) {
            where.append(" AND ");
        }
        where.append(columnName).append(condition);
        if (value != null) {
            DatabaseUtils.appendValueToSql(where, value);
        }
    }

    static void log(String message) {
        Log.d(LOG_TAG, message);
    }
}
