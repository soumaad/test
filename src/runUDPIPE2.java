
import cz.cuni.mff.ufal.udpipe.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;



public class runUDPIPE2

{
	
	// This Source Code Form is subject to the terms of the Mozilla Public
	// License, v. 2.0. If a copy of the MPL was not distributed with this
	// file, You can obtain one at http://mozilla.org/MPL/2.0/.

	// Read Input Text file
	static String readFile(String path, Charset encoding) 
  		  throws IOException 
  		{
  		  byte[] encoded = Files.readAllBytes(Paths.get(path));
  		  return new String(encoded, encoding);
  		}
	
	  public static void main(String[] args) throws IOException {
		  
		
		if (args.length < 3) {
	      System.err.println("Usage: RunUDPipe input_format(tokenize|conllu|horizontal|vertical) output_format(conllu) model_file");
	      System.exit(1);
	    }

	    System.err.print("Loading model: ");
	    Model model = Model.load(args[2]);
	    if (model == null) {
	      System.err.println("Cannot load model from file '" + args[2] + "'");
	      System.exit(1);
	    }
	    System.err.println("done");


	    Pipeline pipeline = new Pipeline(model, args[0], Pipeline.getDEFAULT(), Pipeline.getDEFAULT(), args[1]);
	    ProcessingError error = new ProcessingError();
	    
	    
	    String text = readFile("testAr.txt", StandardCharsets.UTF_8);
	    
	    // Annotate Text
	    
	    String processed = pipeline.process(text, error);

	    if (error.occurred()) {
	      System.err.println("Cannot read input CoNLL-U: " + error.getMessage());
	      System.exit(1);
	    }
	    
	    //System.out.print(processed);
	    
	    // write to output ConLLU file
	    
	    BufferedWriter writer = null;
	    try
	    {
	        writer = new BufferedWriter( new FileWriter("testAr.conllu"));
	        writer.write(processed);

	    }
	    catch ( IOException e)
	    {
	    }
	    finally
	    {
	        try
	        {
	            if ( writer != null)
	            writer.close( );
	        }
	        catch ( IOException e)
	        {
	        }
	    }
	  }
	}



