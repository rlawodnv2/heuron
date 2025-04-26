package hulearnSideProject.com.hulearn.dto.image;

import java.time.LocalDate;

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
	private long patrNo;
    private long imgNo;
    private String imgUrl;
    private String imgNm;
    private Character delYn;
    private LocalDate regDts;
}
