import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private String orderId;
    private String userId;
    private String status; // e.g., "ORDER_PLACED"
    private String email;
    private String message;
}
