package info.apatrix.empiregarage.db;

public class Material {

    public static final String TABLE_NAME = "material";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MATERIAL_ID = "material_id";
    public static final String COLUMN_PACK_ID = "pack_id";

    private int id;
    private String material_id;
    private String pack_id;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_MATERIAL_ID + " TEXT,"
                    + COLUMN_PACK_ID + " TEXT"
                    + ")";

    public Material() {
    }

    public Material(int id, String material_id, String pack_id) {
        this.id = id;
        this.material_id = material_id;
        this.pack_id = pack_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }
}
