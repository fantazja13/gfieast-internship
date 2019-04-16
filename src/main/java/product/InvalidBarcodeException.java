package product;

public class InvalidBarcodeException extends IllegalArgumentException {
    public InvalidBarcodeException(String message) {
        super(message);
    }
}
