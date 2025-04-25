package hulearnSideProject.com.hulearn.dto.image;

import java.time.LocalDate;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.YN;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
	private Integer patrNo;
    private Integer imgNo;
    private String imgUrl;
    private String imgNm;
    private YN delYn;
    private LocalDate regDts;
}
