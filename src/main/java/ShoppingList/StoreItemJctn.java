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

    public StoreItemJctn(Store store, Item item){
        this.store = store;
        this.item = item;
    }

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(foreign = true, columnDefinition = "integer references stores(id) on delete cascade")
    Store store;
    @DatabaseField(foreign = true, columnDefinition = "integer references items(id) on delete cascade")
    Item item;
}
