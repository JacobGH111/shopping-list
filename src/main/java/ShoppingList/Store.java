package ShoppingList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jacob on 7/29/2017.
 */

@DatabaseTable(tableName = "stores")
public class Store {

    /**
     * JDBC needs a no-arg constructor
     */
    public Store(){

    }

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    public String name;

    public Store(String name){
        this.name = name;
    }
}
