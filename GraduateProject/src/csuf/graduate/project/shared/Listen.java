package csuf.graduate.project.shared;

import java.io.IOException;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.Dump;
import net.tinyos.util.PrintStreamMessenger;

public class Listen {
	
	public Listen (String source) {
        PacketSource reader = BuildSource.makePacketSource(source);
		
		try {
			  reader.open(PrintStreamMessenger.err);
			  for (;;) {
			    byte[] packet = reader.readPacket();
			    Dump.printPacket(System.out, packet);
			    System.out.println();
			    System.out.flush();
			  }
			}
		catch (IOException e) {
			    System.err.println("Error on " + reader.getName() + ": " + e);
			}
	}
}
