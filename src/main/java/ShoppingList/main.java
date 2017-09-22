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
//        String databaseUrl = "jdbc:h2:mem:account";
        String databaseUrl = "jdbc:h2:./test";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

        Dao<Store, String> storeDao = DaoManager.createDao(connectionSource, Store.class);
        Dao<Item, String> itemDao = DaoManager.createDao(connectionSource, Item.class);
        Dao<StoreItemJctn, String> storeItemJctnDao = DaoManager.createDao(connectionSource, StoreItemJctn.class);

        TableUtils.createTableIfNotExists(connectionSource, Store.class);
        TableUtils.createTableIfNotExists(connectionSource, Item.class);
        TableUtils.createTableIfNotExists(connectionSource, StoreItemJctn.class);

        port(8080);
        String mode = System.getenv("mode");
        if(mode !=null && mode.equals("development")){
            System.out.println("Server is in development mode");
            staticFiles.externalLocation(System.getProperty("user.dir") + "/src/main/resources/public");
        }
        else {
            System.out.println("Server is in Production mode");
            Spark.staticFileLocation("/public");
        }
        get("/", (req, res) -> {
            main m = new main();
//            String r = m.getResource("../resources/static/home.html").getFile();
            String file = m.getClass().getClassLoader().getResource("./static/home.html").toString();
            FileReader reader = new FileReader(file);
            return reader.toString();
        });

        after((request, response) -> response.type("application/json"));
        path("/stores", () -> {
            get("/", (request, response) -> storeDao.queryForAll(), json());
            post("/", (req, res) -> {
                        Store newStore = gson.fromJson(req.body(), Store.class);
                        storeDao.create(newStore);
                        return "{\"message\": \"created\"}";
                    }
            );
        });
        path("/store/:id", () -> {
            get("/", (req, res) -> storeDao.queryForId(req.params(":id")), json());
        });

        path("/items", () -> {
            get("/", (request, response) -> itemDao.queryForAll(), json());
            post("/", ((request, response) -> {
                Item newItem = gson.fromJson(request.body(), Item.class);
                itemDao.create(newItem);
                return "{\"message\": \"created\"}";
            }));
        });
        path("/item/:id", () -> {
            get("/", (request, response) -> itemDao.queryForId(request.params(":id")), json());
        });

        path("storeitemjctns", () -> {
            get("/", (request, response ) -> storeItemJctnDao.queryForAll(), json());
            post("/", ((request, response) -> {
                StoreItemJctn jctn = gson.fromJson(request.body(), StoreItemJctn.class);
                storeItemJctnDao.create(jctn);
                return "{\"message\": \"created\"}";
            }));
        });
        path("/storeitemjctn/:id", () -> {
            get("/", (request, response) -> storeItemJctnDao.queryForId(request.params(":id")), json());
        });

//        Item i = new Item("toothbrush");
//        Store s = new Store("CVS");
//
//
//        itemDao.create(i);
//        storeDao.create(s);
//        StoreItemJctn j = new StoreItemJctn(s.id, i.id);
//        storeItemJctnDao.create(j);
    }
}
