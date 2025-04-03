import models.Avio;
import models.Vol;
import models.Maleta;
import org.apache.log4j.Logger;

import java.util.List;

public interface VolManager {
    final static Logger logger = Logger.getLogger(VolManagerImpl.class);
    public void addAvio(String id, String model, String companyia);
    public void facturarMaleta(String idVol,Maleta maleta);
    public void addVol(Vol vol);
    public void addVol(String id, String horaSortida, String horaArribada, String idAvio, String origen, String desti);

    public int numVols();
    public List<Avio> getAvioList();
    public int numEquipatge(String id);
    public List<Maleta> getEquipatge(String idVol);

    Vol getVol(String id);

    Avio getAvio(String id);
}
