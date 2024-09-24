import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

data class Video(val id: Int, val title: String, val uri: String)

class VideoDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "videos.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "videos"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_URI = "uri"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_URI TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertVideo(video: Video): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, video.title)
            put(COLUMN_URI, video.uri)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllVideos(): List<Video> {
        val videoList = mutableListOf<Video>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URI))
                videoList.add(Video(id, title, uri))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return videoList
    }
}
