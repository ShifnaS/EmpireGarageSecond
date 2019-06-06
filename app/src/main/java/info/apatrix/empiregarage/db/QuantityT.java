package info.apatrix.empiregarage.db;

public class QuantityT {

    public static final String TABLE_NAME = "tbl_quantity";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PACK_ID = "pack_id";

    private int id;
    private String quantity;
    private String pack_id;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_QUANTITY + " TEXT,"
                    + COLUMN_PACK_ID + " TEXT"
                    + ")";

    public QuantityT() {
    }

    public QuantityT(int id, String quantity, String pack_id) {
        this.id = id;
        this.quantity = quantity;
        this.pack_id = pack_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }
}
