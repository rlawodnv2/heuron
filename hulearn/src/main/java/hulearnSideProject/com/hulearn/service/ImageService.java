package hulearnSideProject.com.hulearn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.repository.TuserPatiImgInfRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final TuserPatiImgInfRepository imgRepository;

	public void save(TuserPatiImgInf image) {
		
		imgRepository.save(image);
	}

	public List<TuserPatiImgInf> findByPati_PatiNo(long patiNo) {
		
		List<TuserPatiImgInf> images = imgRepository.findByPati_PatiNo(patiNo)
														.stream().filter(img -> 'Y' != img.getDelYn()).toList();
		
		return images;
	}
	
}
