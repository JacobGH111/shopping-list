package ShoppingList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by jacob on 7/29/2017.
 */

@RestController
@RequestMapping(value = "/items")
@SpringBootApplication
@Api(value = "Items", description = "Operations for getting and modifying the list of items")
public class Items {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of potential items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List retrieved successfully")
    })
    public ArrayList<Item> items() {
        Store walmart = new Store("Walmart");
        Store walgreens = new Store("Walgreens");
        Store tjs = new Store("Trader Joes");
        Store bestBuy = new Store("Best Buy");
        ArrayList<Item> items = new ArrayList();
        items.add(new Item("toothbrush"));
        items.add(new Item("DVD player"));
        items.add(new Item("oranges"));

        return items;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Items.class, args);
    }
}
