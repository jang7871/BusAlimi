-- 아바타 테이블
CREATE TABLE avatar(
    ano NUMBER(2)
        CONSTRAINT AVAT_ANO_PK primary key,
    afile VARCHAR2(100 CHAR)
        CONSTRAINT AVAT_AFILE_UK UNIQUE
        CONSTRAINT AVAT_AFILE_NN NOT NULL,
    dir VARCHAR2(50 CHAR)
        CONSTRAINT AVAT_DIR_NN NOT NULL,
    len NUMBER
        CONSTRAINT AVAT_LEN_NN NOT NULL,
    gen CHAR(1)
        CONSTRAINT AVAT_GEN_NN NOT NULL
        CONSTRAINT AVAT_GEN_CK CHECK(gen IN ('M', 'W'))
);
-- 회원 테이블
CREATE TABLE member(
    mno NUMBER(4)
        CONSTRAINT MEMB_MNO_PK primary key,
    id VARCHAR2(20 CHAR)
        CONSTRAINT MEMB_ID_UK UNIQUE
        CONSTRAINT MEMB_ID_NN NOT NULL,
    pw VARCHAR2(30 CHAR)
        CONSTRAINT MEMB_PW_NN NOT NULL,
    name VARCHAR2(10 CHAR)
        CONSTRAINT MEMB_NAME_NN NOT NULL,
    mail VARCHAR2(50 CHAR)
        CONSTRAINT MEMB_MAIL_NN NOT NULL
        CONSTRAINT MEMB_MAIL_UK UNIQUE,
    joindate DATE default sysdate
        CONSTRAINT MEMB_JOIN_NN NOT NULL,
    isshow CHAR(1) default 'Y'
        CONSTRAINT MEMB_SHOW_NN NOT NULL
        CONSTRAINT MEMB_SHOW_CK CHECK (isshow IN ('Y', 'N')),
    ano NUMBER(2)
        CONSTRAINT MEMB_ANO_FK references avatar(ano)
        CONSTRAINT MEMB_ANO_NN NOT NULL,
    gen CHAR(1)
        CONSTRAINT MEMB_GEN_NN NOT NULL
        CONSTRAINT MEMB_GEN_CK CHECK(gen IN ('M', 'W'))
);
-- 게시판 테이블
CREATE TABLE board(
    bno NUMBER(5)
        CONSTRAINT BOARD_BNO_PK primary key,
    mno NUMBER(4)
        CONSTRAINT BOARD_MNO_FK references member(mno)
        CONSTRAINT BOARD_MNO_NN NOT NULL,
    body VARCHAR2(4000 CHAR)
        CONSTRAINT BOARD_BODY_NN NOT NULL,
    wdate DATE default sysdate
        CONSTRAINT BOARD_WDATE_NN NOT NULL,
    isshow CHAR(1) default 'Y'
        CONSTRAINT BOARD_SHOW_NN NOT NULL
        CONSTRAINT BOARD_SHOW_CK CHECK (isshow IN ('Y', 'N'))
);
-- 질문 테이블
CREATE TABLE quest(
    qno NUMBER(2)
        CONSTRAINT QUEST_QNO_PK primary key,
    quest VARCHAR2(50 CHAR)
        CONSTRAINT QUEST_QUEST_UK UNIQUE
        CONSTRAINT QUEST_QUEST_NN NOT NULL
);
-- 비번 수정 테이블
CREATE TABLE find(
    fno NUMBER(4)
        CONSTRAINT FIND_FNO_PK primary key,
    mno NUMBER(4)
        CONSTRAINT FIND_MNO_FK references member(mno)
        CONSTRAINT FIND_MNO_NN NOT NULL,
    qno NUMBER(2)
        CONSTRAINT FIND_QNO_FK references quest(qno)
        CONSTRAINT FIND_QNO_NN NOT NULL,
    answer VARCHAR2(20 CHAR)
        CONSTRAINT FIND_ANSWER_NN NOT NULL
);
-- 관할구역 테이블
CREATE TABLE district(
    district_cd NUMBER(1)
        CONSTRAINT DISTRICT_CD_PK primary key,
    region VARCHAR2(3 CHAR)
        CONSTRAINT DISTRICT_REGION_UK UNIQUE
        CONSTRAINT DISTRICT_REGION_NN NOT NULL
        CONSTRAINT DISTRICT_REGION_CK CHECK (region IN ('서울', '경기', '인천'))
);
-- 정류소 테이블
CREATE TABLE station(
    station_id NUMBER(9)
        CONSTRAINT STAT_ID_PK primary key,
    station_nm VARCHAR2(50 CHAR)
        CONSTRAINT STAT_NM_NN NOT NULL,
    x NUMBER
        CONSTRAINT STAT_X_NN NOT NULL,
    y NUMBER
        CONSTRAINT STAT_Y_NN NOT NULL,
    mobile_no VARCHAR2(5 CHAR),
    district_cd NUMBER(1)
        CONSTRAINT STAT_CD_FK references district(district_cd)
        CONSTRAINT STAT_CD_NN NOT NULL
);
-- 노선유형 테이블
CREATE TABLE routetype(
    route_cd NUMBER(2)
        CONSTRAINT ROUTETYPE_CD_PK primary key,
    route_tp VARCHAR2(12 CHAR)
        CONSTRAINT ROUTETYPE_TP_UK UNIQUE
    	CONSTRAINT ROUTETYPE_TP_NN NOT NULL
);
-- 노선 테이블
CREATE TABLE route(
    route_id NUMBER(9)
        CONSTRAINT ROUTE_ID_PK primary key,
    route_nm VARCHAR2(20 CHAR)
        CONSTRAINT ROUTE_NM_NN NOT NULL,
    route_cd NUMBER(2)
        CONSTRAINT ROUTE_CD_FK references routetype(route_cd)
        CONSTRAINT ROUTE_CD_NN NOT NULL,
    st_sta_id NUMBER(9)
        CONSTRAINT ROUTE_STSTAID_FK references station(station_id)
        CONSTRAINT ROUTE_STSTAID_NN NOT NULL,
    ed_sta_id NUMBER(9)
        CONSTRAINT ROUTE_EDSTAID_FK references station(station_id)
        CONSTRAINT ROUTE_EDSTAID_NN NOT NULL,
    up_first_time VARCHAR2(20 CHAR)
        CONSTRAINT ROUTE_UPFIRSTTIME_NN NOT NULL,
    up_last_time VARCHAR2(20 CHAR)
        CONSTRAINT ROUTE_UPLASTTIME_NN NOT NULL,
    down_first_time VARCHAR2(20 CHAR)
        CONSTRAINT ROUTE_DOWNFIRSTTIME_NN NOT NULL,
    dowm_last_time VARCHAR2(20 CHAR)
        CONSTRAINT ROUTE_DOWN_LASTTIME_NN NOT NULL,
    peek_alloc NUMBER(3)
        CONSTRAINT ROUTE_PEEK_NN NOT NULL,
    npeek_alloc NUMBER(3)
        CONSTRAINT ROUTE_NPEEK_NN NOT NULL,
    district_cd NUMBER(1)
        CONSTRAINT ROUTE_DISTRICTCD_FK references district(district_cd)
        CONSTRAINT ROUTE_DISTRICTCD_NN NOT NULL
);
-- 노선경유테이블
CREATE TABLE routestation(
    rsnum NUMBER(7)
        CONSTRAINT ROUTESTAT_RSNUM_PK primary key,
    route_id NUMBER(9)
        CONSTRAINT ROUTESTAT_ROUTEID_FK references route(route_id)
        CONSTRAINT ROUTESTAT_ROUTEID_NN NOT NULL,
    str_order NUMBER(4)
        CONSTRAINT ROUTESTAT_STRORDER_NN NOT NULL,
    station_id NUMBER(9)
        CONSTRAINT ROUTESTAT_STATIONID_FK references station(station_id)
        CONSTRAINT ROUTESTAT_STATIONID_NN NOT NULL,
    direction VARCHAR2(3 CHAR)
        CONSTRAINT ROUTESTAT_DIRECTION_NN NOT NULL
        CONSTRAINT ROUTESTAT_DIRECTION_CK CHECK (direction IN ('정', '역'))
);
-- 북마크 테이블
CREATE TABLE bookmark(
    bmno NUMBER(4)
        CONSTRAINT BOOK_BMNO_PK primary key,
    mno NUMBER(4)
        CONSTRAINT BOOK_MNO_FK references member(mno)
        CONSTRAINT BOOK_MNO_NN NOT NULL,
    route_id NUMBER(9)
        CONSTRAINT BOOK_ROUTEID_FK references route(route_id),
    station_id NUMBER(9)
        CONSTRAINT BOOK_STATIONID_FK references station(station_id),
    isshow CHAR(1) default 'Y'
        CONSTRAINT BOOK_SHOW_NN NOT NULL
        CONSTRAINT BOOK_SHOW_CK CHECK (isshow IN ('Y', 'N'))
);
------------------------------------------------------------------------
INSERT INTO
    routetype
VALUES(
    1, '공항'
);
INSERT INTO
    routetype
VALUES(
    2, '마을'
);
INSERT INTO
    routetype
VALUES(
    3, '간선'
);
INSERT INTO
    routetype
VALUES(
    4, '지선'
);
INSERT INTO
    routetype
VALUES(
    5, '순환'
);
INSERT INTO
    routetype
VALUES(
    6, '광역'
); 
commit;
-- 서울 정류소
CREATE TABLE seoulStation(
    station_id NUMBER(9)
        CONSTRAINT SSTA_ID_PK PRIMARY KEY,
    station_nm VARCHAR2(50 CHAR)
        CONSTRAINT SSTA_NM_NN NOT NULL,
    x NUMBER
        CONSTRAINT SSTA_X_NN NOT NULL,
    y NUMBER
        CONSTRAINT SSTA_Y_NN NOT NULL,
    mobile_no VARCHAR2(5 CHAR),
    district_cd NUMBER(1) DEFAULT 1
        CONSTRAINT SSTA_CD_FK REFERENCES district(district_cd)
        CONSTRAINT SSTA_CD_NN NOT NULL
);

-- 서울 노선
CREATE TABLE seoulRoute(
    route_id NUMBER(9)
        CONSTRAINT SRT_ID_PK PRIMARY KEY,
    route_nm VARCHAR2(20 CHAR)
        CONSTRAINT SRT_NM_NN NOT NULL,
    route_cd NUMBER(2)
        CONSTRAINT SRT_CD_FK REFERENCES routetype(route_cd)
        CONSTRAINT SRT_CD_NN NOT NULL,
    st_sta_nm VARCHAR2(50 CHAR)
        CONSTRAINT SRT_ST_NN NOT NULL,
    ed_sta_nm VARCHAR2(50 CHAR)
        CONSTRAINT SRT_ED_NN NOT NULL,
    up_first_time VARCHAR2(20 CHAR),
    up_last_time VARCHAR2(20 CHAR),
    peek_alloc NUMBER(3),
    district_cd NUMBER(1) DEFAULT 1
        CONSTRAINT SRT_DC_FK REFERENCES district(district_cd)
);

-- 서울 노선경유정류소
CREATE TABLE seoulRouteStation(
    rsnum NUMBER(7)
        CONSTRAINT SRST_NO_PK PRIMARY KEY,
    route_id NUMBER(9)
        CONSTRAINT SRST_RID_FK REFERENCES seoulroute(route_id)
        CONSTRAINT SRST_RID_NN NOT NULL,
    str_order NUMBER(4)
        CONSTRAINT SRST_ORDER_NN NOT NULL,
    station_id NUMBER(9)
        CONSTRAINT SRST_SID_FK REFERENCES seoulstation(station_id)
        CONSTRAINT SRST_SID_NN NOT NULL,
    direction VARCHAR2(50 CHAR),
    transYn CHAR(1)
        CONSTRAINT SRST_TYN_NN NOT NULL
        CONSTRAINT SRST_TYN_CK CHECK (transYn IN ('Y', 'N'))
);
-- 즐겨찾기 테이블 제약조건, 열 삭제
ALTER TABLE bookmark
        DROP CONSTRAINT BOOK_ROUTEID_FK;
ALTER TABLE bookmark
        DROP CONSTRAINT BOOK_STATIONID_FK;

-- 즐겨찾기 테이블 제약조건, 열 수정
ALTER TABLE bookmark
    ADD adddate DATE default sysdate
    CONSTRAINT BOOK_DATE_NN NOT NULL;

ALTER TABLE bookmark
    ADD area NUMBER(1)
        CONSTRAINT BOOK_AREA_FK REFERENCES district(district_cd)
        CONSTRAINT BOOK_AREA_NN NOT NULL;
        
-------------------------------------------------------------------
-- 친구 추가 테이블
CREATE TABLE friend(
    frno NUMBER(5)
        CONSTRAINT FRND_FRNO_PK PRIMARY KEY,
    id VARCHAR2(20 CHAR)
        CONSTRAINT FRND_ID_FK REFERENCES member(id)
        CONSTRAINT FRND_ID_NN NOT NULL,
    frid VARCHAR2(20 CHAR)
        CONSTRAINT FRND_FRID_FK REFERENCES member(id)
        CONSTRAINT FRND_FRID_NN NOT NULL,
    isshow CHAR default 'N'
        CONSTRAINT FRND_SHOW_NN NOT NULL
        CONSTRAINT FRND_SHOW_CK CHECK (isshow IN ('Y', 'N')),
    adddate DATE default sysdate
        CONSTRAINT FRND_DATE_NN NOT NULL
);

-- 메세지 테이블
CREATE TABLE message(
    msgno NUMBER(6)
        CONSTRAINT MEGE_MSNO_PK PRIMARY KEY,
    id VARCHAR2(20 CHAR)
        CONSTRAINT MEGE_ID_FK REFERENCES member(id)
        CONSTRAINT MEGE_ID_MN NOT NULL,
    reid VARCHAR2(20 CHAR)
        CONSTRAINT MEGE_REID_FK REFERENCES member(id)
        CONSTRAINT MEGE_REID_NN NOT NULL,
    msgcheck CHAR(1) default 'N'
        CONSTRAINT MEGE_MSCK_NN NOT NULL
        CONSTRAINT MEGE_MSCK_CK CHECK(msgcheck IN ('Y', 'N')),
    isshow CHAR(1) default 'Y'
        CONSTRAINT MEGE_SHOW_NN NOT NULL
        CONSTRAINT MEGE_SHOW_CK CHECK(isshow IN ('Y', 'N')),
    adddate DATE default sysdate
        CONSTRAINT MEGE_DATE_NN NOT NULL,
    message VARCHAR2(500 CHAR)
        CONSTRAINT MEGE_MESS_ NOT NULL
);
