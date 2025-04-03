package edu.upc.dsa;

import edu.upc.dsa.exceptions.AvioNotFoundException;
import edu.upc.dsa.exceptions.VolNotFoundException;
import edu.upc.dsa.models.Vol;
import edu.upc.dsa.models.Avio;
import edu.upc.dsa.models.Maleta;


import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class VolManagerImpl implements VolManager {
    private static VolManager instance;
    private List<Vol> volList;
    private List<Avio> avioList;
    final static Logger logger = Logger.getLogger(VolManagerImpl.class);

    private VolManagerImpl() {
        this.volList = new LinkedList<>();
    }

    public static VolManager getInstance() {
        if (instance==null) instance = new VolManagerImpl();
        return instance;
    }

    public int sizeVols() {
        int ret = this.volList.size();
        logger.info("Vols " + ret);
        return ret;
    }
    public int sizeAvions(){
        int ret = this.avioList.size();
        logger.info("Avions " + ret);
        return ret;
    }

    public Vol addVol(Vol vol) {
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
                    logger.info("Informacio del vol" + vol.getId() + " actualitzada");
                    return vol;
                }
            }
            volList.add(vol);
            logger.info("El vol amb ID " + vol.getId() + " s'ha afegit a la llista");
        }
        else{
            logger.info("Error: L'avio amb ID " + vol.getAvio().getId() + " no existeix a la llista.");
        }
        return vol;
    }

    public Vol addVol(String id, String name){
        return this.addVol(id, name, 0);
    }

    public Vol addVol(String id, String name, double price) {
        return this.addVol(new Vol(id, name, price));
    }

    public Vol getVol(String id) {
        logger.info("getVol("+id+")");

        for (Vol v: this.volList) {
            if (v.getId().equals(id)) {
                logger.info("getVol("+id+"): "+v);

                return v;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    public Vol getVol2(String id) throws VolNotFoundException {
        Vol v = getVol(id);
        if (v == null) throw new VolNotFoundException();
        return v;
    }
    public Avio getAvio(String id) {
        logger.info("getAvio("+id+")");

        for (Avio a: this.avioList) {
            if (a.getId().equals(id)) {
                logger.info("getVol("+id+"): "+a);

                return a;
            }
        }

        logger.warn("not found " + id);
        return null;
    }
    public Avio getAvio2(String id) throws AvioNotFoundException {
        Avio a = getAvio(id);
        if (a == null) throw new AvioNotFoundException();
        return a;
    }

    public List<Vol> findAllVols() {
        return this.volList;
    }
    public List<Avio> findAllAvions() {
        return this.avioList;
    }

    @Override
    public void deleteVol(String id) {

        Vol v = this.getVol(id);
        if (v==null) {
            logger.warn("not found " + id);
        }
        else logger.info(v+" deleted ");
        this.volList.remove(v);
    }
    @Override
    public void deleteAvio(String id) {

        Avio a = this.getAvio(id);
        if (a==null) {
            logger.warn("not found " + id);
        }
        else logger.info(a+" deleted ");
        this.avioList.remove(a);
    }

    @Override
    public Vol updateVol(Vol v) {
        Vol t = this.getVol(v.getId());

        if (t!=null) {
            logger.info(v+" rebut!!!! ");

            t.setAvio(v.getAvio());
            t.setHoraSortida(v.getHoraSortida());
            t.setHoraArribada(v.getHoraArribada());
            t.setOrigen(v.getOrigen());
            t.setDesti(v.getDesti());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+v);
        }

        return t;
    }
    @Override
    public Avio updateAvio(Avio a) {
        Avio a1 = this.getAvio(a.getId());

        if (a1!=null) {
            logger.info(a1+" rebut!!!! ");

            a1.setCompanyia(a.getCompanyia());
            a1.setModel(a.getModel());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+v);
        }

        return t;
    }
    @Override
    public List<Maleta> getEquipatge(String idVol) {
        for(Vol vol : volList){
            if(vol.getId().equals(idVol)){
                return vol.getEquipatge();
            }
        }
        logger.info("Error: El vol amb ID " + idVol + " no existeix a la llista.");
        return null;
    }



    public void clear() {
        this.volList.clear();
        this.avioList.clear();
    }
}