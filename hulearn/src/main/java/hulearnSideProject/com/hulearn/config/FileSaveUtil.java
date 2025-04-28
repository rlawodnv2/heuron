package hulearnSideProject.com.hulearn.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.service.ImageService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FileSaveUtil {

	private final static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

	private static boolean isAllowedFileType(MultipartFile file) {
		String contentType = file.getContentType();
		return contentType != null && (
				contentType.equals("image/jpeg") ||
				contentType.equals("image/jpg") ||
				contentType.equals("image/png") 
		);
	}

	public static String saveFile(MultipartFile file) {
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

		// 저장된 파일의 URL 리턴
		return "/uploads/" + newFileName;
	}
}
