package apap.ti.silogistik2106632232.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106632232.dto.KaryawanMapper;
import apap.ti.silogistik2106632232.model.Karyawan;
import apap.ti.silogistik2106632232.repository.KaryawanDb;

@Service
public class KaryawanServiceImpl implements KaryawanService {
    @Autowired
    KaryawanDb karyawanDb;
    @Autowired
    KaryawanMapper karyawanMapper;

    @Override
	public void saveKaryawan(Karyawan karyawan){
		karyawanDb.save(karyawan);
	}

    @Override
	public List<Karyawan> getAllKaryawan() { 
        return karyawanDb.findAll(); 
    }

    @Override
    public Karyawan getKaryawanById(Long id) {
        for (Karyawan karyawan: getAllKaryawan()) {
            if (karyawan.getId().equals(id)) {
                return karyawan;
            }
        }

        return null;
    }

    @Override
    public Long getJumlahKaryawan() {
        return karyawanDb.count();
    }
}
