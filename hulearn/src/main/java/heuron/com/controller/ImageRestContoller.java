package heuron.com.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import heuron.com.config.FileSaveUtil;
import heuron.com.dto.image.ImageDto;
import heuron.com.entity.pati.TuserPatiBas;
import heuron.com.entity.pati.TuserPatiImgInf;
import heuron.com.hulearn.dto.ErrorResponse;
import heuron.com.mapper.PatientMapper;
import heuron.com.service.ImageService;
import heuron.com.service.PatientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageRestContoller {

	private final PatientService patientService;
	private final ImageService imageService;

	
	/**
	 * @content 전체이미지조회 삭제조건으로 조회
	 * 			delYn 이 null일 경우 전체 조회
	 * @param patiNo
	 * @return
	 */
	@GetMapping("/api/images/{patiNo}")
	public ResponseEntity<List<ImageDto>> getImagesByPatientDelYN(@PathVariable(name="patiNo") long patiNo
																,@RequestParam(name="delYn") Character delYn) {
		TuserPatiBas patient = patientService.findById(patiNo);
		
		Stream<ImageDto> imageStream = patient.getImageList()
												.stream()
												.map(PatientMapper::toDto);

		if (delYn != null) {
			imageStream = imageStream.filter(image -> delYn == image.getDelYn());
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
	public ResponseEntity<?> uploadImage(@PathVariable(name="patiNo") long patiNo,
										 @RequestParam("file") MultipartFile file) throws IOException {

		TuserPatiBas patient = patientService.findById(patiNo);
		TuserPatiImgInf image = new TuserPatiImgInf();
		try {
			String savedImageUrl = FileSaveUtil.saveFile(file);

			image = TuserPatiImgInf.builder()
													.pati(patient)
													.imgUrl(savedImageUrl)
													.imgNm(file.getOriginalFilename())
													.delYn('N')
													.regrNo("KIMJAEWOO")
													.regPgmUrl("/api/images/upload")
													.regDts(LocalDate.now())
													.modrNo("KIMJAEWOO")
													.modPgmUrl("/api/images/upload")
													.modDts(LocalDate.now())
													.build();

			imageService.save(image);
		} catch(Exception e) {
			ErrorResponse error = new ErrorResponse(e.getMessage(), 500);
			return ResponseEntity.status(500).body(error);
		}

		return ResponseEntity.ok(Map.of(
			"message", "업로드 성공",
			"imageUrl", image.getImgUrl()
		));
	}
}
