package ShoppingList;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;
import static spark.Spark.*;

/**
 * Created by jacob on 8/22/2017.
 */
public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String databaseUrl = "jdbc:h2:mem:account";
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
        get("/", (req, res) ->{
            main m = new main();
//            String r = m.getResource("../resources/static/home.html").getFile();
            String file = m.getClass().getClassLoader().getResource("./static/home.html").toString();
            FileReader reader = new FileReader(file);
            return reader.toString();
        });
    }
}
