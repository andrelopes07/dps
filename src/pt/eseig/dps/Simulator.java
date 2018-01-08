package pt.eseig.dps;

import pt.eseig.dps.controllers.AntennaController;
import pt.eseig.dps.controllers.TagController;
import pt.eseig.dps.models.Reading;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * The class pt.eseig.dps.Simulator generates a file with several entries as antenna readings
 * @author Ricardo
 *
 */
public class Simulator {

	private static LocalDate currentDay  = LocalDate.now();
	private static LocalDate currentDate = LocalDate.of(currentDay.getYear(), currentDay.getMonth(), currentDay.getDayOfMonth());
	
	private static final String FILENAME = "./mockDB/simulator.txt";
	private static final int FIRST_YEAR = 2014;
	private static final LocalDate CURRENT_DATE = currentDate;
	private static long beginTime = Timestamp.valueOf(FIRST_YEAR + "-01-01 00:00:00").getTime();
	private static long endTime = Timestamp.valueOf(CURRENT_DATE + " 23:59:59").getTime();
	
	/**
	 * Generate file with the following format
	 * date(10)hour(8)numAntena(3)numEtiqueta(4)
	 * @param numEntries number of lines of the file
	 */
	public static void generateFile(int numEntries) {

		Map<Long, Reading> registers = new TreeMap<>();
		PrintWriter out = null;
		try {
			out = new PrintWriter(FILENAME);
			for (int i = 1; i <= numEntries; i++) {
				Long ld = getRandomTimeBetweenTwoDates();				
				Reading reg = new Reading(ld, getNumAntena(), getNumEtiqueta());
				registers.put(ld, reg);
			}
			for (Entry<Long, Reading> entry : registers.entrySet()) {
				Reading r =  entry.getValue();
				out.println(formatLongToDateTime(r.getDateTime()) +  "==" + r.getAntennaId() + "==" + r.getTagId());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		finally{
			if(out!=null) {
				out.close();
			}
		}	
	}	
	
	private static long getRandomTimeBetweenTwoDates() {
	    long diff = endTime - beginTime + 1;
	    return beginTime + (long) (Math.random() * diff);
	}
	
	private static String formatLongToDateTime(Long date) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");
	    return dateFormat.format(date);
	}
	
	/**
	 * Get the antenna id
	 * @return the antenna id in string format
	 */
	private static String getNumAntena() {
		int num = randBetween(1, AntennaController.getAntennas().size());
        return String.format("%03d", num);
	}
	
	/**
	 * Get the tag id
	 * @return the tag id in string format
	 */
	private static String getNumEtiqueta() {
		int num = randBetween(1, TagController.getTags().size());
        return String.format("%04d", num);
	}

	/**
	 * generares a random number
	 * @param start initial range
	 * @param end end range
	 * @return the number generated in int format
	 */
	private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }	
}