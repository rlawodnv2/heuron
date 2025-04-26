프로젝트 개요
간단한 게시판과 API 호출을 위한 기능을 구현한 프로젝트입니다.

DB: MySQL

Back-End: Spring Boot, Java 17, JPA

Front-End: Thymeleaf


아래는 주요 패키지 및 파일 설명입니다.

패키지명|파일명|설명
---|---|---|
controller|ImageController.java|이미지 컨트롤러 @Controller|
controller|ImageRestController.java|이미지 API 컨트롤러 @RestController|
controller|PatientController.java|환자 관련 컨트롤러 @Controller|
controller|PatientRestController.java|환자 관련 API 컨트롤러 @RestController|
---|---|---|
dto|---|JPA 조회시 |
dto.image|ImageDto.java|이미지 JPA 데이터셋을 위한 DTO package|
dto.image|PatientDto.java|환자 JPA DTO List<ImageDto> 포함|
---|---|---|
entity|---|---|
entity.pati|TuserPatiBas.java|환자정보 entity|
entity.pati|TuserPatiImgInf.java|환자 이미지 entity|
---|---|---|
mapper|---|Entity 객체(데이터베이스 테이블과 매핑된 객체)를 DTO 객체(전달 용도로 단순화된 객체)로 변환하는 역할|
mapper|PatientMapper|환자정보 전달|
---|---|---|
repository|---|JpaRepository를 사용하여 JPA 에서 제공되는 간단한 CRUD 처리|
repository|TuserPatiBasRepository.java|환자 JPA CRUD Repository 환자명으로 삭제처리(DelYn업데이트) 환자번호로 삭제처리 DelYn업데이트|
repository|TuserPatiImgInfRepository.java|환자 이미지 JPA CURD Repository 환자번호로 이미지 검색|
---|---|---|
service|---|비즈니스 로직 및 Repository와 Controller 연결|
service|PatientService.java|환자 저장 조회 삭제 조건, 삭제 처리|
service|ImageService.java|이미지 처리|
---|---|---|
config|---|handler와 config 포함
config|WebConfig.java|파일 경로 맵핑|
config|GlobalExceptionHandler.java|Exception 처리 Handler|
config|FileSaveUtil.java|파일 저장 Util|

처음으로 설명드릴 뷰 페이지 URL

설명({PathVariable}    @{requestParamter})

@Controller

ImageController

/images/upload/{patiNo} -> 이미지 업로드 화면

/image/delete/{imgNo}/{patiNo} -> 이미지 삭제

@RestController

ImageRestController

/api/images/{patiNo} | @{delYn} type enum YN -> 이미지 삭제 delYn 값이 null 일 경우 전체 조회 Y 및 N일 경우 조회처리

/api/images/upload/{patiNo} | @{file} type MultipartFile -> 이미지 업로드


@Controller

PatientController

/patient -> 환자 등록 html

/patients/save | @{patiNm} type String 

                 @{age} type int
		 
                 @{genCd} type enum Gender
		 
                 @{diseaseYn} type enum YN
		 
                 @{files} type MultipartFile -> 환자 정보 및 환자 이미지 저장
		 
/patient/{patiNo} | @{patiNo} type int -> 환자 정보 화면(조회)

/patients/update/{patiNo} | @{patiNm} type String 

                            @{age} type int
			    
                            @{genCd} type enum Gender
			    
                            @{diseaseYn} type enum YN
			    
                            @{files} type MultipartFile -> 환자 이미지 및 정보 수정
			    
/patients/delete/{patiNo} -> 환자 정보 삭제

/patients/list  | @{patiNm} type String -> 환자 검색

/patients/view/{patiNo} -> 환자 상세

@RestController

/api/patients/save @{TuserPatiBas} Type Entity -> 저장

/api/patients/findAll -> 전체검색

/api/patient/{patiNo} | @{delYn} -> 회원 조회 delYn 이 null일경우 전체조회

/api/patient/{patiNo} -> 회원삭제

/api/patient/update/{patiNo} -> 회원정보 수정


문제 사항:

enum을 적용할 시 index 오류가 발생하고 있음.

YN 값의 경우에는 enum -> Character로 다시 변경

GenCd 값의 경우에는 enum -> String으로 다시 변경


MYSQL TABLE SQL

DROP TABLE hulearn.TCOMM_GRP_CD_BAS;

DROP TABLE hulearn.TCOMM_CD_DTL;

DROP TABLE hulearn.TUSER_PATI_BAS;

DROP TABLE hulearn.TUSER_PATI_IMG_INF;

#공통 코드 그룹 

CREATE TABLE hulearn.TCOMM_GRP_CD_BAS (

	GRP_CD VARCHAR(10), #그룹코드
 
    GRP_CD_NM VARCHAR(100), #그룹코드명
    
    USE_YN CHAR, #사용여부
    
    REGR_NO VARCHAR(20) NOT NULL, #생성자 번호
    
    REG_PGM_URL VARCHAR(100) NOT NULL, #생성 프로그램 URL
    
    REG_DTS DATE NOT NULL, #생성 일자
    
    MODR_NO VARCHAR(20) NOT NULL, #수정자 번호
    
    MOD_PGM_URL VARCHAR(100) NOT NULL, #수정 프로그램 URL
    
    MOD_DTS DATE NOT NULL, #수정 일
    
    CONSTRAINT TCOMM_GRP_CD_BAS_PK PRIMARY KEY(GRP_CD)
    
);

CREATE TABLE hulearn.TCOMM_CD_DTL (

	GRP_CD VARCHAR(10), #그룹코드
 
    CD VARCHAR(10), #코드
    
    CD_NM VARCHAR(100), #코드명
    
    DISP_PRIR INT(3), #전시우선순위
    
    USE_YN CHAR, #사용여부
    
    REGR_NO VARCHAR(20) NOT NULL, #생성자 번호
    
    REG_PGM_URL VARCHAR(100) NOT NULL, #생성 프로그램 URL
    
    REG_DTS DATE NOT NULL, #생성 일자
    
    MODR_NO VARCHAR(20) NOT NULL, #수정자 번호
    
    MOD_PGM_URL VARCHAR(100) NOT NULL, #수정 프로그램 URL
    
    MOD_DTS DATE NOT NULL, #수정 일
    
    CONSTRAINT TCOMM_CD_DTL_PK PRIMARY KEY(GRP_CD, CD),
    
    FOREIGN KEY (GRP_CD) REFERENCES hulearn.TCOMM_GRP_CD_BAS (GRP_CD)
    
);

#이름, 나이, 성별, 질병 여부 

CREATE TABLE hulearn.TUSER_PATI_BAS (

	PATI_NO INT(11) NOT NULL AUTO_INCREMENT, #환자 번호
 
    PATI_NM VARCHAR(20) NOT NULL, #환자 명
    
    AGE INT(3) NOT NULL, #나이
    
    GEN_CD VARCHAR(10) NOT NULL, #성별
    
    DISEASE_YN CHAR NOT NULL, #질병 여부
    
    DEL_YN CHAR NOT NULL, #삭제 여부
    
    HP_NO VARCHAR(13), #핸드폰 번호
    
    REGR_NO VARCHAR(20) NOT NULL, #생성자 번호
    
    REG_PGM_URL VARCHAR(100) NOT NULL, #생성 프로그램 URL
    
    REG_DTS DATE NOT NULL, #생성 일자

    MODR_NO VARCHAR(20) NOT NULL, #수정자 번호
    
    MOD_PGM_URL VARCHAR(100) NOT NULL, #수정 프로그램 URL
    
    MOD_DTS DATE NOT NULL, #수정 일
    
    CONSTRAINT TUSER_PATI_BAS_PK PRIMARY KEY(PATI_NO)
    
);

#이미지 정보
CREATE TABLE hulearn.TUSER_PATI_IMG_INF (

	IMG_NO INT(11) NOT NULL AUTO_INCREMENT, # 이미지 번호
 
    PATI_NO INT(11) NOT NULL, # 환자 번호
    
    IMG_URL VARCHAR(500) NOT NULL, #이미지 URL
    
    IMG_NM VARCHAR(100) NOT NULL, #이미지명
    
    DEL_YN CHAR NOT NULL, #삭제 여부
    
    REGR_NO VARCHAR(20) NOT NULL, # 생성자 번호
    
    REG_PGM_URL VARCHAR(100) NOT NULL, # 생성 프로그램 URL
    
    REG_DTS DATE NOT NULL, # 생성 일자
    
    MODR_NO VARCHAR(20) NOT NULL, # 수정자 번호
    
    MOD_PGM_URL VARCHAR(100) NOT NULL, #수정 프로그램 URL
    
    MOD_DTS DATE NOT NULL, # 수정 일자
    
    CONSTRAINT TUSER_PATI_IMG_INF_PK PRIMARY KEY(IMG_NO),
    
    FOREIGN KEY (PATI_NO) REFERENCES hulearn.TUSER_PATI_BAS (PATI_NO)
    
);
