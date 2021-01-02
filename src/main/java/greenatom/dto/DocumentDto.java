package greenatom.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto implements Serializable {
    private Long id;
    private String name;
    private String type;
    private byte[] value;
    private UserDto owner;
}