package statz.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.sourceforge.stripes.validation.DateTypeConverter;

public class StatzDateTypeConverter extends DateTypeConverter {
	
	  // use only my DateFormat.. not the ones from DateTypeConverter
	  @Override
	  protected DateFormat[] getDateFormats()
	  {
	     return new DateFormat[]{ new SimpleDateFormat( "MM/dd/yyyy hh:mm:ss" ) };
	  }
	  
//		 override so that our slashes don't get removed
	  @Override
	  protected String preProcessInput( String input )
	  {
	     return input;
	  }
}
