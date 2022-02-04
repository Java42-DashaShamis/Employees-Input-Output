package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String prompt);
	void writeObject(Object obj);
	
	default void writeObjectLine(Object obj) {
		writeObject(obj.toString() + "\n");
	}
	
	default <R> R readObject(String prompt, String errorMessage, Function<String,R> mapper ) {
		
		while(true) {
			String string = readString(prompt);
			try {
				R result = mapper.apply(string);
				return result;
			} catch (Exception e) {
				writeObjectLine(errorMessage);
			}
		}
		
	}
	
	default String readStringPredicate(String prompt, String errorMessage, Predicate<String> predicate) {
		
		return readObject(prompt, errorMessage, str -> {
			if(predicate.test(str)) {
				return str;
			}
			throw new IllegalArgumentException();
		});
	}

	// V.R. The interface isn't the best place for keeping public strings.
	// It is better to declare these message inside certain methods.
	String errorNumber = "It is not a number";
	String errorRange = "The number is not in a certain range";
	String errorOptions = "There is no such option";
	String errorLocalDate = "It is wrong format of date";
	
	default Integer readInt(String prompt) {
		return readObject(prompt, errorNumber ,str -> Integer.parseInt(str));
	}
	
	default Integer readInt(String prompt, int min, int max) {
		// V.R. It is possible to do the same using readObject. It will be simpler.
		return checkRange(prompt, min, max);
	}
	/* V.R. Using private methods is allowed for interfaces. 
	 * But it this case it is possible don't use them.
	 */
	private Integer checkRange(String prompt, int min, int max) {
		while(true) {
			Integer num = readInt(prompt);
			try {
				if(num >= min && num <= max) {
					return num;
				}else {
					throw new IllegalArgumentException();
				}
			} catch (Exception e) {
				writeObjectLine(errorRange);
			}
		}
	}
	
	default Long readLong(String prompt) {
		return readObject(prompt, errorNumber ,str -> Long.parseLong(str));
	}
	
	default String readStringOption(String prompt, Set<String>options) {
		// V.R. How user will know which departments are in the list?
		return readObject(prompt, errorOptions, str -> {
			if(options.contains(str)) {
				return str;
			}
			throw new IllegalArgumentException();
		});
	}
	
	default LocalDate readDate(String prompt) {
		return readObject(prompt, errorLocalDate, str -> LocalDate.parse(str));
	}
	
	default LocalDate readDate(String prompt, DateTimeFormatter formatter) {
		return readObject(prompt, errorLocalDate, str -> LocalDate.parse(str, formatter));
	}
	
}
