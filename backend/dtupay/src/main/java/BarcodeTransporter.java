import java.io.Serializable;
import java.util.UUID;

/**
 *
 *
 * @author Lasse, Jesper
 * @version 1.0
 */
public class BarcodeTransporter implements Serializable{
	public final UUID barcodeUUID;
	
	/**
	 * Constructor
	 *
	 * @param barcodeUUID (required)
	 */
	public BarcodeTransporter(UUID barcodeUUID) {
		this.barcodeUUID = barcodeUUID;
	}
}
