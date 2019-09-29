package util.validator;

/**
 *
 * @author Jerson
 */
public abstract class JeValidatorLetter {
    
    public static boolean isLetter(String letter){
        return letter.matches("^[ |a-z|A-Z|áéíóú|ÁÉÍÓÚ]+$");
    }
    /*
    public static void main(String[] args) {
        System.out.println(JeValidatorLetter.isLetter("ABc"));
    }
    */
    
}
