import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService; // Assume you have a service class

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        // 1. Validate the token (e.g., using a JwtUtils class)
        if (token == null || !isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid or missing token");
        }

        // 2. Fetch the availability from the service
        List<String> availability = doctorService.getDoctorAvailability(id);

        if (availability.isEmpty()) {
            return ResponseEntity.status(404).body("No availability found for this doctor.");
        }

        // 3. Return a structured response using ResponseEntity
        return ResponseEntity.ok(availability);
    }

    private boolean isValidToken(String token) {
        // Implement your token validation logic here
        return token.startsWith("Bearer "); // Simple example
    }
}
