package com.pokebattler.gamemaster;

import POGOProtos.Networking.Responses.DownloadItemTemplatesResponseOuterClass.*;
import com.google.protobuf.util.*;

import java.io.*;

public class GenerateJSON {
	public GenerateJSON() {
	}

	public void writeJSON(InputStream is, OutputStream os) throws IOException {
		DownloadItemTemplatesResponse response = DownloadItemTemplatesResponse.parseFrom(is);
		//no longer needed
		//response = addLegacyMoves(response);
		JsonFormat.Printer printer = JsonFormat.printer();
		try (OutputStreamWriter writer = new OutputStreamWriter(os)) {
			printer.appendTo(response, writer);
		}
	}

	public static void main(String[] args) throws Exception {
		GenerateJSON gen = new GenerateJSON();
		if (args.length == 0 || args.length > 2) {
			System.err.println("USAGE: java -jar pokemongo-game-master-2.46.0.jar BINARY_INPUT_FILE [optional JSON_OUTPUT_FILE]");
			return;
		}
		File f = new File(args[0]);
		if (!f.exists()) {
			System.err.println("File not found: " + args[0]);
		}

		try (OutputStream os = args.length == 2 ? new FileOutputStream(new File(args[1])) : System.out;
			 InputStream is = new FileInputStream(f)) {
			gen.writeJSON(is, os);
		}
	}
}
