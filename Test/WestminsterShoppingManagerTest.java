import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {
    public static HashMap<String,Product> product_list = new HashMap<>();
    @BeforeEach
    void setUp() {
        WestminsterShoppingManager.product_list.clear();
    }

    @Test
    void AddProductToElectronics() {
        Electronics electronic_item1 = new Electronics("E001", "iPhone", 2, 1599, "Apple", 1);
        product_list.put("E001",electronic_item1);
        WestminsterShoppingManager.product_list.put("E001",electronic_item1);
        assertEquals(product_list.get("E001").getInfo(),WestminsterShoppingManager.product_list.get("E001").getInfo());

    }
    @Test
    void AddProductToClothing() {
        Clothing clothing_item1 = new Clothing("C001", "iPhone", 2, 1599, "L", "Black");
        product_list.put("C001", clothing_item1);
        WestminsterShoppingManager.product_list.put("C001",clothing_item1);
        assertEquals(product_list.get("C001").getInfo(),WestminsterShoppingManager.product_list.get("C001").getInfo());

    }
    @Test
    void DeleteProductFromElectronics(){
        product_list.remove("E001");
        WestminsterShoppingManager.product_list.remove("E001");
        assertEquals(product_list.get("E001"),WestminsterShoppingManager.product_list.get("EOO1"));
    }
    @Test
    void DeleteProductFromClothing(){
        product_list.remove("C001");
        WestminsterShoppingManager.product_list.remove("C001");
        assertEquals(product_list.get("C001"),WestminsterShoppingManager.product_list.get("COO1"));
    }

    @Test
    void CheckSaveInFile(){

    }

    @Test
    void LoadGUI(){
        User usertest = new User("John","JonDoe");
        WestminsterShoppingCentre sctest = new WestminsterShoppingCentre("Test",usertest);
        assertNotEquals(sctest,new WestminsterShoppingCentre("Test2",usertest));
    }
}

