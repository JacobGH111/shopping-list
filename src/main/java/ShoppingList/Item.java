package ShoppingList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Arrays;

@ApiModel(value="Item", description = "a single item")
@DatabaseTable(tableName = "items")
public class Item {
    /**
     * JDBC needs a no-arg constructor
     */
    public Item(){

    }

    @DatabaseField(generatedId = true)
    int id;

    @ApiModelProperty(notes = "The name of the item (e.g. toothpaste, bananans")
    @DatabaseField
    public String name;

    @ApiModelProperty(notes = "Whether or not the item is needed at this time")
    @DatabaseField
    public boolean needed;



    public Item(String name){
        this.name = name;
        this.needed = false;
    }

}
