CREATE
EVENT `CLEANING_JOB_cycle`
ON SCHEDULE EVERY 2 MINUTE 
	
DO BEGIN
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_Str_winch_cycle');
	DELETE FROM ODIM_Str_winch_cycle WHERE Date_ < adddate(sysdate(), INTERVAL -2 MINUTE);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_Str_winch_cycle');

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_Leadin_winch_cycle');
	DELETE FROM ODIM_Leadin_winch_cycle WHERE Date_ < adddate(sysdate(), INTERVAL -2 MINUTE);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_Leadin_winch_cycle');

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_WideTow_winch_cycle');
	DELETE FROM ODIM_WideTow_winch_cycle WHERE Date_ < adddate(sysdate(), INTERVAL -2 MINUTE);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_WideTow_winch_cycle');

END;