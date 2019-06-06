package info.apatrix.empiregarage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;


    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        return this;
    }

    public void close() {
        dbHelper.close();
    }
    public long insertMaterial(String material_id,String pack_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Material.COLUMN_MATERIAL_ID, material_id);
        values.put(Material.COLUMN_PACK_ID, pack_id);

        long id = db.insert(Material.TABLE_NAME, null, values);

        db.close();
        return id;
    }
    public long insertQuantity(String quantity,String pack_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuantityT.COLUMN_QUANTITY, quantity);
        values.put(QuantityT.COLUMN_PACK_ID, pack_id);

        long id = db.insert(QuantityT.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    public String getMaterialID(String pack_id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+Material.TABLE_NAME+" where "+Material.COLUMN_PACK_ID+"=? ORDER BY "+Material.COLUMN_ID+" desc limit 1",new String[] {pack_id});

        if (cursor != null)
            cursor.moveToFirst();

        String material_id = cursor.getString(cursor.getColumnIndex(Material.COLUMN_MATERIAL_ID));
        cursor.close();

        return material_id;
    }
    public String getQuantity(String pack_id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+QuantityT.TABLE_NAME+" where "+QuantityT.COLUMN_PACK_ID+"=? ORDER BY "+QuantityT.COLUMN_ID+" desc limit 1",new String[] {pack_id});

        if (cursor != null)
            cursor.moveToFirst();

        String quantity = cursor.getString(cursor.getColumnIndex(QuantityT.COLUMN_QUANTITY));
        cursor.close();

        return quantity;
    }

    public void delete()
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("delete from "+ QuantityT.TABLE_NAME);
        db.execSQL("delete from "+ Material.TABLE_NAME);
        db.close();


    }
}
