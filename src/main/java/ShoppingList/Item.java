package ShoppingList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Arrays;

@ApiModel(value="Item", description = "a single item")
public class Item {
    @ApiModelProperty(notes = "The name of the item (e.g. toothpaste, bananans")
    public String name;
    @ApiModelProperty(notes = "Whether or not the item is needed at this time")
    public boolean needed;
    @ApiModelProperty(notes = "The list of stores where this item is available")
    public ArrayList<Store> availableAt;

    public Item(String name, Store... availableAt){
        this.name = name;
        this.availableAt = new ArrayList<Store>(Arrays.asList(availableAt));
        this.needed = false;
    }

}
