package product;

public class BarcodeInUseException extends IllegalArgumentException {

    public BarcodeInUseException(String message) {
        super(message);
    }
}
