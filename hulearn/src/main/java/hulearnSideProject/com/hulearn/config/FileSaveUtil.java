package hulearnSideProject.com.hulearn.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.service.ImageService;

public class FileSaveUtil {
	
	private ImageService imageService;
	
	private final String uploadDir = "C:/uploads";
	
	public void save(MultipartFile[] files, TuserPatiBas patient) {
		
		if (files != null) {
			File dir = new File(uploadDir);
			if (!dir.exists()) dir.mkdirs();

			for (MultipartFile file : files) {
				if (file.isEmpty()) continue;

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
														.regPgmUrl("/patients/save")
														.regDts(LocalDate.now())
														.modrNo("KIMJAEWOO")
														.modPgmUrl("/patients/save")
														.modDts(LocalDate.now())
														.build();

				imageService.save(image);
			}
		}
	}
	
	public void save(MultipartFile file, TuserPatiBas patient) {
		
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

		imageService.save(image);
	}
}
