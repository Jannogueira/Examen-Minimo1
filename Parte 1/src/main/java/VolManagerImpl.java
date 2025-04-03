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
                logger.warn("L'avio" + avio.getId() + " ja existeix. Actualizant informació");
                avio.setModel(model);
                avio.setCompanyia(companyia);
                logger.info("Info de l'avio" +id +" actualitzada (model: "+model + " y companyia: " + companyia + ")");
                return;
            }
        }
        avioList.add(new Avio(id, model, companyia));
        logger.info("Avio" +id +" afegit");
    }
    @Override
    public void facturarMaleta(String idVol,Maleta maleta) {
            for (Vol vol : volList) {
                if (vol.getId().equals(idVol)) {
                    vol.afegirMaleta(maleta);
                    logger.info("La maleta de: " +maleta.getPropietari()+ " s'ha afegit al vol " +idVol);
                    return;
                }
            }
        logger.error("Error: El vol amb ID " + idVol + " no existeix a la llista.");
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
                    logger.warn("El vol" + vol.getId() + " ja existeix. Actualitzant informació");
                    volA.setHoraSortida(vol.getHoraSortida());
                    volA.setHoraArribada(vol.getHoraArribada());
                    volA.setAvio(vol.getAvio());
                    volA.setOrigen(vol.getOrigen());
                    volA.setDesti(vol.getDesti());
                    logger.info("Informació del vol" + vol.getId() + " actualitzada.");
                    return;
                }
            }
            volList.add(vol);
            logger.info("El vol amb ID " + vol.getId() + " s'ha afegit a la llista correctament");
        }
        else{
            logger.error("Error: L'avio amb ID " + vol.getAvio().getId() + " no existeix a la llista.");
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
        Vol vol = volList.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (vol == null) {
            logger.error("Vol amb ID: " + id + "no trobat.");
        }
        return vol;
    }

    @Override
    public Avio getAvio(String id) {
        Avio avio = avioList.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
        if(avio == null) {
            logger.error("Avio amb ID: " + id + "no trobat.");
        }
        return avio;
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
        logger.error("Error: El vol amb ID " + id + " no existeix a la llista.");
        return 0;
    }
    @Override
    public List<Maleta> getEquipatge(String idVol) {
        for(Vol vol : volList){
            if(vol.getId().equals(idVol)){
                return vol.getEquipatge();
            }
        }
        logger.error("Error: El vol amb ID " + idVol + " no existeix a la llista.");
        return null;
    }
}
