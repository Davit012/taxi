package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    int id;
    String username;
    String email;
    String phoneNumber;
    String destFrom;
    String destTo;
    Date date;
    Date time;
    Cars car;
}
