package ShoppingList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jacob on 8/22/2017.
 */
@DatabaseTable(tableName = "store_item_jctn")
public class StoreItemJctn {

    public StoreItemJctn(){

    }

    public StoreItemJctn(int store_id, int item_id){
        this.store_id = store_id;
        this.item_id = item_id;
    }

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(columnDefinition = "integer references stores(id) on delete cascade")
    Integer store_id;
    @DatabaseField(columnDefinition = "integer references items(id) on delete cascade")
    Integer item_id;
}
