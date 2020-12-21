-- 사용자 계정 만들기
CREATE user alimi IDENTIFIED by bus;
-- 권한 부여
grant create session, create table, create sequence, create view to alimi;
-- 사용자 계정에 테이블 공간 설정
alter user alimi default tablespace users;
-- 테이블 공간에 쿼터 할당
alter user alimi quota unlimited on users;