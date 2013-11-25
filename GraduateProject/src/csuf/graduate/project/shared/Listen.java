package csuf.graduate.project.shared;

import java.io.IOException;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.PrintStreamMessenger;

public class Listen {
	
	PacketSource reader;
	
	public Listen (String source) {
		reader = BuildSource.makePacketSource(source);
	}
	
	public String startListen () {
		 
		 byte[] packet = null;
		
		try {
			  reader.open(PrintStreamMessenger.err);
			  packet = reader.readPacket();
			}
		catch (IOException e) {
			    System.err.println("Error on " + reader.getName() + ": " + e);
			}
		return packet.toString();
	}
	
}
