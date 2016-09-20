package com.android.factorytest;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FactoryDatabase {

	private static final String DATABASE_NAME = "factory_test.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TEST_RESULT_TABLE = "test_result";
	public static final String ID = "_id";
	public static final String CLASS = "class";
	public static final String STATE = "state";
	public static final String POSITION = "position";

	public static final String[] COLUMNS = {ID, CLASS, STATE, POSITION};

	private FactoryOpenHelper mFactoryDataHelper;
	private static FactoryDatabase mFactoryDatabase;

	public static final FactoryDatabase getInstance(Context context) {
		if (mFactoryDatabase == null) {
			mFactoryDatabase = new FactoryDatabase(context);
		}
		return mFactoryDatabase;
	}

	public TestItemInfo.State getTestState(String className) {
		SQLiteDatabase sd = mFactoryDataHelper.getReadableDatabase();
		TestItemInfo.State state = TestItemInfo.State.UNKNOW;
		String[] cols = new String[]{CLASS, STATE};
		String[] selectionArgs = new String[]{className};
		Cursor c = sd.query(TEST_RESULT_TABLE, cols, CLASS + "=?", selectionArgs, null, null, null);
		Log.d(this, "getTestState=>className: " + className + " count: " + (c != null ? c.getCount() : 0));
		if (c != null) {
			if (c.getCount() > 0) {
				c.moveToFirst();
				int s = c.getInt(c.getColumnIndexOrThrow(STATE));
				if (s == 1) {
					state = TestItemInfo.State.PASS;
				} else if (s == 2) {
					state = TestItemInfo.State.FAIL;
				}
			}
			c.close();
		}
		Log.d(this, "getTestState=>className: " + className + " state: " + state);
		return state;
	}

	public ArrayList<ItemState> getAllTestState() {
		ArrayList<ItemState> list = new ArrayList<ItemState>();
		SQLiteDatabase sd = mFactoryDataHelper.getReadableDatabase();
		String[] cols = new String[]{CLASS, STATE, POSITION};
		String[] selectionArgs = new String[]{CLASS};
		Cursor c = sd.query(TEST_RESULT_TABLE, cols, null, null, null, null, null);
		if (c != null) {
			if (c.getCount() > 0) {
				String className = "";
				int s = 0;
				int index = -1;
				while (c.moveToNext()) {
					TestItemInfo.State state = TestItemInfo.State.UNKNOW;
					className = c.getString(c.getColumnIndexOrThrow(CLASS));
					s = c.getInt(c.getColumnIndexOrThrow(STATE));
					index = c.getInt(c.getColumnIndexOrThrow(POSITION));
					if (s == 1) {
						state = TestItemInfo.State.PASS;
					} else if (s == 2) {
						state = TestItemInfo.State.FAIL;
					}
					ItemState item = new ItemState();
					item.mClassName = className;
					item.mState = state;
					item.mIndex = index;
					list.add(item);
				}
			}
			c.close();
		}
		return list;
	}

	public long setTestState(int index, String className, TestItemInfo.State state) {
		long result = -1L;
		int s = (state == TestItemInfo.State.UNKNOW ? 0 : (state == TestItemInfo.State.PASS ? 1 : 2));
		SQLiteDatabase sd = mFactoryDataHelper.getWritableDatabase();
		String[] cols = new String[]{CLASS, STATE};
		String[] selectionArgs = new String[]{className};
		ContentValues cv = new ContentValues();
		cv.put(CLASS, className);
		cv.put(POSITION, index);
		cv.put(STATE, s);
		Cursor c = sd.query(TEST_RESULT_TABLE, cols, CLASS + "=?", selectionArgs, null, null, null);
		Log.d(this, "setTestState=>count: " + (c != null ? c.getCount() : 0));
		if (c != null && c.getCount() > 0) {
			c.close();
			String[] whereArgs = new String[]{className};
			result = sd.update(TEST_RESULT_TABLE, cv, CLASS + "=?", whereArgs);
		} else {
			c.close();
			result = sd.insert(TEST_RESULT_TABLE, CLASS, cv);
		}
		Log.d(this, "setTestState=>resutl: " + result + " index: " + index + " className: " + className + " state: " + state);
		return result;
	}

	public void clearAllData() {
		SQLiteDatabase sd = mFactoryDataHelper.getWritableDatabase();
		sd.execSQL("delete from " + TEST_RESULT_TABLE + ";");
		sd.execSQL("update sqlite_sequence SET seq = 0 where name =\'" + TEST_RESULT_TABLE + "\';");
	}

	private FactoryDatabase(Context context) {
		mFactoryDataHelper = new FactoryOpenHelper(context);
	}

	class FactoryOpenHelper extends SQLiteOpenHelper {

		public FactoryOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TEST_RESULT_TABLE
					+ " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ CLASS + " TEXT,"
					+ STATE + " INTEGER,"
					+ POSITION + " INTEGER);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TEST_RESULT_TABLE);
			onCreate(db);
		}
	}

	class ItemState {
		String mClassName;
		TestItemInfo.State mState;
		int mIndex;
	}
}