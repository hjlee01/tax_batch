package com.hanwha.tax.batch.mydata.model;

import com.hanwha.tax.batch.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 쿠콘 마이데이터 공통부
 */
@Slf4j
@Getter
@Setter
@ToString
public abstract class AbstractMydataCoocon {

	// header 부 & tailer 부
	protected String 식별코드;
	protected String 파일구분;
	protected String 기관코드;
	protected String 생성일자;
	protected String 전송레코드수;

	/**
	 * ROW_TYPE 식별코드
	 */
	public static enum ROW_TYPE {
		헤더레코드부("ST"),
		데이터레코드부("CI"),
		테일레코드부("ED"),
		은행계좌목록조회("BA01"),
		은행수신계좌기본정보조회("BA02"),
		은행수신계좌추가정보조회("BA03"),
		은행수신계좌거래내역조회("BA04"),
		은행투자계좌기본정보조회("BA05"),
		은행투자계좌상세정보조회("BA06"),
		은행투자계좌거래내역조회("BA07"),
		은행대출계좌기본정보조회("BA08"),
		은행대출계좌추가정보조회("BA09"),
		은행대출계좌거래내역조회("BA10"),

		은행수신계좌매핑거래내역조회("BT01");
		private final String code;
		ROW_TYPE(String code){this.code = code;}
		public String getCode(){return this.code;}
		public boolean equals(String code){return this.code.equals(code);}
	}

	/**
	 * FILE_KIND 파일구분
	 */
	public static enum FILE_KIND {
		쿠콘("01"),
		이용기관("02");
		private final String code;
		FILE_KIND(String code){this.code = code;}
		public String getCode(){return this.code;}
		public boolean equals(String code){return this.code.equals(code);}
	}

	/**
	 * header 부 기본정보
	 */
	public AbstractMydataCoocon() {
		this.파일구분					= FILE_KIND.이용기관.getCode();
		this.기관코드					= "IS000001";
		this.생성일자					= Utils.getCurrentDate();		// 파일생성일자
	}

	/**
	 * header 공통부 파싱
	 * @param data
	 * @throws Exception 
	 */
	public void parseHeader(String data) {
		String[] headerArr = data != null ? data.split("|") : null;

		// header 부 검증
		if (headerArr == null || headerArr.length != 5 || !ROW_TYPE.헤더레코드부.getCode().equals(headerArr[0])) {
			log.info("헤더부 데이터를 확인해주시기 바랍니다.");
			return;
		}

		// header 부
		식별코드 = headerArr[0];
		파일구분 = headerArr[1];
		기관코드 = headerArr[2];
		생성일자 = headerArr[3];
	}

	/**
	 * tailer 부 파싱
	 * @param data
	 * @throws Exception 
	 */
	public void parseTailer(String data) {
		String[] tailerArr = data != null ? data.split("|") : null;

		// header 부 검증
		if (tailerArr == null || tailerArr.length != 3 || !ROW_TYPE.테일레코드부.getCode().equals(tailerArr[0])) {
			log.info("헤더부 데이터를 확인해주시기 바랍니다.");
			return;
		}

		// header 부
		식별코드 = tailerArr[0];
		전송레코드수 = tailerArr[1];
	}
	
	/**
	 * data 부 파싱
	 * @param data
	 * @throws Exception
	 */
	public abstract void parseData(String data);
}
