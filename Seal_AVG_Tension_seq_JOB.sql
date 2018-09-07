CREATE EVENT `Seal_AVG_Tension_seq_JOB`
ON SCHEDULE EVERY 10 MINUTE
DO BEGIN
		
	DECLARE seq_rt INT;
	DECLARE seq_avg INT;
		
	DECLARE TotalStr INT;
	DECLARE tension_avg INT;
	DECLARE counter INT DEFAULT 1;
	
	DECLARE sol_time VARCHAR(30);
	DECLARE eol_time VARCHAR(30);
	DECLARE sol_date VARCHAR(30);
	DECLARE eol_date VARCHAR(30);

	SELECT MAX(Sequence_Number) INTO seq_rt FROM RealTimeLog;
	SELECT MAX(Seq) INTO seq_avg FROM Seal_AVG_Tension_seq;

	SELECT MAX(str_number) INTO TotalStr from Seal_AVG_Tension_minute; 
	
	IF seq_avg is null THEN
		set seq_avg = 0;
	END IF;
		
	IF seq_rt != seq_avg+1  THEN

		SET seq_avg = seq_avg + 1;
		SELECT MIN(T0_Date) INTO sol_date FROM RealTimeLog WHERE Sequence_Number = seq_avg;	
		SELECT MAX(T0_Date) INTO eol_date FROM RealTimeLog WHERE Sequence_Number = seq_avg;
	
		SELECT MIN(T0_Time) INTO sol_time FROM RealTimeLog WHERE Sequence_Number = seq_avg and T0_Date = sol_date;	
		SELECT MAX(T0_Time) INTO eol_time FROM RealTimeLog WHERE Sequence_Number = seq_avg and T0_Date = eol_date;

		from_first_to_last: LOOP
			SELECT avg(Tension) into tension_avg from Seal_MetricLog where Streamer=counter and Date_ > concat(sol_date," ", sol_time) and Date_ < concat(eol_date," ", eol_time);
			/*Insert result*/		
			INSERT into Seal_AVG_Tension_seq(Date_, Seq, Str_number, AVG_Tension)VALUE(concat(sol_date," ", sol_time), seq_avg, counter, tension_avg);
			IF counter = TotalStr THEN 
				LEAVE from_first_to_last;
			END IF;
			SET counter = counter + 1;
		END LOOP from_first_to_last;
		/*DBLog rec*/
		INSERT INTO DB_Log(Date_, category, Message) VALUE(sysdate(6), 'I', 'Seal AVG Tension by seq');
	END IF;
	
END;