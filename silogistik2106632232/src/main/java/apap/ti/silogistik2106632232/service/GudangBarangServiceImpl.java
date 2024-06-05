package apap.ti.silogistik2106632232.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.dto.GudangBarangMapper;
import apap.ti.silogistik2106632232.model.GudangBarang;
import apap.ti.silogistik2106632232.repository.GudangBarangDb;

@Service
public class GudangBarangServiceImpl implements GudangBarangService {
    @Autowired
    GudangBarangDb gudangBarangDb;
    @Autowired
    GudangBarangMapper gudangBarangMapper;

    @Override
	public void saveGudangBarang(GudangBarang gudangBarang){
		gudangBarangDb.save(gudangBarang);
	}

    @Override
    public List<GudangBarang> getAllGudangBarang() {
        return gudangBarangDb.findAll();
    }

    @Override
    public GudangBarang getGudangBarangById(Long idGudangBarang) {
        for (GudangBarang gudangBarang : getAllGudangBarang()) {
			if (gudangBarang.getId().equals(idGudangBarang)) {
				return gudangBarang;
			}
		}	
		return null;
    }
}
