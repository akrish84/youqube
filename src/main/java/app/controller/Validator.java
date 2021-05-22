package app.controller;

import java.util.List;
import java.util.Set;


public class Validator {

	public static void nullCheck(Object object, String paramName) throws IllegalArgumentException {
		
		if( object == null) {
			throw new IllegalArgumentException(paramName + " cannot be null");
		}
	}

	public static void emptyValueCheck(String object, String paramName) throws IllegalArgumentException {
		
		if( object == null || object.isEmpty()) {
			throw new IllegalArgumentException(paramName + " cannot be empty");
		}
	}

	public static void emptyListCheck(List<?> items, String paramName) throws IllegalArgumentException {
		
		if( items == null || items.size() == 0) {
			throw new IllegalArgumentException(paramName + " cannot be empty");
		}
	}

	public static void defaultValueCheck(Integer number, String paramName) throws IllegalArgumentException {
		
		if (number == null || number == 0) {
			throw new IllegalArgumentException(paramName + " cannot have value 0");
		}
	}

	public static void defaultValueCheck(Long number, String paramName) throws IllegalArgumentException {
		
		if( number == null || number == 0) {
			throw new IllegalArgumentException(paramName + " cannot have value 0");
		}
	}

	public static void containsCheck(String object, Set<String> options, String paramName) {
		if(options == null || options.isEmpty()) {
			throw new IllegalArgumentException("Options to check is empty");
		}
		if(!options.contains(object)) {
			throw new IllegalArgumentException(paramName + " " + object + " not in options: " + options);
		}
	}
	
}
