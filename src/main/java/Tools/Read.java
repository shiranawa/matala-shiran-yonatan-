
package Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import wifi_data.*;



public class Read {
	public static Comparator com1= new Comparator<location>(){
		public int compare(location arg0, location arg1) {
			return arg0.FirstSeen.compareTo(arg1.FirstSeen);
		}
	};
	public static Comparator com= new Comparator<wifiPoint>(){
		public int compare(wifiPoint arg0, wifiPoint arg1) {
			return arg0.FirstSeen.compareTo(arg1.FirstSeen);
		}
	};

	public static Comparator com2 = new Comparator<wifiPoint>(){
		public int compare(wifiPoint arg0, wifiPoint arg1) {
			return arg1.RSSI-arg0.RSSI;
		}
	};

	public 	List<location> locations= new ArrayList<location>();
	public List<wifiPoint> wifiPoints = new ArrayList<wifiPoint>();
	public List<wifiNetwork> wifiNetworks = new ArrayList<wifiNetwork>();
	


	public Read() {//read our wigelewifi
		/**
		 * a class that reads wiglewifi files, and creates a list of wifi scans
		 * Shiran &Yonatan
		 *
		 */



		File folder = new File("C:\\Users\\Yoni\\git\\matala-shiran-yonatan-\\Data\\input\\WigleWifi");//path of the files for reading
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()&&file.getName().endsWith(".csv")) {


				try  (BufferedReader br = new BufferedReader(new FileReader(file))){

					String lineRead = ""; 
					lineRead = br.readLine();
					getID ID = new getID(lineRead);
					lineRead = "";
					while ((lineRead = br.readLine()) != null) {

						if (!lineRead.contains("Type")&&!lineRead.contains("brand")&&!lineRead.contains("GSM")) {// Discourages points that are not wifi
							wifiPoint point = new wifiPoint(lineRead,ID);
							location loc= new location(point,ID);
							if(!locations.contains(loc)) 
								locations.add(loc);
							wifiPoints.add(point);	//adds point of wifi to a list				
						}
					}br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}

			}
		}


		make_wifiNetworks(locations, wifiPoints);

	}

	public void add_wigle(String path) throws IOException {//read our wigelewifi and extra wigelwifi from gui
		/**
		 * a class that reads wiglewifi files, and creates a list of wifi scans
		 * Shiran &Yonatan
		 *
		 */
		locations.clear();
		wifiPoints.clear();

		File folder1 = new File(path);


		File[] listOfFiles1 = folder1.listFiles();
		for (File file1 : listOfFiles1) {
			if (file1.isFile()&&file1.getName().endsWith(".csv")) {


				try  (BufferedReader br = new BufferedReader(new FileReader(file1))){

					String lineRead = ""; 
					lineRead = br.readLine();
					getID ID = new getID(lineRead);
					lineRead = "";
					while ((lineRead = br.readLine()) != null) {

						if (!lineRead.contains("Type")&&!lineRead.contains("brand")&&!lineRead.contains("GSM")) {// Discourages points that are not wifi
							wifiPoint point = new wifiPoint(lineRead,ID);
							location loc= new location(point,ID);
							if(!locations.contains(loc)) 
								locations.add(loc);
							wifiPoints.add(point);	//adds point of wifi to a list
						}					
					}br.close();								
				}							
				catch (IOException e) {
					e.printStackTrace();
				}

			}
		}


		make_wifiNetworks(locations, wifiPoints);

	}


	public void make_wifiNetworks(List<location> locations ,List<wifiPoint> wifiPoints) {

		
		wifiPoints.sort(com);
		wifiPoints.sort(com2);
		locations.sort(com1);

		for(int i=0;i<locations.size();i++) 
			for(int j=0;j<wifiPoints.size();j++)
				if((locations.get(i).FirstSeen).compareTo((wifiPoints.get(j).FirstSeen))==0&&
				(locations.get(i).Id).compareTo(wifiPoints.get(j).Id)==0&&locations.get(i).NumberOWN<10) {
					locations.get(i).NumberOWN++;
				}

		for(int i=0;i<locations.size();i++) {
			wifiNetwork wifiNet = new wifiNetwork();
			wifiNet.FirstSeen= locations.get(i).FirstSeen;
			wifiNet.Id= locations.get(i).Id;
			wifiNet.CurrentLatitude= locations.get(i).Lat;
			wifiNet.CurrentLongitude= locations.get(i).Lon;
			wifiNet.AltitudeMeters= locations.get(i).Alt;
			wifiNet.NumberOWN= locations.get(i).NumberOWN;
			for(int j=0;j<wifiPoints.size();j++) 
				if((locations.get(i).FirstSeen).compareTo((wifiPoints.get(j).FirstSeen))==0&&(locations.get(i).Id).compareTo(wifiPoints.get(j).Id)==0) {
					wifiNet.Channel3.add((wifiPoints.get(j).Channel));
					wifiNet.RSSI2.add((wifiPoints.get(j).RSSI));
					wifiNet.MAC2.add((wifiPoints.get(j).MAC));
					wifiNet.SSID2.add((wifiPoints.get(j).SSID));
				}	
			wifiNetworks.add(wifiNet);
		}
	}

	public List<location> getlocations() {

		return locations;
	}

	public List<wifiNetwork> getWifiNetwork() {

		return wifiNetworks;
	}


	public List<wifiPoint> getwifiPoints() {

		return wifiPoints;
	}
	public void sql_adder() {
		
	}

	public void comb_adder(String path) {//adds comb to data

		File folder = new File(path);//path of the files for reading
		File[] listOfFiles = folder.listFiles();
		

		for (File file : listOfFiles) {
			if (file.isFile()&&file.getName().endsWith(".csv")) {


				try  (BufferedReader br = new BufferedReader(new FileReader(file))){


					String lineRead = ""; 
					lineRead = br.readLine();
					getID ID = new getID(lineRead);
					lineRead = "";
					while ((lineRead = br.readLine()) != null) {
						if (!lineRead.contains("Type")&&!lineRead.contains("brand")&&!lineRead.contains("FirstSeen")) {
							wifiNetwork wifiNet = new wifiNetwork();// fix to get the right id
							String[] split = lineRead.split(",");
							wifiNet.FirstSeen= split[0];
							wifiNet.Id= split[1];
							wifiNet.CurrentLatitude = Double.parseDouble(split[2]);
							wifiNet.CurrentLongitude = Double.parseDouble(split[3]);
							wifiNet.AltitudeMeters = Double.parseDouble(split[4]);
							wifiNet.NumberOWN = Integer.parseInt(split[5]);

							for(int i=0;i<wifiNet.NumberOWN;i++) {
								wifiNet.SSID2.add(split[6+i*4]);
								wifiNet.MAC2.add((split[7+i*4]));
								wifiNet.Channel3.add(Integer.parseInt(split[8+i*4]));
								wifiNet.RSSI2.add((int) Double.parseDouble(split[9+i*4]));
							}	


							wifiNetworks.add(wifiNet);
						}


					}br.close();


				}


				catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
