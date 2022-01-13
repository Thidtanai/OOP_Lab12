public interface Tokenizer {
    String peek();

    String consume() throws SyntaxError;

}
