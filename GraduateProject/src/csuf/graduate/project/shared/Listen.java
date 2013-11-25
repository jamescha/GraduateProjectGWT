package csuf.graduate.project.shared;

import java.io.IOException;
import java.io.PrintStream;

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
			Dump.dump(sPacket, packet);
			
			return sPacket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
}
