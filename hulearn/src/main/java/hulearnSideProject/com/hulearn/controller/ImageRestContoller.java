package hulearnSideProject.com.hulearn.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hulearnSideProject.com.hulearn.dto.image.ImageDto;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.YN;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.mapper.PatientMapper;
import hulearnSideProject.com.hulearn.repository.TuserPatiBasRepository;
import hulearnSideProject.com.hulearn.repository.TuserPatiImgInfRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageRestContoller {

	private final TuserPatiImgInfRepository imgRepository;
	private final TuserPatiBasRepository patiRepository;
	
	private final String uploadDir = "C:/uploads";
	
	/**
	 * @content 전체이미지조회 삭제조건으로 조회
	 * 			delYn 이 null일 경우 전체 조회
	 * @param patiNo
	 * @return
	 */
	@GetMapping("/api/images/{patiNo}")
	public ResponseEntity<List<ImageDto>> getImagesByPatientDelYN(@PathVariable(name="patiNo") Integer patiNo
																,@RequestParam(name="delYn") YN delYn) {
		TuserPatiBas patient = patiRepository.findById(patiNo)
												.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));
		
		Stream<ImageDto> imageStream = patient.getImageList()
												.stream()
												.map(PatientMapper::toDto);

		if (delYn != null) {
			imageStream = imageStream.filter(image -> delYn.equals(image.getDelYn()));
		}

		List<ImageDto> result = imageStream.toList();
		
		return ResponseEntity.ok(result);
	}
	
	/**
	 * @content 이미지 업로드
	 * @param patiNo
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/api/images/upload/{patiNo}")
	public ResponseEntity<?> uploadImage(@PathVariable(name="patiNo") Integer patiNo,
										 @RequestParam("file") MultipartFile file) throws IOException {

		TuserPatiBas patient = patiRepository.findById(patiNo)
												.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

		if (file.isEmpty()) {
			throw new RuntimeException("파일이 없습니다");
		}

		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String originalFilename = file.getOriginalFilename();
		String newFileName = UUID.randomUUID() + "_" + originalFilename;
		String filePath = uploadDir + File.separator + newFileName;

		Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

		String imageUrl = "http://localhost:8080/uploads/" + newFileName;

		TuserPatiImgInf image = TuserPatiImgInf.builder()
												.pati(patient)
												.imgUrl(imageUrl)
												.imgNm(originalFilename)
												.delYn('N')
												.regrNo("KIMJAEWOO")
												.regPgmUrl("/api/images/upload")
												.regDts(LocalDate.now())
												.modrNo("KIMJAEWOO")
												.modPgmUrl("/api/images/upload")
												.modDts(LocalDate.now())
												.build();

		imgRepository.save(image);

		return ResponseEntity.ok(Map.of(
			"message", "업로드 성공",
			"imageUrl", imageUrl
		));
	}
}
