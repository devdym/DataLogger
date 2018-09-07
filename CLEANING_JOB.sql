CREATE
EVENT `CLEANING_JOB`
ON SCHEDULE EVERY 1 DAY 
	STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY + INTERVAL 1 HOUR)
DO BEGIN
	INSERT INTO DB_Log(Date_, Category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_AVG_tension_minute');
	DELETE FROM ODIM_AVG_Tension_minute WHERE Date_ < adddate(sysdate(), INTERVAL -30 DAY);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_AVG_tension_minute');	

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from Seal_AVG_tension_minute');
	DELETE FROM Seal_AVG_Tension_minute WHERE Date_ < adddate(sysdate(), INTERVAL -30 DAY);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from Seal_AVG_tension_minute');	

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_Str_winch');
	DELETE FROM ODIM_Str_winch WHERE Date_ < adddate(sysdate(), INTERVAL -30 DAY);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_Str_winch');

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_Leadin_winch');
	DELETE FROM ODIM_Leadin_winch WHERE Date_ < adddate(sysdate(), INTERVAL -30 DAY);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_Leadin_winch');

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from ODIM_WideTow_winch');
	DELETE FROM ODIM_WideTow_winch WHERE Date_ < adddate(sysdate(), INTERVAL -30 DAY);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from ODIM_WideTow_winch');

	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'START - delete all data from Seal_MetricLog');
	DELETE FROM Seal_MetricLog WHERE Date_ < adddate(sysdate(), INTERVAL -30 DAY);
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'FINISH - delete all data from Seal_MetricLog');
END;