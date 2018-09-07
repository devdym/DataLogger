CREATE
EVENT `RVIMs_Usage_JOB`
ON SCHEDULE EVERY 1 DAY 
	STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY + INTERVAL 40 MINUTE)
DO BEGIN
	DECLARE TotalStr INT;
	DECLARE tension_avg INT;
	DECLARE RVIM_usage DOUBLE;
	DECLARE RVIM_SN INT;
	DECLARE RVIM_old_usage DOUBLE;
	DECLARE counter INT DEFAULT 1;
	SELECT MAX(str_number) INTO TotalStr from Seal_AVG_Tension_minute;
	
IF TotalStr IS NOT null THEN

	from_first_to_last: LOOP
		SELECT avg(Tension) into tension_avg from Seal_MetricLog where Streamer=counter and Date_ like concat(date_format(adddate(sysdate(), INTERVAL -1 day), "%Y-%m-%d"), '%');

		CASE 
			WHEN (tension_avg<17500) THEN 
				SET RVIM_usage = 1;
			WHEN (tension_avg>17500 AND tension_avg<2000) THEN 
				SET RVIM_usage = 1.2;
			WHEN (tension_avg>2000 AND tension_avg<2250) THEN 
				SET RVIM_usage = 1.5;
			WHEN (tension_avg>2250 AND tension_avg<2500) THEN 
				SET RVIM_usage = 2;
			WHEN (tension_avg>2500) THEN 
				SET RVIM_usage = 3;
		END CASE;		

		SELECT c.Section_sn INTO RVIM_SN FROM Streamer_conf a, SECTION c WHERE a.SECTION_ID = c.ID and a.Str_number = concat('Streamer ',counter);
		SELECT c.RVIM_Usage INTO RVIM_old_usage FROM Streamer_conf a, SECTION c WHERE a.SECTION_ID = c.ID and a.Str_number = concat('Streamer ',counter);
		
		SET RVIM_usage = (RVIM_usage * 100)/540;
		
		UPDATE SECTION SET RVIM_Usage = (RVIM_old_usage + RVIM_usage), Date_ = sysdate() WHERE Section_sn = RVIM_SN;
			
		IF counter = TotalStr THEN 
			LEAVE from_first_to_last;
		END IF;
		SET counter = counter + 1;
	END LOOP from_first_to_last;	
	/*DBLog rec*/
	INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'RVIM Usage is copmputed successfully');

END IF;

END;