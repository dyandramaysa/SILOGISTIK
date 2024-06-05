package apap.ti.silogistik2106632232.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.dto.GudangMapper;
import apap.ti.silogistik2106632232.model.Gudang;
import apap.ti.silogistik2106632232.model.GudangBarang;
import apap.ti.silogistik2106632232.repository.GudangBarangDb;
import apap.ti.silogistik2106632232.repository.GudangDb;

@Service
public class GudangServiceImpl implements GudangService{
    @Autowired
    GudangDb gudangDb;
    @Autowired
    GudangMapper gudangMapper;
    @Autowired
    GudangBarangService gudangBarangService;
    @Autowired
    GudangBarangDb gudangBarangDb;

    @Override
	public void saveGudang(Gudang gudang){
		gudangDb.save(gudang);
	}

    @Override
    public Long getJumlahGudang() {
        return gudangDb.count();
    }

    @Override
    public List<Gudang> getAllGudang() {
        return gudangDb.sortGudangByNamaLower();
    }

    @Override
    public Gudang getGudangById(Long idGudang) {
        for (Gudang gudang : getAllGudang()) {
			if (gudang.getId().equals(idGudang)) {
				return gudang;
			}
		}	
		return null;
    }

    @Override
    public void restockGudang(Gudang gudangFromDTO) {
        Gudang gudang = getGudangById(gudangFromDTO.getId());
        
        for (GudangBarang gudangBarangDTO : gudangFromDTO.getListGudangBarang()) {
            if (gudangBarangDTO.getId() !=null && gudangBarangService.getGudangBarangById(gudangBarangDTO.getId()) != null) {
                GudangBarang gudangBarang = gudangBarangService.getGudangBarangById(gudangBarangDTO.getId());
                gudangBarang.setStok(gudangBarangDTO.getStok());
                gudangBarangDb.save(gudangBarang);
            } else {
            gudangBarangDTO.setGudang(gudang);
            gudangBarangDb.save(gudangBarangDTO);
            }
        }   
    }
}