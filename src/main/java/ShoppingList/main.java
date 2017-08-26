package ShoppingList;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Spark;

import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;

import static ShoppingList.JsonUtil.json;
import static spark.Spark.*;

class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}

/**
 * Created by jacob on 8/22/2017.
 */
public class main {
    static Gson gson = new Gson();
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String databaseUrl = "jdbc:h2:mem:account";
//        String databaseUrl = "jdbc:h2:test";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

        Dao<Store, String> storeDao = DaoManager.createDao(connectionSource, Store.class);
        Dao<Item, String> itemDao = DaoManager.createDao(connectionSource, Item.class);
        Dao<StoreItemJctn, String> storeItemJctnDao = DaoManager.createDao(connectionSource, StoreItemJctn.class);

        TableUtils.createTable(connectionSource, Store.class);
        TableUtils.createTable(connectionSource, Item.class);
        TableUtils.createTable(connectionSource, StoreItemJctn.class);


        Item item = new Item("toothbrush");
        Store store = new Store("Target");
        StoreItemJctn storeItemJctn = new StoreItemJctn(store, item);

        itemDao.create(item);
        storeDao.create(store);
        storeItemJctnDao.create(storeItemJctn);

        List<Item> items = itemDao.queryForAll();
        System.out.println(items.get(0).id);
        List<StoreItemJctn> jctns = storeItemJctnDao.queryForAll();
        StoreItemJctn jctn = jctns.get(0);
        itemDao.refresh(jctn.item);
        System.out.println(jctn.item.name);

        System.out.println(storeItemJctnDao.queryForAll().size());
        itemDao.delete(item);
        System.out.println(itemDao.queryForAll().size());
        System.out.println(storeItemJctnDao.queryForAll().size());


        port(8080);
        Spark.staticFileLocation("/public");
        get("/", (req, res) -> {
            main m = new main();
//            String r = m.getResource("../resources/static/home.html").getFile();
            String file = m.getClass().getClassLoader().getResource("./static/home.html").toString();
            FileReader reader = new FileReader(file);
            return reader.toString();
        });

        after((request, response) -> response.type("application/json"));
        path("/stores", () -> {

            get("/", (request, response) -> {
                        return storeDao.queryForAll();
                    },
                    json()
            );
            post("/", (req, res) -> {
                        Store newStore = gson.fromJson(req.body(), Store.class);
                        storeDao.create(newStore);
                        return "{\"success\": \"yes\"}";
                    }
            );
        });

    }
}
