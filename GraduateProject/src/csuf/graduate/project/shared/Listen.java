package csuf.graduate.project.shared;

import java.io.IOException;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.Dump;
import net.tinyos.util.PrintStreamMessenger;

public class Listen {
	
	PacketSource reader;
	
	public Listen (String source) {
		reader = BuildSource.makePacketSource(source);
		try {
			reader.open(PrintStreamMessenger.err);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String startListen () {
		 
		byte[] packet = null;
		String sPacket = "";
		
		try {
			packet = reader.readPacket();
			sPacket = packetToString(packet);
			//System.out.println("Success Listen");
			return sPacket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String packetToString(byte[] packet){
		String sPacket = "";
		
		for (int i = 0; i < packet.length; i++) {
			
			if (packet[i] >= 0 && packet[i] < 16)
				sPacket += "0";
			sPacket += (Integer.toHexString(packet[i] & 0xff).toUpperCase() + " ");
		}
		
		return sPacket;
	}
	
}
