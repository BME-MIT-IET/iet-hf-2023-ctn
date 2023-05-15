import Logika.Virologus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeldaTest {
    static Virologus virologus;

    @BeforeEach
    public void init(){
        virologus = new Virologus();
    }

    @Test
    public void test1(){
        assertNotNull(virologus);
    }

    @Test
    public void test2(){
        assertNull(null);
    }
}