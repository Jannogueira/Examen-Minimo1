package edu.upc.dsa;

import edu.upc.dsa.exceptions.AvioNotFoundException;
import edu.upc.dsa.exceptions.VolNotFoundException;
import edu.upc.dsa.models.Vol;
import edu.upc.dsa.models.Avio;
import edu.upc.dsa.models.Maleta;

import java.util.List;

public interface VolManager {


    public Vol addVol(String id, String horaSortida, String horaArribada, String idAvio, String origen, String desti);
    public Vol addVol(String id, String horaSortida, String horaArribada, Avio avio, String origen, String desti);
    public Vol addVol(Vol v);
    public Vol getVol(String id);
    public Vol getVol2(String id) throws VolNotFoundException;

    public Avio addAvio(String id, String model, String Companyia);
    public Avio addAvio(Avio avio);
    public Avio getAvio(String id);
    public Avio getAvio2(String id) throws AvioNotFoundException;

    public List<Vol> findAllVols();
    public List<Avio> findAllAvions();

    public void deleteVol(String id);
    public Vol updateVol(Vol v);
    public void deleteAvio(String id);
    public Avio updateAvio(Avio a);

    public List<Maleta> getEquipatge(String idVol);
    public void clear();
    public int sizeVols();
    public int sizeAvions();
    public int sizeEquipatge(String volId);
}
