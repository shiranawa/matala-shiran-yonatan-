package Tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class Writer {

	public Read data=new Read();

	public Writer() throws IOException {
		/**
		 *  a class that writes a comb file from the wigelwifi files
		 * @author Shiran &Yonatan
		 *
		 */
		
	
		
		String path=("C:\\Users\\Yoni\\Documents\\EclipseProjects\\testMatala\\src\\Data\\output\\our_comb\\best.csv");//output file for the comb/best file
	
	PrintWriter writer = new PrintWriter(path);
	
	writer.println("FirstSeen"+",Id"+",CurrentLantitude"+",CurrentLongitude"+",AltitudeMeters"+",#OfWifiNetworks"+",SSID"+",MAC"+",Channel"+",RSSI"+
			","+",SSID2"+",MAC2"+",Channel2"+",RSSI2"+
			","+",SSID3"+",MAC3"+",Channel3"+",RSSI3"+
			","+",SSID4"+",MAC4"+",Channel4"+",RSSI4"+
			","+",SSID5"+",MAC5"+",Channel5"+",RSSI5"+
			","+",SSID6"+",MAC6"+",Channel6"+",RSSI6"+
			","+",SSID7"+",MAC7"+",Channel7"+",RSSI7"+
			","+",SSID8"+",MAC8"+",Channel8"+",RSSI8"+
			","+",SSID9"+",MAC9"+",Channel9"+",RSSI9"+
			","+",SSID10"+",MAC10"+",Channel10"+",RSSI10"
			);
	

	for(int i=0;i<data.wifiNetworks.size();i++) {
		writer.print(data.wifiNetworks.get(i).FirstSeen+","+ data.wifiNetworks.get(i).Id
				+","+data.wifiNetworks.get(i).CurrentLatitude +","+ data.wifiNetworks.get(i).CurrentLongitude
				+","+ data.wifiNetworks.get(i).AltitudeMeters +","+data.wifiNetworks.get(i).NumberOWN);
		
	for(int j=0;j<data.wifiNetworks.size();j++)
		if(j<data.wifiNetworks.get(i).NumberOWN) {
		writer.print(","+data.wifiNetworks.get(i).SSID2.get(j)+","+
				data.wifiNetworks.get(i).MAC2.get(j) +","+
				data.wifiNetworks.get(i).Channel3.get(j) +","+
				data.wifiNetworks.get(i).RSSI2.get(j)+",");
}writer.println();
	}

			
	writer.close();
	}
	public Read getData() {
		return data;
	}
	
}
