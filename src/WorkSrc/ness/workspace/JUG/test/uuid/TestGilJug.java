package test.uuid;

import com.ness.fw.persistence.uuid.FWUUID;
import com.ness.fw.persistence.uuid.UUIDProvider;

public class TestGilJug {

	public static void main(String[] args) {

		int genCount = 200000;
		long now;
		
		// So that all class loading time stuff will be executed and not measured in the loop;
		UUIDProvider.generateUUID();
		
		now = System.currentTimeMillis();

		for (int i = 0; i < genCount; ++i) {
			byte[] uuid = UUIDProvider.generateUUIDasBytes();
			//System.out.println(uuid);
		}

		printTimes(now, genCount);
		now = System.currentTimeMillis();

		for (int i = 0; i < genCount; ++i) {
			String uuid = UUIDProvider.generateUUID();
			//System.out.println(uuid);
		}

		printTimes(now, genCount);
		now = System.currentTimeMillis();

		for (int i = 0; i < genCount; ++i) {
			String uuid = UUIDProvider.generateUUIDasHex();
			//System.out.println(uuid);
		}

		printTimes(now, genCount);
		now = System.currentTimeMillis();

		for (int i = 0; i < genCount; ++i) {
			FWUUID uuid = new FWUUID();
			uuid.generate();
			System.out.println(uuid.getUUIDAsString() + " " + uuid.hashCode());
		}
		
		printTimes(now, genCount);
	}
	
	public static void printTimes(long now, int genCount){
		now = System.currentTimeMillis() - now;
		long avg = (now * 10 + (genCount / 2)) / genCount;
		System.out.println("Performance: took " + now
				+ " milliseconds to generate (and print out) " + genCount
				+ " UUIDs; average being " + (avg / 10) + "." + (avg % 10)
				+ " msec.");
		
	}
}
