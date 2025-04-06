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
        this.avioList = new LinkedList<>();
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

    public int sizeEquipatge(String volId){
        for(Vol vol : volList){
            if(vol.getId().equals(volId)){
                return vol.getEquipatge().size();
            }
        }
        logger.error("Error: El vol amb ID " + volId + " no existeix a la llista.");
        return 0;
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

    public Vol addVol(String id, String horaSortida, String horaArribada, String idAvio, String origen, String desti) {
        Avio avio = getAvio(idAvio);
        return addVol(id, horaSortida, horaArribada, avio, origen, desti);
    }
    public Vol addVol(String id, String horaSortida, String horaArribada, Avio avio, String origen, String desti) {
        boolean found = false;
        Vol vol = new Vol(id, horaSortida, horaArribada, avio, origen, desti);
        for (Avio avioA : avioList) {
            if (avioA.getId().equals(avio.getId()))
                found = true;
        }
        if(found) {
            for (Vol volA : volList) {
                if (volA.getId().equals(id)) {
                    volA.setHoraSortida(horaSortida);
                    volA.setHoraArribada(horaArribada);
                    volA.setAvio(avio);
                    volA.setOrigen(origen);
                    volA.setDesti(desti);
                    logger.info("Informacio del vol" + id + " actualitzada");
                    return vol;
                }
            }
            volList.add(new Vol(id, horaSortida, horaArribada, avio, origen, desti));
            logger.info("El vol amb ID " + id + " s'ha afegit a la llista");
        }
        else{
            logger.info("Error: L'avio no existeix a la llista.");
        }
        return vol;
    }

    public Vol getVol(String id) {
        logger.info("getVol("+id+")");
        for (Vol v: this.volList) {
            if (v.getId().equals(id)) {
                logger.info("getVol("+id+"): "+v);

                return v;
            }
        }

        logger.warn("El vol amb ID:" + id + " No trobat");
        return null;
    }

    public Vol getVol2(String id) throws VolNotFoundException {
        Vol v = getVol(id);
        if (v == null) throw new VolNotFoundException();
        return v;
    }

    public Avio addAvio(String id, String model, String Companyia) {
        Avio avio = new Avio(id, model, Companyia);
        for (Avio avioA : avioList) {
            if (avioA.getId().equals(avio.getId())) {
                avioA.setModel(avio.getModel());
                avioA.setCompanyia(avio.getCompanyia());

                logger.info("Informacio de l'avio" + avio.getId() + " actualitzada");
                return avio;
            }
        }
        avioList.add(avio);
        logger.info("L'avio amb ID " + avio.getId() + " s'ha afegit a la llista");
        return avio;
    }

    public Avio addAvio(Avio avio) {
        return addAvio(avio.getId(), avio.getModel(), avio.getCompanyia());
    }



    public Avio getAvio(String id) {
        logger.info("getAvio("+id+")");

        for (Avio a: this.avioList) {
            if (a.getId().equals(id)) {
                logger.info("getVol("+id+"): "+a);
                return a;
            }
        }
        logger.warn("L'avio amb ID:" + id + " No trobat");
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
    public List<Avio> findAllAvions(){
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

            logger.info(a1+" updated ");
        }
        else {
            logger.warn("not found ");
        }

        return a1;
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
    public void facturarMaleta(String idVol,String Propietari) {
        Maleta maleta = new Maleta(Propietari);
        for (Vol vol : volList) {
            if (vol.getId().equals(idVol)) {
                vol.afegirMaleta(maleta);
                logger.info("La maleta de: " +maleta.getPropietari()+ " s'ha afegit al vol " +idVol);
                return;
            }
        }
        logger.error("Error: El vol amb ID " + idVol + " no existeix a la llista.");
    }


    public void clear() {
        this.volList.clear();
        this.avioList.clear();
    }
}