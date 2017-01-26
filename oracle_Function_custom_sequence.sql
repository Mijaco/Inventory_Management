CREATE OR REPLACE function my_sequence(parameter varchar2)
return varchar2
is
v_level varchar2(255);
begin
 select parameter||LPAD(nvl(max(max_id),0)+1,4,0)into v_level from CUSTOM_SEQUENCE where substr(serial,1,8)=parameter;
 
return v_level;
exception when no_data_found then return null;
end;

## Table Structure

CREATE TABLE CUSTOM_SEQUENCE
(
  ID      NUMBER(10)                            NOT NULL,
  MAX_ID  NUMBER(10)                            NOT NULL,
  SERIAL  VARCHAR2(255 CHAR)                    NOT NULL
)