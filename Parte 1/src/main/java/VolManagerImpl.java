import models.Avio;
import models.Vol;
import models.Maleta;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class VolManagerImpl implements VolManager {
    final static Logger logger = Logger.getLogger(VolManagerImpl.class);
    private List<Vol> volList;
    private List<Avio> avioList;


    public VolManagerImpl() {
        volList = new ArrayList<>();
        avioList = new ArrayList<>();
    }

    @Override
    public void addAvio(String id, String model, String companyia) {
        for (Avio avio : avioList) {
            if (avio.getId().equals(id)) {
                avio.setModel(model);
                avio.setCompanyia(companyia);
                return;
            }
        }
        avioList.add(new Avio(id, model, companyia));
    }
    @Override
    public void facturarMaleta(String idVol,Maleta maleta) {
            for (Vol vol : volList) {
                if (vol.getId().equals(idVol)) {
                    vol.afegirMaleta(maleta);
                    return;
                }
            }
        throw new IllegalArgumentException("Error: El vuelo con ID " + idVol + " no existe en la lista.");
    }

    @Override
    public void addVol(Vol vol) {
        boolean found = false;
        for (Avio avioA : avioList) {
            if (avioA.getId().equals(vol.getAvio().getId())) {
                found = true;
            }
        }
        if(found) {
            for (Vol volA : volList) {
                if (volA.getId().equals(vol.getId())) {
                    volA.setHoraSortida(vol.getHoraSortida());
                    volA.setHoraArribada(vol.getHoraArribada());
                    volA.setAvio(vol.getAvio());
                    volA.setOrigen(vol.getOrigen());
                    volA.setDesti(vol.getDesti());
                    return;
                }
            }
            volList.add(vol);
        }
        else{
            throw new IllegalArgumentException("Error: El avión con ID " + vol.getAvio().getId() + " no existe en la lista.");
        }
    }


    @Override
    public int numVols() {
        if (!volList.isEmpty())
            return volList.size();
        return 0;
    }

    @Override
    public Vol getVol(String id) {
        return volList.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Avio getAvio(String id) {
        return avioList.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<Avio> getAvioList() {
        return avioList;
    }

    @Override
    public int numEquipatge(String id){
        for(Vol vol : volList){
            if(vol.getId().equals(id)){
                return vol.getEquipatge().size();
            }
        }
        throw new IllegalArgumentException("Error: El vuelo con ID " + id + " no existe en la lista.");
    }
}
