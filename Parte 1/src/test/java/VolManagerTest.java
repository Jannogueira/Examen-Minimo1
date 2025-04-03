import models.Avio;
import models.Vol;
import models.Maleta;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class VolManagerTest {
    VolManager vm;

    @Before
    public void setUp() {
        vm = new VolManagerImpl();
        vm.addAvio("A1", "A320", "Ryanair");
        vm.addAvio("A2", "A330", "Air France");
        vm.addAvio("A3", "B767", "Vueling");
        vm.addAvio("A4", "B565", "EasyJet");
    }

    @After
    public void tearDown() {
        this.vm = null;
    }

    @Test
    public void testAddAvio() {
        List<Avio> avions = vm.getAvioList();
        Assert.assertEquals("A1", avions.get(0).getId());
        Assert.assertEquals("A2", avions.get(1).getId());
        Assert.assertEquals("A3", avions.get(2).getId());
        Assert.assertEquals("A4", avions.get(3).getId());
    }

    @Test
    public void testAddVols() {
        Assert.assertEquals(0, vm.numVols());
        Avio avio1 = vm.getAvio("A1");
        Vol v = new Vol("V123", "08:30", "12:45", avio1, "Barcelona", "Madrid");
        vm.addVol(v);
        Assert.assertEquals(1, vm.numVols());
    }

    @Test
    public void testAfegirEquipage() {
        testAddVols();
        Assert.assertEquals(1, vm.numVols());
        Maleta m = new Maleta("David");
        Maleta m2 = new Maleta("Eduart");
        Maleta m3 = new Maleta("Miquel");
        Assert.assertEquals(0, vm.numEquipatge("V123"));
        vm.facturarMaleta("V123", m);
        Assert.assertEquals(1, vm.numEquipatge("V123"));
        vm.facturarMaleta("V123", m2);
        vm.facturarMaleta("V123", m3);
        Assert.assertEquals(3, vm.numEquipatge("V123"));
        List<Maleta> equipatge = vm.getEquipatge("V123");
        Assert.assertEquals("Miquel", equipatge.get(0).getPropietari());
        Assert.assertEquals("Eduart", equipatge.get(1).getPropietari());
        Assert.assertEquals("David", equipatge.get(2).getPropietari());
    }
}
