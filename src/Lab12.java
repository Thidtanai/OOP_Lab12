import java.util.ArrayList;
import java.util.Arrays;

public class Lab12 implements Tokenizer{
    public static ArrayList<String> sum;
    static Lab12 newLab12 = new Lab12();
    public static void main(String[] args) throws Exception {
        //normal test
        System.out.println(Calculator("10+150+40"));
        System.out.println(Calculator("25*25"));
        System.out.println(Calculator("12/6"));
        System.out.println(Calculator("12%10"));

        //sum test
        System.out.println(Calculator("10*5+20/2"));
        System.out.println(Calculator("12/2+10%7"));

        //error test
        System.out.println(Calculator("12/0"));
        System.out.println(Calculator("12%0"));
        System.out.println(Calculator("12/+30"));
        System.out.println(Calculator("121"));
        System.out.println(Calculator("+10"));
    }

    private static int parseE() throws SyntaxError{
        int parsedT = parseT();
        String peeking = newLab12.peek();
        while(peek("+") || peek("-")){
            newLab12.consume();
            if(peeking.equals("+")){
                parsedT += parseT();
            }else if(peeking.equals("-")){
                parsedT -= parseT();
            }
        }
        return parsedT;
    }

    private static int parseT() throws SyntaxError{
        int parsedF = parseF();
        String peeking = newLab12.peek();
        if(peek("*") || peek("/") || peek("%")){
            newLab12.consume();
            if(peeking.equals("*")){
                parsedF *= parseT();
            }else if(peeking.equals("/")){
                //use tmp for parseT() only one time
                int tmp = parseT();
                if(tmp != 0){
                    parsedF /= tmp;
                }else {
                    throw new SyntaxError("is divide by 0");
                }
            }else if(peeking.equals("%")){
                int tmp = parseT();
                if(tmp != 0){
                    parsedF %= tmp;
                }else {
                    throw new SyntaxError("is mod by 0");
                }
            }
        }
        return parsedF;
    }

    private static int parseF() throws SyntaxError{
        String peeking = newLab12.peek();
        if(isNum(peeking)){
            String sum = newLab12.consume();
            return Integer.parseInt(sum);
        }else {
            consume("(");
            int parsedE = parseE();
            consume(")");
            return parsedE;
        }
    }

    private static boolean checkCharacterAt(String input, int i) {
        if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' ||input.charAt(i) == '/' ||input.charAt(i) == '%' || input.charAt(i) == '(' || input.charAt(i) == ')')
            return true;
        return false;
    }

    public static String Calculator(String input) {
        input = input.replaceAll("\\s+", "");
        String newString = new String();
        for (int i = 0; i < input.length(); i++) {
            if (checkCharacterAt(input, i)) {
                newString += " ";
            }
            newString += input.charAt(i);
            if (checkCharacterAt(input, i)) {
                newString += " ";
            }
        }
        sum = new ArrayList<String>(Arrays.asList(newString.split("\\s+")));
        if (sum.get(0).equals(""))
            sum.remove(0);
        try {
            int parsedE=parseE();
            if(parsedE!=Integer.MIN_VALUE)
            return String.valueOf(parsedE);
        } catch (SyntaxError e) {
            e.printStackTrace();
        }
        return "Invalid Input";
    }

    private static boolean isNum(String input){
        try{
            Double.parseDouble(input);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public String peek() {
        //if empty return emty else return index0
        if(sum.isEmpty()){
            return "";
        }else {
            return sum.get(0);
        }
    }

    public static boolean peek(String input){
        //return true if this input equal lab12 peek
        return newLab12.peek().equals(input);
    }

    @Override
    public String consume() throws SyntaxError{
        //if empty return empty else return index0 and delete it
        if(sum.isEmpty()){
            return "";
        }else {
            return sum.remove(0);
        }
    }

    public static void consume(String input) throws SyntaxError{
        //return true if this input equal lab12 peek and delete it
        if(peek(input)){
            newLab12.consume();
        }else {
            throw new SyntaxError("Syntax error" + "(" + newLab12.peek() + ")");
        }
    }
}
