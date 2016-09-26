package com.android.factorytest;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FactoryDatabase {

	// 数据库名称
	private static final String DATABASE_NAME = "factory_test.db";
	// 数据库版本号
	private static final int DATABASE_VERSION = 1;

	// 测试结果表名称
	public static final String TEST_RESULT_TABLE = "test_result";
	// 测试id号，自动生产，不重复
	public static final String ID = "_id";
	// 测试类名
	public static final String CLASS = "class";
	// 测试状态
	public static final String STATE = "state";
	// 测试所属位置，用于在显示测试报告时进行排序
	public static final String POSITION = "position";

	// 测试结果表栏目名称数组
	public static final String[] COLUMNS = {ID, CLASS, STATE, POSITION};

	private FactoryOpenHelper mFactoryDataHelper;
	private static FactoryDatabase mFactoryDatabase;

	public static final FactoryDatabase getInstance(Context context) {
		if (mFactoryDatabase == null) {
			mFactoryDatabase = new FactoryDatabase(context);
		}
		return mFactoryDatabase;
	}

	/**
	 * 获取测试项的测试结果
	 * @param className　测试项名称
	 * @return １表示测试通过，返回State.PASS；２表示测试失败，返回State.FAIL；没有或其他值表示没有测试，返回State.UNKNOWN
     */
	public TestItemInfo.State getTestState(String className) {
		SQLiteDatabase sd = mFactoryDataHelper.getReadableDatabase();
		TestItemInfo.State state = TestItemInfo.State.UNKNOWN;
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

	/**
	 * 获取所有测试项的测试状态
	 * @return	返回所有测试项测试结果的数组
     */
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
					TestItemInfo.State state = TestItemInfo.State.UNKNOWN;
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

	/**
	 * 设置测试项的测试结果
	 * @param index　测试项的下标
	 * @param className　测试项的类名
	 * @param state　测试项的测试结果
     * @return 如果设置成功，这返回数据库的插入位置；否则返回-1;
     */
	public long setTestState(int index, String className, TestItemInfo.State state) {
		long result = -1L;
		int s = (state == TestItemInfo.State.UNKNOWN ? 0 : (state == TestItemInfo.State.PASS ? 1 : 2));
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

	/**
	 * 清除测试结果表的所有内容
	 */
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