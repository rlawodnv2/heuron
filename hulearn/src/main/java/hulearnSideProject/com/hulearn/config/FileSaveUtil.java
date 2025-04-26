package hulearnSideProject.com.hulearn.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.service.ImageService;

@RequiredArgsConstructor
@Component
public class FileSaveUtil {

	private final ImageService imageService;

	private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

	private boolean isAllowedFileType(MultipartFile file) {
		String contentType = file.getContentType();
		return contentType != null && (
				contentType.equals("image/jpeg") ||
				contentType.equals("image/jpg") ||
				contentType.equals("image/png") 
		);
	}

	public void save(MultipartFile file, TuserPatiBas patient) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("빈 파일은 저장할 수 없습니다.");
		}

		if (!isAllowedFileType(file)) {
			throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. (jpeg, png만 허용)");
		}

		Path uploadPath = Paths.get(uploadDir);

		try {
			Files.createDirectories(uploadPath);
		} catch (IOException e) {
			throw new RuntimeException("업로드 디렉토리 생성 실패: " + uploadDir, e);
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || originalFilename.isBlank()) {
			throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
		}

		String newFileName = UUID.randomUUID() + "_" + originalFilename;
		Path targetPath = uploadPath.resolve(newFileName);

		try {
			Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패: " + newFileName, e);
		}

		String imageUrl = "/uploads/" + newFileName;

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

		imageService.save(image);
	}

	public void save(MultipartFile[] files, TuserPatiBas patient) {
		if (files != null) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					save(file, patient);
				}
			}
		}
	}
}
